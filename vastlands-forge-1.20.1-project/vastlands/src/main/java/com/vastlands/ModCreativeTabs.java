package com.vastlands;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VastLandsMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> VAST_LANDS = TABS.register("vastlands",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.vastlands"))
                    .icon(() -> new ItemStack(ModBlocks.GRASS_SLAB.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.DIRT_SLAB.get());
                        output.accept(ModBlocks.GRASS_SLAB.get());
                    })
                    .build());

    private ModCreativeTabs() {
    }
}
