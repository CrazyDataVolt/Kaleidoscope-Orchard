package com.crazydatavolt.kaleidoscopeorchard.register;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.crazydatavolt.kaleidoscopeorchard.block.*;
import com.crazydatavolt.kaleidoscopeorchard.init.OrchardTreeGrower;
import com.github.ysbbbbbb.kaleidoscopecookery.block.drink.TeacupBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.DrinkBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.crazydatavolt.kaleidoscopeorchard.register.ItemRegister.*;

public class BlockRegister {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(KaleidoscopeOrchard.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, KaleidoscopeOrchard.MODID);



    //============ 果树 ============//
    public static final DeferredBlock<Block> FRUIT_TREE_SAPLING_BLOCK = BLOCKS.registerBlock(
            "fruit_tree_sapling",
            properties -> new FruitTreeSaplingBlock(OrchardTreeGrower.FRUIT_TREE_GROWER)
    );
    public static final DeferredBlock<Block> ORANGE_SAPLING_BLOCK = BLOCKS.registerBlock(
            "orange_sapling",
            properties -> new FruitSaplingBlock(OrchardTreeGrower.ORANGE_TREE_GROWER)
    );
    public static final DeferredBlock<Block> KIWI_SAPLING_BLOCK = BLOCKS.registerBlock(
            "kiwi_sapling",
            properties -> new FruitSaplingBlock(OrchardTreeGrower.KIWI_TREE_GROWER)
    );
    public static final DeferredBlock<Block> LEMON_SAPLING_BLOCK = BLOCKS.registerBlock(
            "lemon_sapling",
            properties -> new FruitSaplingBlock(OrchardTreeGrower.LEMON_TREE_GROWER)
    );

    public static final DeferredBlock<Block> FRUIT_TREE_ROOT_BLOCK = BLOCKS.registerBlock(
            "fruit_tree_root",
            properties -> new FruitTreeRootBlock()
    );

    public static final DeferredBlock<Block> FRUIT_TREE_LOG_BLOCK = BLOCKS.registerBlock(
            "fruit_tree_log",
            properties -> new FruitTreeLogBlock(Blocks.STRIPPED_OAK_LOG)
    );

    public static final DeferredBlock<Block> FRUIT_TREE_LEAVES_BLOCK = BLOCKS.registerBlock(
            "fruit_tree_leaves", properties -> new FruitTreeLeavesBlock()
    );

    public static final DeferredBlock<Block> APPLE_LEAVES_BLOCK = BLOCKS.registerBlock(
            "apple_leaves", properties -> new FruitLeavesBlock(() -> Items.APPLE)
    );

    public static final DeferredBlock<Block> ORANGE_LEAVES_BLOCK = BLOCKS.registerBlock(
            "orange_leaves", properties -> new FruitLeavesBlock(ORANGE)
    );

    public static final DeferredBlock<Block> KIWI_LEAVES_BLOCK = BLOCKS.registerBlock(
            "kiwi_leaves", properties -> new FruitLeavesBlock(KIWI)
    );

    public static final DeferredBlock<Block> LEMON_LEAVES_BLOCK = BLOCKS.registerBlock(
            "lemon_leaves", properties -> new FruitLeavesBlock(LEMON)
    );



    //============ 洒水器&堆肥箱&沃土 ============//
    public static final DeferredBlock<Block> SPRINKLER_BLOCK = BLOCKS.registerBlock(
            "sprinkler", properties -> new SprinklerBlock()
    );
    public static final DeferredBlock<Block> COMPOST_BIN_BLOCK = BLOCKS.registerBlock(
            "compost_bin", properties -> new CompostBinBlock()
    );
    public static final DeferredBlock<Block> RICH_SOIL_BLOCK = BLOCKS.registerBlock(
            "rich_soil", properties -> new RichSoilBlock()
    );
    public static final DeferredBlock<Block> RICH_SOIL_FARMLAND_BLOCK = BLOCKS.registerBlock(
            "rich_soil_farmland", properties -> new RichSoilFarmBlock()
    );



    //============ 植株 ============//
    public static final DeferredBlock<Block> STRAWBERRY_PLANT_BLOCK = BLOCKS.registerBlock(
            "strawberry_plant", properties -> new StrawberryPlantBlock()
    );

    public static final DeferredBlock<Block> BLUEBERRY_BUSH_BLOCK = BLOCKS.registerBlock(
            "blueberry_bush", properties -> new BlueBerryBushBlock()
    );



    //============ 蛋糕 ============//
    public static final DeferredBlock<Block> STRAWBERRY_CAKE_BLOCK = BLOCKS.registerBlock(
            "strawberry_cake",
            properties -> new RoundCakeBlock(STRAWBERRY_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> ORANGE_CAKE_BLOCK = BLOCKS.registerBlock(
            "orange_cake",
            properties -> new RoundCakeBlock(ORANGE_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> KIWI_CAKE_BLOCK = BLOCKS.registerBlock(
            "kiwi_cake",
            properties -> new RoundCakeBlock(KIWI_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> BLUEBERRY_CAKE_BLOCK = BLOCKS.registerBlock(
            "blueberry_cake",
            properties -> new RoundCakeBlock(BLUEBERRY_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> SWEET_BERRY_CAKE_BLOCK = BLOCKS.registerBlock(
            "sweet_berry_cake",
            properties -> new RoundCakeBlock(SWEET_BERRY_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> GLOW_BERRY_CAKE_BLOCK = BLOCKS.registerBlock(
            "glow_berry_cake",
            properties -> new RoundCakeBlock(GLOW_BERRY_CAKE_SLICE) {
                @Override public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) { return 14; }
            }
    );

    public static final DeferredBlock<Block> GRAPE_CAKE_BLOCK = BLOCKS.registerBlock(
            "grape_cake",
            properties -> new RoundCakeBlock(GRAPE_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> LEMON_CAKE_BLOCK = BLOCKS.registerBlock(
            "lemon_cake",
            properties -> new RoundCakeBlock(LEMON_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> WATERMELON_CAKE_BLOCK = BLOCKS.registerBlock(
            "watermelon_cake",
            properties -> new RoundCakeBlock(WATERMELON_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> CHORUS_CAKE_BLOCK = BLOCKS.registerBlock(
            "chorus_cake",
            properties -> new RoundCakeBlock(CHORUS_CAKE_SLICE)
    );
    public static final DeferredBlock<Block> GOLD_GRAPE_CAKE_BLOCK = BLOCKS.registerBlock(
            "gold_grape_cake",
            properties -> new RoundCakeBlock(ItemRegister.GOLD_GRAPE_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> GREEN_GRAPE_CAKE_BLOCK = BLOCKS.registerBlock(
            "green_grape_cake",
            properties -> new RoundCakeBlock(ItemRegister.GREEN_GRAPE_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> ICE_GRAPE_CAKE_BLOCK = BLOCKS.registerBlock(
            "ice_grape_cake",
            properties -> new RoundCakeBlock(ItemRegister.ICE_GRAPE_CAKE_SLICE)
    );

    public static final DeferredBlock<Block> CHOCOLATE_CAKE_BLOCK = BLOCKS.registerBlock(
            "chocolate_cake",
            properties -> new RoundCakeBlock(CHOCOLATE_CAKE_SLICE)
    );
    public static final DeferredBlock<Block> HONEY_CAKE_BLOCK = BLOCKS.registerBlock(
            "honey_cake",
            properties -> new RoundCakeBlock(ItemRegister.ICE_GRAPE_CAKE_SLICE)
    );
    public static final DeferredBlock<Block> MATCHA_CAKE_BLOCK = BLOCKS.registerBlock(
            "matcha_cake",
            properties -> new RoundCakeBlock(ItemRegister.ICE_GRAPE_CAKE_SLICE)
    );
    public static final DeferredBlock<Block> APPLE_CAKE_BLOCK = BLOCKS.registerBlock(
            "apple_cake",
            properties -> new RoundCakeBlock(ItemRegister.ICE_GRAPE_CAKE_SLICE)
    );



    //============ 巧克力&热可可 ============//
    public static final DeferredBlock<Block> CHOCOLATE_BLOCK = BLOCKS.registerSimpleBlock("chocolate_block", BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_WOOD).sound(SoundType.STONE));



    //============ 酒 ============//
    public static final DeferredBlock<Block> STRAWBERRY_WINE_BLOCK = BLOCKS.register("strawberry_wine", DrinkBlock.create().maxCount(4).shapes(new VoxelShape[]{Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0), Block.box(2.0, 0.0, 6.0, 14.0, 16.0, 10.0), Shapes.or(Block.box(2.0, 0.0, 10.0, 14.0, 16.0, 14.0), Block.box(6.0, 0.0, 2.0, 10.0, 16.0, 14.0)), Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)}).build());
    public static final DeferredBlock<Block> ORANGE_WINE_BLOCK = BLOCKS.register("orange_wine", DrinkBlock.create().maxCount(4).shapes(new VoxelShape[]{Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0), Block.box(2.0, 0.0, 6.0, 14.0, 16.0, 10.0), Shapes.or(Block.box(2.0, 0.0, 10.0, 14.0, 16.0, 14.0), Block.box(6.0, 0.0, 2.0, 10.0, 16.0, 14.0)), Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)}).build());
    public static final DeferredBlock<Block> KIWI_WINE_BLOCK = BLOCKS.register("kiwi_wine", DrinkBlock.create().maxCount(4).shapes(new VoxelShape[]{Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0), Block.box(2.0, 0.0, 6.0, 14.0, 16.0, 10.0), Shapes.or(Block.box(2.0, 0.0, 10.0, 14.0, 16.0, 14.0), Block.box(6.0, 0.0, 2.0, 10.0, 16.0, 14.0)), Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)}).build());
    public static final DeferredBlock<Block> BLUEBERRY_WINE_BLOCK = BLOCKS.register("blueberry_wine", DrinkBlock.create().maxCount(4).shapes(new VoxelShape[]{Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0), Block.box(2.0, 0.0, 6.0, 14.0, 16.0, 10.0), Shapes.or(Block.box(2.0, 0.0, 10.0, 14.0, 16.0, 14.0), Block.box(6.0, 0.0, 2.0, 10.0, 16.0, 14.0)), Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)}).build());
    public static final DeferredBlock<Block> GLOW_HONEY_WINE_BLOCK = BLOCKS.register("glow_honey_wine", DrinkBlock.create().maxCount(4).shapes(new VoxelShape[]{Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0), Block.box(2.0, 0.0, 6.0, 14.0, 16.0, 10.0), Shapes.or(Block.box(2.0, 0.0, 10.0, 14.0, 16.0, 14.0), Block.box(6.0, 0.0, 2.0, 10.0, 16.0, 14.0)), Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)}).build());



    //============ 瓜 ============//
    public static final DeferredBlock<Block> EMPTY_MELON_BLOCK = BLOCKS.registerBlock(
            "empty_melon", EmptyMelonBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.MELON).noOcclusion()
    );

    public static final DeferredBlock<Block> OPEN_MELON_BLOCK = BLOCKS.registerBlock(
            "open_melon", OpenMelonBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.MELON).noOcclusion()
    );

    public static final DeferredBlock<Block> FRUIT_MELON_BLOCK = BLOCKS.registerBlock(
            "fruit_melon", FruitMelonBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.MELON).noOcclusion()
    );



}
