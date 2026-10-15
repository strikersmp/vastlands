package com.vastlands;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A slab with a grass top. Behaves like a grass block: it turns back into a dirt slab when it is
 * buried or drowned, and it spreads grass onto nearby dirt blocks. (Dirt slabs pull grass from
 * nearby grass, see {@link DirtSlabBlock}.)
 */
public class GrassSlabBlock extends SlabBlock {

    public GrassSlabBlock(Properties properties) {
        super(properties);
    }

    /** Same idea as the vanilla grass check: grass survives under open sky, leaves, slabs, snow layers. */
    static boolean canBeGrass(LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        if (aboveState.is(Blocks.SNOW) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        }
        if (aboveState.getFluidState().getAmount() == 8) {
            return false;
        }
        return !aboveState.isSolidRender(level, above);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canBeGrass(level, pos)) {
            if (!level.hasChunksAt(pos.offset(-3, -3, -3), pos.offset(3, 3, 3))) {
                return;
            }
            level.setBlockAndUpdate(pos, ModBlocks.DIRT_SLAB.get().defaultBlockState()
                    .setValue(TYPE, state.getValue(TYPE))
                    .setValue(WATERLOGGED, state.getValue(WATERLOGGED)));
            return;
        }

        if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
            if (!level.hasChunksAt(pos.offset(-3, -3, -3), pos.offset(3, 3, 3))) {
                return;
            }
            for (int i = 0; i < 4; i++) {
                BlockPos target = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (level.getBlockState(target).is(Blocks.DIRT) && canBeGrass(level, target)) {
                    boolean snowy = level.getBlockState(target.above()).is(Blocks.SNOW);
                    level.setBlockAndUpdate(target,
                            Blocks.GRASS_BLOCK.defaultBlockState().setValue(GrassBlock.SNOWY, snowy));
                }
            }
        }
    }
}
