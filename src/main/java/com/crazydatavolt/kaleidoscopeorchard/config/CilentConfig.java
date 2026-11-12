package com.crazydatavolt.kaleidoscopeorchard.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CilentConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SHOW_STAR_ON_WINE = BUILDER
            .define("showStarOnWine", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
