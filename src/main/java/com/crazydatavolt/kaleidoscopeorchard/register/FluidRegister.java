package com.crazydatavolt.kaleidoscopeorchard.register;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.github.ysbbbbbb.kaleidoscopetavern.fluid.JuiceFluidType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FluidRegister {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, KaleidoscopeOrchard.MODID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, KaleidoscopeOrchard.MODID);

    //=============== 果汁 ===============//
    // 草莓汁
    public static final ResourceLocation STRAWBERRY_JUICE_ID = KaleidoscopeOrchard.path("strawberry_juice");
    public static final Supplier<FluidType> STRAWBERRY_JUICE_TYPE =
            FLUID_TYPES.register("strawberry_juice", () -> new JuiceFluidType(STRAWBERRY_JUICE_ID, 0));
    public static final Supplier<BaseFlowingFluid.Source> STRAWBERRY_JUICE;
    public static final Supplier<BaseFlowingFluid.Flowing> FLOWING_STRAWBERRY_JUICE;

    // 橙汁
    public static final ResourceLocation ORANGE_JUICE_ID = KaleidoscopeOrchard.path("orange_juice");
    public static final Supplier<FluidType> ORANGE_JUICE_TYPE =
            FLUID_TYPES.register("orange_juice", () -> new JuiceFluidType(ORANGE_JUICE_ID, 0));
    public static final Supplier<BaseFlowingFluid.Source> ORANGE_JUICE;
    public static final Supplier<BaseFlowingFluid.Flowing> FLOWING_ORANGE_JUICE;

    // 猕猴桃汁
    public static final ResourceLocation KIWI_JUICE_ID = KaleidoscopeOrchard.path("kiwi_juice");
    public static final Supplier<FluidType> KIWI_JUICE_TYPE =
            FLUID_TYPES.register("kiwi_juice", () -> new JuiceFluidType(KIWI_JUICE_ID, 0));
    public static final Supplier<BaseFlowingFluid.Source> KIWI_JUICE;
    public static final Supplier<BaseFlowingFluid.Flowing> FLOWING_KIWI_JUICE;

    // 蓝莓汁
    public static final ResourceLocation BLUEBERRY_JUICE_ID = KaleidoscopeOrchard.path("blueberry_juice");
    public static final Supplier<FluidType> BLUEBERRY_JUICE_TYPE =
            FLUID_TYPES.register("blueberry_juice", () -> new JuiceFluidType(BLUEBERRY_JUICE_ID, 0));
    public static final Supplier<BaseFlowingFluid.Source> BLUEBERRY_JUICE;
    public static final Supplier<BaseFlowingFluid.Flowing> FLOWING_BLUEBERRY_JUICE;

    // 柠檬汁
    public static final ResourceLocation LEMON_JUICE_ID = KaleidoscopeOrchard.path("lemon_juice");
    public static final Supplier<FluidType> LEMON_JUICE_TYPE =
            FLUID_TYPES.register("lemon_juice", () -> new JuiceFluidType(LEMON_JUICE_ID, 0));
    public static final Supplier<BaseFlowingFluid.Source> LEMON_JUICE;
    public static final Supplier<BaseFlowingFluid.Flowing> FLOWING_LEMON_JUICE;

    // 西瓜汁
    public static final ResourceLocation WATERMELON_JUICE_ID = KaleidoscopeOrchard.path("watermelon_juice");
    public static final Supplier<FluidType> WATERMELON_JUICE_TYPE =
            FLUID_TYPES.register("watermelon_juice", () -> new JuiceFluidType(WATERMELON_JUICE_ID, 0));
    public static final Supplier<BaseFlowingFluid.Source> WATERMELON_JUICE;
    public static final Supplier<BaseFlowingFluid.Flowing> FLOWING_WATERMELON_JUICE;







    //属性方法
    private static BaseFlowingFluid.Properties strawberryProperties() {
        return new BaseFlowingFluid.Properties(STRAWBERRY_JUICE_TYPE, STRAWBERRY_JUICE, FLOWING_STRAWBERRY_JUICE)
                .bucket(ItemRegister.STRAWBERRY_BUCKET);
    }
    private static BaseFlowingFluid.Properties orangeProperties() {
        return new BaseFlowingFluid.Properties(ORANGE_JUICE_TYPE, ORANGE_JUICE, FLOWING_ORANGE_JUICE)
                .bucket(ItemRegister.ORANGE_BUCKET);
    }
    private static BaseFlowingFluid.Properties kiwiProperties() {
        return new BaseFlowingFluid.Properties(KIWI_JUICE_TYPE, KIWI_JUICE, FLOWING_KIWI_JUICE)
                .bucket(ItemRegister.KIWI_BUCKET);
    }
    private static BaseFlowingFluid.Properties blueberryProperties() {
        return new BaseFlowingFluid.Properties(BLUEBERRY_JUICE_TYPE, BLUEBERRY_JUICE, FLOWING_BLUEBERRY_JUICE)
                .bucket(ItemRegister.BLUEBERRY_BUCKET);
    }
    private static BaseFlowingFluid.Properties lemonProperties() {
        return new BaseFlowingFluid.Properties(LEMON_JUICE_TYPE, LEMON_JUICE, FLOWING_LEMON_JUICE)
                .bucket(ItemRegister.LEMON_BUCKET);
    }
    private static BaseFlowingFluid.Properties watermelonProperties() {
        return new BaseFlowingFluid.Properties(WATERMELON_JUICE_TYPE, WATERMELON_JUICE, FLOWING_WATERMELON_JUICE)
                .bucket(ItemRegister.WATERMELON_BUCKET);
    }





    static {
        //========= 果汁 =========//
        // 草莓汁
        STRAWBERRY_JUICE = FLUIDS.register("strawberry_juice",
                () -> new BaseFlowingFluid.Source(strawberryProperties()));
        FLOWING_STRAWBERRY_JUICE = FLUIDS.register("flowing_strawberry_juice",
                () -> new BaseFlowingFluid.Flowing(strawberryProperties()));
        // 橙汁
        ORANGE_JUICE = FLUIDS.register("orange_juice",
                () -> new BaseFlowingFluid.Source(orangeProperties()));
        FLOWING_ORANGE_JUICE = FLUIDS.register("flowing_orange_juice",
                () -> new BaseFlowingFluid.Flowing(orangeProperties()));
        // 猕猴桃汁
        KIWI_JUICE = FLUIDS.register("kiwi_juice",
                () -> new BaseFlowingFluid.Source(kiwiProperties()));
        FLOWING_KIWI_JUICE = FLUIDS.register("flowing_kiwi_juice",
                () -> new BaseFlowingFluid.Flowing(kiwiProperties()));
        // 蓝莓汁
        BLUEBERRY_JUICE = FLUIDS.register("blueberry_juice",
                () -> new BaseFlowingFluid.Source(blueberryProperties()));
        FLOWING_BLUEBERRY_JUICE = FLUIDS.register("flowing_blueberry_juice",
                () -> new BaseFlowingFluid.Flowing(blueberryProperties()));
        // 柠檬汁
        LEMON_JUICE = FLUIDS.register("lemon_juice",
                () -> new BaseFlowingFluid.Source(lemonProperties()));
        FLOWING_LEMON_JUICE = FLUIDS.register("flowing_lemon_juice",
                () -> new BaseFlowingFluid.Flowing(lemonProperties()));
        // 西瓜汁
        WATERMELON_JUICE = FLUIDS.register("watermelon_juice",
                () -> new BaseFlowingFluid.Source(watermelonProperties()));
        FLOWING_WATERMELON_JUICE = FLUIDS.register("flowing_watermelon_juice",
                () -> new BaseFlowingFluid.Flowing(watermelonProperties()));
    }

    public FluidRegister() {}
}
