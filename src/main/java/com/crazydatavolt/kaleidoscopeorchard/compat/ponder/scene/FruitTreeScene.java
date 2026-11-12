package com.crazydatavolt.kaleidoscopeorchard.compat.ponder.scene;

import com.crazydatavolt.kaleidoscopeorchard.register.BlockRegister;
import com.crazydatavolt.kaleidoscopeorchard.register.ItemRegister;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FruitTreeScene {

    public static void scene1(SceneBuilder scene, SceneBuildingUtil util) {
        BlockPos pos = util.grid().at(2, 1, 2);

        // ---- 以下三个占位，替换成你自己的方块/物品 ----
        BlockState blockA = BlockRegister.FRUIT_TREE_SAPLING_BLOCK.get().defaultBlockState();
        BlockState blockB = BlockRegister.ORANGE_SAPLING_BLOCK.get().defaultBlockState();
        ItemStack itemB = new ItemStack(ItemRegister.ORANGE.get());

        scene.title("fruit_tree_sapling_1", "果树嫁接");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().everywhere(), Direction.DOWN);

        // 1. 场地中心显示方块 A
        scene.idle(10);
        scene.world().setBlock(pos, blockA, false);
        scene.idle(20);

        // 2. 文字：默认会长成苹果树
        scene.overlay().showText(60)
                .text("果树树苗默认会长成苹果树")
                .pointAt(util.vector().blockSurface(pos, Direction.UP))
                .placeNearTarget();
        scene.idle(70);

        scene.addKeyframe();

        // 3. 文字：嫁接说明
        scene.overlay().showText(100)
                .text("对它使用某些水果可以对其进行嫁接，从而改变它的树种")
                .pointAt(util.vector().blockSurface(pos, Direction.UP))
                .placeNearTarget();
        scene.idle(20);

        // 4. 物品 B 指向方块 A
        scene.overlay().showControls(util.vector().blockSurface(pos, Direction.UP), Pointing.DOWN, 25)
                .withItem(itemB);
        scene.idle(30);


        // 5. 方块 A 变成方块 B
        scene.world().setBlock(pos, blockB, true);
        scene.idle(30);

    }


    public static void scene2(SceneBuilder scene, SceneBuildingUtil util) {
        BlockPos rootPos = util.grid().at(4, 0, 4);
        BlockPos waterPos = util.grid().at(3, 0, 3);

        BlockState coarseDirt = Blocks.COARSE_DIRT.defaultBlockState();
        BlockState dirt = Blocks.DIRT.defaultBlockState();
        BlockState grass = Blocks.GRASS_BLOCK.defaultBlockState();
        BlockState podzol = Blocks.PODZOL.defaultBlockState();
        BlockState richSoil = BlockRegister.RICH_SOIL_BLOCK.get().defaultBlockState();
        BlockState root = BlockRegister.FRUIT_TREE_ROOT_BLOCK.get().defaultBlockState();
        BlockState slab = Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState()
                .setValue(SlabBlock.WATERLOGGED, false);
        BlockState waterSlab = Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState()
                .setValue(SlabBlock.WATERLOGGED, true);

        scene.title("fruit_tree_sapling_2", "果树培养");

        scene.configureBasePlate(0, 0, 9);
        scene.world().showSection(util.select().everywhere(), Direction.DOWN);

        scene.idle(10);

        for (int x = 6; x <= 8; x++) {
            for (int z = 0; z <= 2; z++) {
                BlockPos p = util.grid().at(x, 1, z);
                scene.world().setBlock(p, dirt, true);
                scene.idle(1);
            }
        }
        scene.world().setBlock(util.grid().at(7, 1, 1), root, true);
        scene.idle(3);
        scene.overlay().showOutline(PonderPalette.BLUE, new Object(), util.select().fromTo(6, 0, 0, 8, 1, 2), 120);
        scene.overlay().showOutline(PonderPalette.GREEN, new Object(), util.select().fromTo(7, 1, 1, 7, 1, 1), 120);

        scene.overlay().showText(120)
                .text("果树树根的影响范围为以它为中心的3×2×3")
                .pointAt(util.vector().blockSurface(util.grid().at(7, 1, 1), Direction.UP))
                .placeNearTarget();
        scene.idle(160);

        for (int x = 6; x <= 8; x++) {
            for (int z = 0; z <= 2; z++) {
                BlockPos p = util.grid().at(x, 1, z);
                scene.world().destroyBlock(p);
                scene.idle(1);
            }
        }

        scene.addKeyframe();
        scene.idle(10);
        //==汲取养分与水分==//

        List<BlockPos> soilPositions = new ArrayList<>();
        for (int x = 3; x <= 5; x++) {
            for (int z = 3; z <= 5; z++) {
                BlockPos p = util.grid().at(x, 0, z);
                if (!p.equals(rootPos) && !p.equals(waterPos)) soilPositions.add(p);
            }
        }
        Collections.shuffle(soilPositions, new Random(1221983245));

        scene.overlay().showOutline(PonderPalette.BLUE, new Object(), util.select().fromTo(3, 0, 3, 5, 0, 5), 120);
        scene.overlay().showText(120)
                .text("果树根部会汲取周围土壤的养分与水分")
                .pointAt(util.vector().blockSurface(rootPos, Direction.UP))
                .placeNearTarget();
        scene.idle(30);

        for (BlockPos p : soilPositions) {
            scene.world().setBlock(p, coarseDirt, false);
            scene.world().setBlock(waterPos, slab, false);
            scene.idle(5);
            scene.world().setBlock(waterPos, waterSlab, false);
            scene.idle(10);
        }
        scene.idle(20);

        scene.addKeyframe();

        //==营养土==//
        scene.overlay().showText(100)
                .text("某些土壤具有更高的营养价值")
                .pointAt(util.vector().blockSurface(rootPos, Direction.UP))
                .placeNearTarget();
        scene.idle(20);

        Collections.shuffle(soilPositions, new Random(13343245));
        for (int i = 0; i < soilPositions.size(); i++) {
            BlockPos p = soilPositions.get(i);
            scene.world().setBlock(p, i % 2 == 0 ? podzol : richSoil, true);
            scene.idle(1);
        }
        scene.idle(20);

        Collections.shuffle(soilPositions, new Random(19657645));
        for (int i = 0; i < soilPositions.size(); i++) {
            BlockPos p = soilPositions.get(i);
            scene.world().setBlock(p, i % 2 == 0 ? dirt : podzol, false);
            scene.world().setBlock(waterPos, slab, false);
            scene.idle(5);
            scene.world().setBlock(waterPos, waterSlab, false);
            scene.idle(10);
        }

        scene.idle(20);
        scene.addKeyframe();

        // ===== 第三部分：条件与结果 =====
        for (BlockPos p : soilPositions) {
            scene.world().setBlock(p, coarseDirt, false);
            scene.idle(1);
        }
        scene.world().setBlock(waterPos, slab, false);

        scene.overlay().showText(80)
                .text("若养分与水分都不满足，果树会慢慢停止结果")
                .pointAt(util.vector().blockSurface(rootPos, Direction.UP))
                .placeNearTarget();
        scene.idle(90);

        scene.world().setBlock(waterPos, waterSlab, true);

        scene.overlay().showText(80)
                .text("满足其中至少一种，果树才会正常结果")
                .pointAt(util.vector().blockSurface(rootPos, Direction.UP))
                .placeNearTarget();
        scene.idle(90);



        for (BlockPos p : soilPositions) {
            scene.world().setBlock(p, grass, true);
            scene.idle(1);
        }

        Vec3 treeTop = util.vector().centerOf(rootPos).add(0, 1.5, 0);
        scene.overlay().showText(80)
                .text("若养分与水分都持续满足，结果速度与产量均会大幅提升")
                .pointAt(util.vector().blockSurface(rootPos, Direction.UP))
                .placeNearTarget();
        scene.idle(90);



        for (BlockPos p : soilPositions) {
            scene.world().setBlock(p, richSoil, true);
            scene.idle(1);
        }
        scene.overlay().showText(80)
                .text("以及，如果果树树根的影响范围内有至少6块沃土……")
                .pointAt(util.vector().blockSurface(rootPos, Direction.UP))
                .placeNearTarget();
        scene.idle(90);

        scene.overlay().showText(80)
                .text("周围的土壤将永不退化。")
                .pointAt(util.vector().blockSurface(rootPos, Direction.UP))
                .placeNearTarget();
        for (int i = 0; i < 6; i++) {
            scene.world().setBlock(waterPos, slab, false);
            scene.idle(5);
            scene.world().setBlock(waterPos, waterSlab, false);
            scene.idle(15);
        }
    }

}
