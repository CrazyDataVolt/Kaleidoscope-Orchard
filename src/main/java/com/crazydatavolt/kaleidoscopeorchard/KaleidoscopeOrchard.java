package com.crazydatavolt.kaleidoscopeorchard;

import com.crazydatavolt.kaleidoscopeorchard.config.CilentConfig;
import com.crazydatavolt.kaleidoscopeorchard.config.ServerConfig;
import com.crazydatavolt.kaleidoscopeorchard.register.FluidRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.List;

import static com.crazydatavolt.kaleidoscopeorchard.register.BlockRegister.BLOCKS;
import static com.crazydatavolt.kaleidoscopeorchard.register.BlockRegister.BLOCK_ENTITIES;
import static com.crazydatavolt.kaleidoscopeorchard.register.EffectsRegister.MOB_EFFECTS;
import static com.crazydatavolt.kaleidoscopeorchard.register.ItemRegister.ITEMS;
import static com.crazydatavolt.kaleidoscopeorchard.register.TabRegister.CREATIVE_MODE_TABS;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(KaleidoscopeOrchard.MODID)
public class KaleidoscopeOrchard {

    public static final String MODID = "kaleidoscope_orchard";

    public static ResourceLocation path(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static void addTooltip(List<Component> components, String descriptionId){
        components.add(Component.translatable("tooltip" + descriptionId.substring(5)).withStyle(ChatFormatting.GRAY));
    }

    public static void addTooltip(List<Component> components, String descriptionId, String suffix){
        components.add(Component.translatable("tooltip" + descriptionId.substring(5) + "." + suffix).withStyle(ChatFormatting.GRAY));
    }


    public KaleidoscopeOrchard(IEventBus Bus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, CilentConfig.SPEC);
        FluidRegister.FLUID_TYPES.register(Bus);
        FluidRegister.FLUIDS.register(Bus);
        NeoForgeMod.enableMilkFluid();
        MOB_EFFECTS.register(Bus);
        BLOCKS.register(Bus);
        BLOCK_ENTITIES.register(Bus);
        ITEMS.register(Bus);
        CREATIVE_MODE_TABS.register(Bus);
    }
}
