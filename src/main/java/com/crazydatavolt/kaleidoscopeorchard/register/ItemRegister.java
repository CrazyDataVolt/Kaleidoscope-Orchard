package com.crazydatavolt.kaleidoscopeorchard.register;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.crazydatavolt.kaleidoscopeorchard.item.*;
import com.github.ysbbbbbb.kaleidoscopetavern.item.DrinkBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.item.JuiceBucketItem;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.crazydatavolt.kaleidoscopeorchard.register.BlockRegister.*;
import static com.crazydatavolt.kaleidoscopeorchard.register.EffectsRegister.*;

public class ItemRegister {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(KaleidoscopeOrchard.MODID);

    //============ 水果 ============//
    public static final DeferredItem<Item> STRAWBERRY = ITEMS.register("strawberry", () -> new ItemNameBlockItem(STRAWBERRY_PLANT_BLOCK.get(),new Item.Properties().food(
            buildFoodValue(2,1.2F).alwaysEdible().build())));
    public static final DeferredItem<Item> ORANGE = ITEMS.registerSimpleItem("orange", new Item.Properties().food(
            buildFoodValue(4,2.4F).alwaysEdible().build()));
    public static final DeferredItem<Item> KIWI = ITEMS.registerSimpleItem("kiwi", new Item.Properties().food(
            buildFoodValue(4,2.4F).alwaysEdible().build()));
    public static final DeferredItem<Item> BLUEBERRY = ITEMS.register("blueberry", () -> new ItemNameBlockItem(BLUEBERRY_BUSH_BLOCK.get(),new Item.Properties().food(
            buildFoodValue(2,0.4F).alwaysEdible().build())));
    public static final DeferredItem<Item> LEMON = ITEMS.registerSimpleItem("lemon", new Item.Properties().food(
            buildFoodValue(4,2.4F).alwaysEdible().build()));



    //============ 水果切片 ============//
    public static final DeferredItem<EatTimeItem> APPLE_SLICE = ITEMS.register("apple_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(2,1.2F).alwaysEdible().build()),24));
    public static final DeferredItem<EatTimeItem> ORANGE_SLICE = ITEMS.register("orange_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(2,1.2F).alwaysEdible().build()),24));
    public static final DeferredItem<EatTimeItem> LEMON_SLICE = ITEMS.register("lemon_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(2,1.2F).alwaysEdible().build()),24));
    public static final DeferredItem<EatTimeItem> KIWI_SLICE = ITEMS.register("kiwi_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(2,1.2F).alwaysEdible().build()),24));
    public static final DeferredItem<EatTimeItem> CANTALOUPE_SLICE = ITEMS.register("cantaloupe_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(2,1.2F).alwaysEdible().build()),24));



    //============ 果树 ============//
    public static final DeferredItem<BlockItem> FRUIT_TREE_SAPLING = ITEMS.registerSimpleBlockItem("fruit_tree_sapling", FRUIT_TREE_SAPLING_BLOCK);
    public static final DeferredItem<BlockItem> FRUIT_TREE_LOG = ITEMS.registerSimpleBlockItem("fruit_tree_log", FRUIT_TREE_LOG_BLOCK);
    public static final DeferredItem<BlockItem> FRUIT_TREE_LEAVES = ITEMS.registerSimpleBlockItem("fruit_tree_leaves", FRUIT_TREE_LEAVES_BLOCK);
    public static final DeferredItem<BlockItem> APPLE_LEAVES = ITEMS.registerSimpleBlockItem("apple_leaves", APPLE_LEAVES_BLOCK);
    public static final DeferredItem<BlockItem> ORANGE_LEAVES = ITEMS.registerSimpleBlockItem("orange_leaves", ORANGE_LEAVES_BLOCK);
    public static final DeferredItem<BlockItem> KIWI_LEAVES = ITEMS.registerSimpleBlockItem("kiwi_leaves", KIWI_LEAVES_BLOCK);
    public static final DeferredItem<BlockItem> LEMON_LEAVES = ITEMS.registerSimpleBlockItem("lemon_leaves", LEMON_LEAVES_BLOCK);
    public static final DeferredItem<BlockItem> FRUIT_TREE_ROOT = ITEMS.registerSimpleBlockItem("fruit_tree_root", FRUIT_TREE_ROOT_BLOCK);



    //============ 洒水器&堆肥箱&沃土 ============//
    public static final DeferredItem<BlockItem> SPRINKLER = ITEMS.registerSimpleBlockItem("sprinkler", SPRINKLER_BLOCK);
    public static final DeferredItem<BlockItem> COMPOST_BIN = ITEMS.registerSimpleBlockItem("compost_bin", COMPOST_BIN_BLOCK);
    public static final DeferredItem<BlockItem> RICH_SOIL = ITEMS.registerSimpleBlockItem("rich_soil", RICH_SOIL_BLOCK);
    public static final DeferredItem<BlockItem> RICH_SOIL_FARMLAND = ITEMS.registerSimpleBlockItem("rich_soil_farmland", RICH_SOIL_FARMLAND_BLOCK);



    //============ 桶 ============//
    public static final DeferredItem<JuiceBucketItem> STRAWBERRY_BUCKET = ITEMS.register("strawberry_bucket",
            () -> new JuiceBucketItem(FluidRegister.STRAWBERRY_JUICE));
    public static final DeferredItem<JuiceBucketItem> ORANGE_BUCKET = ITEMS.register("orange_bucket",
            () -> new JuiceBucketItem(FluidRegister.ORANGE_JUICE));
    public static final DeferredItem<JuiceBucketItem> KIWI_BUCKET = ITEMS.register("kiwi_bucket",
            () -> new JuiceBucketItem(FluidRegister.KIWI_JUICE));
    public static final DeferredItem<JuiceBucketItem> BLUEBERRY_BUCKET = ITEMS.register("blueberry_bucket",
            () -> new JuiceBucketItem(FluidRegister.BLUEBERRY_JUICE));
    public static final DeferredItem<JuiceBucketItem> LEMON_BUCKET = ITEMS.register("lemon_bucket",
            () -> new JuiceBucketItem(FluidRegister.LEMON_JUICE));
    public static final DeferredItem<JuiceBucketItem> WATERMELON_BUCKET = ITEMS.register("watermelon_bucket",
            () -> new JuiceBucketItem(FluidRegister.WATERMELON_JUICE));



    //============ 巧克力 ============//
    public static final DeferredItem<Item> CHOCOLATE = ITEMS.registerSimpleItem("chocolate", new Item.Properties().food(
            buildFoodValue(6,6).alwaysEdible().build()));
    public static final DeferredItem<BlockItem> CHOCOLATE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("chocolate_block", CHOCOLATE_BLOCK);



    //============ 蛋糕 ============//
    public static final DeferredItem<Item> CAKE_BASE = ITEMS.register("cake_base", () -> new CakeBaseItem(new Item.Properties()));
    public static final DeferredItem<BlockItem> STRAWBERRY_CAKE = ITEMS.registerSimpleBlockItem("strawberry_cake", STRAWBERRY_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> ORANGE_CAKE = ITEMS.registerSimpleBlockItem("orange_cake", ORANGE_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> KIWI_CAKE = ITEMS.registerSimpleBlockItem("kiwi_cake", KIWI_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> BLUEBERRY_CAKE = ITEMS.registerSimpleBlockItem("blueberry_cake", BLUEBERRY_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> SWEET_BERRY_CAKE = ITEMS.registerSimpleBlockItem("sweet_berry_cake", SWEET_BERRY_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> GLOW_BERRY_CAKE = ITEMS.registerSimpleBlockItem("glow_berry_cake", GLOW_BERRY_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> GRAPE_CAKE = ITEMS.registerSimpleBlockItem("grape_cake", GRAPE_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> CHOCOLATE_CAKE = ITEMS.registerSimpleBlockItem("chocolate_cake", CHOCOLATE_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> LEMON_CAKE = ITEMS.registerSimpleBlockItem("lemon_cake", LEMON_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> WATERMELON_CAKE = ITEMS.registerSimpleBlockItem("watermelon_cake", WATERMELON_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> CHORUS_CAKE = ITEMS.registerSimpleBlockItem("chorus_cake", CHORUS_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> GOLD_GRAPE_CAKE = ITEMS.registerSimpleBlockItem("gold_grape_cake", GOLD_GRAPE_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> GREEN_GRAPE_CAKE = ITEMS.registerSimpleBlockItem("green_grape_cake", GREEN_GRAPE_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> ICE_GRAPE_CAKE = ITEMS.registerSimpleBlockItem("ice_grape_cake", ICE_GRAPE_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> HONEY_CAKE = ITEMS.registerSimpleBlockItem("honey_cake", HONEY_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> MATCHA_CAKE = ITEMS.registerSimpleBlockItem("matcha_cake", MATCHA_CAKE_BLOCK);
    public static final DeferredItem<BlockItem> APPLE_CAKE = ITEMS.registerSimpleBlockItem("apple_cake", APPLE_CAKE_BLOCK);




    //============ 蛋糕切片 ============//
    public static final DeferredItem<EatTimeItem> CAKE_SLICE = ITEMS.register("cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(2,0.4f).build()),24));
    public static final DeferredItem<EatTimeItem> STRAWBERRY_CAKE_SLICE = ITEMS.register("strawberry_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> ORANGE_CAKE_SLICE = ITEMS.register("orange_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> KIWI_CAKE_SLICE = ITEMS.register("kiwi_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> BLUEBERRY_CAKE_SLICE = ITEMS.register("blueberry_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> SWEET_BERRY_CAKE_SLICE = ITEMS.register("sweet_berry_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> GLOW_BERRY_CAKE_SLICE = ITEMS.register("glow_berry_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> GRAPE_CAKE_SLICE = ITEMS.register("grape_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> CHOCOLATE_CAKE_SLICE = ITEMS.register("chocolate_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> LEMON_CAKE_SLICE = ITEMS.register("lemon_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> WATERMELON_CAKE_SLICE = ITEMS.register("watermelon_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> CHORUS_CAKE_SLICE = ITEMS.register("chorus_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> GOLD_GRAPE_CAKE_SLICE = ITEMS.register("gold_grape_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> GREEN_GRAPE_CAKE_SLICE = ITEMS.register("green_grape_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> ICE_GRAPE_CAKE_SLICE = ITEMS.register("ice_grape_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));
    public static final DeferredItem<EatTimeItem> APPLE_CAKE_SLICE = ITEMS.register("apple_cake_slice", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),24));



    //============ 饼干 ============//
    public static final DeferredItem<EatTimeItem> SWEET_BERRY_COOKIE = ITEMS.register("sweet_berry_cookie", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),16));

    public static final DeferredItem<EatTimeItem> GLOW_BERRY_COOKIE = ITEMS.register("glow_berry_cookie", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),16));

    public static final DeferredItem<EatTimeItem> BLUEBERRY_COOKIE = ITEMS.register("blueberry_cookie", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),16));

    public static final DeferredItem<EatTimeItem> CHOCOLATE_COOKIE = ITEMS.register("chocolate_cookie", () -> new EatTimeItem(new Item.Properties().food(
            buildFoodValue(4,3f).effect(buildEffect(HIT_RECOVER_EFFECT,120,1),1).build()),16));




    //============ 酒 ============//
    public static final DeferredItem<Item> BLUEBERRY_WINE = ITEMS.register("blueberry_wine", () -> new DrinkBlockItem(BLUEBERRY_WINE_BLOCK.get()));
    public static final DeferredItem<Item> STRAWBERRY_WINE = ITEMS.register("strawberry_wine", () -> new DrinkBlockItem(STRAWBERRY_WINE_BLOCK.get()));
    public static final DeferredItem<Item> ORANGE_WINE = ITEMS.register("orange_wine", () -> new DrinkBlockItem(ORANGE_WINE_BLOCK.get()));
    public static final DeferredItem<Item> KIWI_WINE = ITEMS.register("kiwi_wine", () -> new DrinkBlockItem(KIWI_WINE_BLOCK.get()));
    public static final DeferredItem<Item> GLOW_HONEY_WINE = ITEMS.register("glow_honey_wine", () -> new DrinkBlockItem(GLOW_HONEY_WINE_BLOCK.get()));



    //============ 瓜 ============//
    public static final DeferredItem<BlockItem> EMPTY_MELON = ITEMS.register("empty_melon", () -> new EmptyMelonItem(EMPTY_MELON_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> OPEN_MELON = ITEMS.registerSimpleBlockItem("open_melon", OPEN_MELON_BLOCK);
    public static final DeferredItem<BlockItem> FRUIT_MELON = ITEMS.registerSimpleBlockItem("fruit_melon", FRUIT_MELON_BLOCK);



    //============ 未完成的蛋糕（机械动力联动） ============//
    public static final DeferredItem<Item> INCOMPLETE_STRAWBERRY_CAKE = ITEMS.register("incomplete_strawberry_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_ORANGE_CAKE = ITEMS.register("incomplete_orange_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_KIWI_CAKE = ITEMS.register("incomplete_kiwi_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_BLUEBERRY_CAKE = ITEMS.register("incomplete_blueberry_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_SWEET_BERRY_CAKE = ITEMS.register("incomplete_sweet_berry_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_GLOW_BERRY_CAKE = ITEMS.register("incomplete_glow_berry_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_GRAPE_CAKE = ITEMS.register("incomplete_grape_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_LEMON_CAKE = ITEMS.register("incomplete_lemon_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_WATERMELON_CAKE = ITEMS.register("incomplete_watermelon_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_CHORUS_CAKE = ITEMS.register("incomplete_chorus_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_GOLD_GRAPE_CAKE = ITEMS.register("incomplete_gold_grape_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_GREEN_GRAPE_CAKE = ITEMS.register("incomplete_green_grape_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_ICE_GRAPE_CAKE = ITEMS.register("incomplete_ice_grape_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_MATCHA_CAKE = ITEMS.register("incomplete_matcha_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INCOMPLETE_APPLE_CAKE = ITEMS.register("incomplete_apple_cake", () -> new CakeBaseItem(new Item.Properties().stacksTo(1)));



    //============ 其他 ============//




    //============ 食物效果构建 ============//
    public static FoodProperties.Builder buildFoodValue(int nutrition, float saturation) {
        return new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation / 2 / (float) nutrition);
    }

    public static Supplier<MobEffectInstance> buildEffect(Holder<MobEffect> effect, int time, int level) {
        return () -> new MobEffectInstance(effect, time * 20, level - 1);
    }
}
