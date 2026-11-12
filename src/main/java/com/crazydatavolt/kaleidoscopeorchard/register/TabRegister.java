package com.crazydatavolt.kaleidoscopeorchard.register;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModDataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.crazydatavolt.kaleidoscopeorchard.register.ItemRegister.*;

public class TabRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KaleidoscopeOrchard.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.kaleidoscope_orchard"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> STRAWBERRY.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(FRUIT_TREE_SAPLING);
                output.accept(FRUIT_TREE_LEAVES);
                output.accept(APPLE_LEAVES);
                output.accept(ORANGE_LEAVES);
                output.accept(LEMON_LEAVES);
                output.accept(KIWI_LEAVES);
                output.accept(FRUIT_TREE_LOG);
                output.accept(FRUIT_TREE_ROOT);

                //output.accept(SPRINKLER);
                output.accept(COMPOST_BIN);
                output.accept(RICH_SOIL);
                output.accept(RICH_SOIL_FARMLAND);

                output.accept(STRAWBERRY);
                output.accept(ORANGE.get());
                output.accept(ORANGE_SLICE);
                output.accept(LEMON.get());
                output.accept(LEMON_SLICE.get());
                output.accept(KIWI.get());
                output.accept(KIWI_SLICE.get());
                output.accept(BLUEBERRY.get());
                //output.accept(APPLE_SLICE);

                output.accept(STRAWBERRY_BUCKET.get());
                output.accept(ORANGE_BUCKET.get());
                output.accept(LEMON_BUCKET.get());
                output.accept(KIWI_BUCKET.get());
                output.accept(BLUEBERRY_BUCKET.get());
                output.accept(WATERMELON_BUCKET.get());

                output.accept(CHOCOLATE.get());
                output.accept(CHOCOLATE_BLOCK_ITEM.get());

                output.accept(CAKE_BASE.get());
                output.accept(CAKE_SLICE.get());

                output.accept(SWEET_BERRY_CAKE.get());
                output.accept(SWEET_BERRY_CAKE_SLICE.get());
                output.accept(GLOW_BERRY_CAKE.get());
                output.accept(GLOW_BERRY_CAKE_SLICE.get());
                output.accept(BLUEBERRY_CAKE.get());
                output.accept(BLUEBERRY_CAKE_SLICE.get());

                output.accept(STRAWBERRY_CAKE.get());
                output.accept(STRAWBERRY_CAKE_SLICE.get());

                output.accept(ORANGE_CAKE.get());
                output.accept(ORANGE_CAKE_SLICE.get());
                output.accept(LEMON_CAKE.get());
                output.accept(LEMON_CAKE_SLICE.get());
                output.accept(KIWI_CAKE.get());
                output.accept(KIWI_CAKE_SLICE.get());

                output.accept(APPLE_CAKE.get());
                output.accept(APPLE_CAKE_SLICE.get());
                output.accept(WATERMELON_CAKE.get());
                output.accept(WATERMELON_CAKE_SLICE.get());

                output.accept(GRAPE_CAKE.get());
                output.accept(GRAPE_CAKE_SLICE.get());
                output.accept(ICE_GRAPE_CAKE.get());
                output.accept(ICE_GRAPE_CAKE_SLICE.get());
                output.accept(GOLD_GRAPE_CAKE.get());
                output.accept(GOLD_GRAPE_CAKE_SLICE.get());
                output.accept(GREEN_GRAPE_CAKE.get());
                output.accept(GREEN_GRAPE_CAKE_SLICE.get());

                output.accept(CHOCOLATE_CAKE.get());
                output.accept(CHOCOLATE_CAKE_SLICE.get());

                output.accept(CHORUS_CAKE.get());
                output.accept(CHORUS_CAKE_SLICE.get());

                //output.accept(HONEY_CAKE.get());
                //output.accept(MATCHA_CAKE.get());

                output.accept(SWEET_BERRY_COOKIE.get());
                output.accept(GLOW_BERRY_COOKIE.get());
                output.accept(BLUEBERRY_COOKIE.get());
                output.accept(CHOCOLATE_COOKIE.get());

                output.accept(wineWithLevel(STRAWBERRY_WINE, 5));
                output.accept(wineWithLevel(ORANGE_WINE, 5));
                output.accept(wineWithLevel(KIWI_WINE, 5));
                output.accept(wineWithLevel(BLUEBERRY_WINE, 5));
                output.accept(wineWithLevel(GLOW_HONEY_WINE,5));

                output.accept(EMPTY_MELON.get());
                output.accept(OPEN_MELON.get());
                output.accept(FRUIT_MELON.get());



                output.accept(INCOMPLETE_STRAWBERRY_CAKE);
                output.accept(INCOMPLETE_ORANGE_CAKE);
                output.accept(INCOMPLETE_KIWI_CAKE);
                output.accept(INCOMPLETE_BLUEBERRY_CAKE);
                output.accept(INCOMPLETE_SWEET_BERRY_CAKE);
                output.accept(INCOMPLETE_GLOW_BERRY_CAKE);
                output.accept(INCOMPLETE_GRAPE_CAKE);
                output.accept(INCOMPLETE_LEMON_CAKE);
                output.accept(INCOMPLETE_WATERMELON_CAKE);
                output.accept(INCOMPLETE_CHORUS_CAKE);
                output.accept(INCOMPLETE_GOLD_GRAPE_CAKE);
                output.accept(INCOMPLETE_GREEN_GRAPE_CAKE);
                output.accept(INCOMPLETE_ICE_GRAPE_CAKE);
                output.accept(INCOMPLETE_MATCHA_CAKE);
                output.accept(INCOMPLETE_APPLE_CAKE);


            }).build());

    private static ItemStack wineWithLevel(Supplier<? extends Item> wineItem, int brewLevel) {
        ItemStack stack = new ItemStack(wineItem.get());
        stack.set(ModDataComponents.BREW_LEVEL.get(), brewLevel);
        return stack;
    }
}
