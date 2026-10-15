package com.vastlands;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Vast Lands: an exploration-focused world type (see data/vastlands/worldgen) plus dirt and grass slabs.
 * All terrain generation is data driven, the code here only registers the blocks.
 */
@Mod(VastLandsMod.MOD_ID)
public class VastLandsMod {
    public static final String MOD_ID = "vastlands";

    public VastLandsMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(modBus);
        ModBlocks.ITEMS.register(modBus);
        ModCreativeTabs.TABS.register(modBus);
    }
}
