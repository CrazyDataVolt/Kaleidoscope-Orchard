package com.crazydatavolt.kaleidoscopeorchard.config;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue RICH_SOIL_CHANCE = BUILDER
            .defineInRange("RichSoilChance", 0.5,0.01,1);

    public static final ModConfigSpec.DoubleValue COMPOST_BIN_CHANCE_MULTPLIER = BUILDER
            .defineInRange("CompostBinChanceMultiplier", 0.25,0.01,1);

    public static final ModConfigSpec.IntValue COMPOST_BIN_TIME = BUILDER
            .defineInRange("CompostBinTime", 200,20,Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue FRUIT_TREE_LEAVES_FIND_ROOT_RANGE = BUILDER
            .defineInRange("FruitTreeLeavesFindRootRange", 20,5,64);

    public static final ModConfigSpec.IntValue FRUIT_TREE_LEAVES_FIND_ROOT_BLOCK_QUANTITY = BUILDER
            .defineInRange("FruitTreeLeavesFindRootBlockQuantity", 100,10,512);


    public static final ModConfigSpec SPEC = BUILDER.build();
}
