package com.vastlands;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A dirt slab that slowly grows grass when it has light and a grass block or grass slab is nearby,
 * so levelled-out fields green up on their own. (Vanilla grass only spreads onto full dirt blocks.)
 */
public class DirtSlabBlock extends SlabBlock {

    public DirtSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!GrassSlabBlock.canBeGrass(level, pos)) {
            return;
        }
        if (level.getMaxLocalRawBrightness(pos.above()) < 9) {
            return;
        }
        if (!level.hasChunksAt(pos.offset(-3, -3, -3), pos.offset(3, 3, 3))) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            BlockPos source = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 2, random.nextInt(3) - 1);
            BlockState sourceState = level.getBlockState(source);
            if (sourceState.is(Blocks.GRASS_BLOCK) || sourceState.is(ModBlocks.GRASS_SLAB.get())) {
                level.setBlockAndUpdate(pos, ModBlocks.GRASS_SLAB.get().defaultBlockState()
                        .setValue(TYPE, state.getValue(TYPE))
                        .setValue(WATERLOGGED, state.getValue(WATERLOGGED)));
                return;
            }
        }
    }
}
