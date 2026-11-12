package com.crazydatavolt.kaleidoscopeorchard.compat.ponder.scene;

import com.crazydatavolt.kaleidoscopeorchard.block.BlueBerryBushBlock;
import com.crazydatavolt.kaleidoscopeorchard.register.BlockRegister;
import com.crazydatavolt.kaleidoscopeorchard.register.ItemRegister;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class RichSoilScene {

    //这个思索借助了AI因为我实在是写麻了
    private static final int CX = 3, CZ = 3;
    private static final long SEED_CROP = 20251109L;
    private static final long SEED_CANE = 2025345340L;

    // ==================== 主流程 ====================
    public static void scene1(SceneBuilder scene, SceneBuildingUtil util) {
        BlockState richFarm = BlockRegister.RICH_SOIL_FARMLAND_BLOCK.get().defaultBlockState();
        BlockState richSoil = BlockRegister.RICH_SOIL_BLOCK.get().defaultBlockState();
        Vec3 centerTop = util.vector().blockSurface(util.grid().at(CX, 0, CZ), Direction.UP);

        scene.title("rich_soil_1", "沃土");
        scene.configureBasePlate(0, 0, 7);
        scene.world().showSection(util.select().everywhere(), Direction.DOWN);
        scene.idle(10);

        // 1. 作物催熟
        scene.overlay().showText(160)
                .text("沃土会催熟它上方的作物，只要它可以被骨粉催熟。")
                .pointAt(centerTop).placeNearTarget();
        scene.addKeyframe();
        phaseCropGrowth(scene, util, richFarm, centerTop);
        scene.addKeyframe();

        // 2. 耕地稳定性
        scene.overlay().showText(80)
                .text("沃土耕地不会因生物踩踏或上方有方块而退化，但你可以用铲子将它翻回沃土。")
                .pointAt(centerTop).placeNearTarget();
        scene.idle(10);
        scene.overlay().showControls(centerTop, Pointing.DOWN, 20)
                .withItem(new ItemStack(Items.IRON_SHOVEL));
        scene.idle(5);
        for (BlockPos p : cropFarmland(util)) {
            scene.world().setBlock(p, richSoil, false);
        }
        scene.idle(70);

        // 3. 甘蔗
        scene.overlay().showText(90)
                .text("沃土上的甘蔗无需水源即可生长，同时生长速度加快，并且可长到最高5格。")
                .pointAt(centerTop).placeNearTarget();
        scene.idle(20);
        phaseSugarCane(scene, util);
        scene.idle(30);

        // 4. 瓜茎
        scene.overlay().showText(90)
                .text("沃土上的瓜茎也会以更快的速度结瓜，且结瓜时无视周围空地下方的方块类型。")
                .pointAt(centerTop).placeNearTarget();
        scene.idle(20);
        phaseMelonStems(scene, util, richFarm, centerTop);
    }

    // ==================== Phase 1：作物催熟 ====================
    private static void phaseCropGrowth(SceneBuilder scene, SceneBuildingUtil util,
                                        BlockState richFarm, Vec3 centerTop) {
        Map<BlockPos, BlockState[]> growth = new LinkedHashMap<>();
        Set<BlockPos> pitchers = new HashSet<>();

        growth.put(util.grid().at(2, 1, 2), cropStages(Blocks.SWEET_BERRY_BUSH, SweetBerryBushBlock.AGE, 3));
        growth.put(util.grid().at(4, 1, 2), cropStages(BlockRegister.BLUEBERRY_BUSH_BLOCK.get(), BlueBerryBushBlock.AGE, 3));
        growth.put(util.grid().at(3, 1, 2), cropStages(Blocks.WHEAT,       CropBlock.AGE,     7));
        growth.put(util.grid().at(4, 1, 3), cropStages(Blocks.WHEAT,       CropBlock.AGE,     7));
        growth.put(util.grid().at(2, 1, 3), cropStages(Blocks.CARROTS,     CropBlock.AGE,     7));
        growth.put(util.grid().at(3, 1, 4), cropStages(Blocks.BEETROOTS,   BeetrootBlock.AGE, 3));
        growth.put(util.grid().at(3, 1, 3), cropStages(Blocks.POTATOES,    CropBlock.AGE,     7));
        growth.put(util.grid().at(2, 1, 4), torchflowerStages());
        BlockPos pitcherPos = util.grid().at(4, 1, 4);
        growth.put(pitcherPos, pitcherLowerStages());
        pitchers.add(pitcherPos);

        // 锄头 → 变耕地
        scene.overlay().showControls(centerTop, Pointing.DOWN, 20)
                .withItem(new ItemStack(Items.IRON_HOE));
        scene.idle(5);
        for (BlockPos p : cropFarmland(util)) {
            scene.world().setBlock(p, richFarm, false);
        }
        scene.idle(15);

        // 种下
        List<BlockPos> all = new ArrayList<>(growth.keySet());
        for (BlockPos p : all) {
            applyCrop(scene, p, growth.get(p)[0], pitchers);
            scene.idle(3);
        }
        scene.idle(30);

        // 随机生长
        growCropsRandomly(scene, all, growth, pitchers, new Random(SEED_CROP), 2);
        scene.idle(20);

        // 清除
        for (BlockPos p : all) {
            scene.world().setBlock(p, Blocks.AIR.defaultBlockState(), true);
            if (pitchers.contains(p)) {
                scene.world().setBlock(p.above(), Blocks.AIR.defaultBlockState(), true);
            }
        }
        scene.idle(20);
    }

    // ==================== Phase 3：甘蔗 ====================
    private static void phaseSugarCane(SceneBuilder scene, SceneBuildingUtil util) {
        List<BlockPos> canes = List.of(
                util.grid().at(2, 1, 3),
                util.grid().at(3, 1, 3),
                util.grid().at(4, 1, 3)
        );
        BlockState cane = Blocks.SUGAR_CANE.defaultBlockState();

        for (BlockPos p : canes) {
            scene.world().setBlock(p, cane, false);
            scene.idle(3);
        }
        scene.idle(20);

        growStacksRandomly(scene, canes, cane, 5, new Random(SEED_CANE), 4);
        scene.idle(40);

        for (BlockPos p : canes) {
            for (int i = 0; i < 5; i++) scene.world().destroyBlock(p.above(i));
        }
    }

    // ==================== Phase 4：瓜茎 ====================
    private static void phaseMelonStems(SceneBuilder scene, SceneBuildingUtil util,
                                        BlockState richFarm, Vec3 centerTop) {
        BlockPos nw = util.grid().at(2, 1, 2), ne = util.grid().at(4, 1, 2);
        BlockPos sw = util.grid().at(2, 1, 4), se = util.grid().at(4, 1, 4);
        BlockPos nwG = util.grid().at(2, 0, 2), neG = util.grid().at(4, 0, 2);
        BlockPos swG = util.grid().at(2, 0, 4), seG = util.grid().at(4, 0, 4);
        BlockPos nE = util.grid().at(3, 0, 2), eE = util.grid().at(4, 0, 3);
        BlockPos sE = util.grid().at(3, 0, 4), wE = util.grid().at(2, 0, 3);

        // 四边平滑石（唯一有粒子的一步）
        for (BlockPos p : List.of(nE, eE, sE, wE)) {
            scene.world().setBlock(p, Blocks.SMOOTH_STONE.defaultBlockState(), true);
            scene.idle(3);
        }
        scene.idle(20);

        // 锄头 + 四角变耕地
        scene.overlay().showControls(centerTop, Pointing.DOWN, 20)
                .withItem(new ItemStack(Items.IRON_HOE));
        scene.idle(5);
        for (BlockPos p : List.of(nwG, neG, swG, seG)) {
            scene.world().setBlock(p, richFarm, false);
            scene.idle(2);
        }
        scene.idle(15);

        // 种下 4 根茎
        BlockPos[] stems = {nw, ne, sw, se};
        BlockState[] types = {
                Blocks.MELON_STEM.defaultBlockState(),
                Blocks.PUMPKIN_STEM.defaultBlockState(),
                Blocks.PUMPKIN_STEM.defaultBlockState(),
                Blocks.MELON_STEM.defaultBlockState()
        };
        for (int i = 0; i < 4; i++) {
            scene.world().setBlock(stems[i], types[i].setValue(StemBlock.AGE, 0), false);
            scene.idle(3);
        }
        scene.idle(15);

        // 同步生长到 AGE=7
        for (int age = 1; age <= 7; age++) {
            for (int i = 0; i < 4; i++) {
                scene.world().setBlock(stems[i], types[i].setValue(StemBlock.AGE, age), false);
            }
            scene.idle(3);
        }
        scene.idle(20);

        // 结瓜
        placeMelon(scene, nw, nE.above(), Blocks.ATTACHED_MELON_STEM, Blocks.MELON, Direction.EAST);
        placeMelon(scene, ne, eE.above(), Blocks.ATTACHED_PUMPKIN_STEM, Blocks.PUMPKIN, Direction.SOUTH);
        placeMelon(scene, se, sE.above(), Blocks.ATTACHED_MELON_STEM, Blocks.MELON, Direction.WEST);
        placeMelon(scene, sw, wE.above(), Blocks.ATTACHED_PUMPKIN_STEM, Blocks.PUMPKIN, Direction.NORTH);
        scene.idle(40);
    }

    private static void placeMelon(SceneBuilder scene, BlockPos stemPos, BlockPos fruitPos,
                                   Block attachedStem, Block fruit, Direction facing) {
        scene.world().setBlock(stemPos, attachedStem.defaultBlockState()
                .setValue(AttachedStemBlock.FACING, facing), false);
        scene.world().setBlock(fruitPos, fruit.defaultBlockState(), false);
        scene.idle(8);
    }

    // ==================== 通用辅助 ====================
    /** 3×3 中心区域里，被翻成耕地的 7 个 y=0 位置（(2,0,2)/(4,0,2) 保留普通沃土） */
    private static BlockPos[] cropFarmland(SceneBuildingUtil util) {
        return new BlockPos[]{
                util.grid().at(3, 0, 2), util.grid().at(2, 0, 3),
                util.grid().at(3, 0, 3), util.grid().at(4, 0, 3),
                util.grid().at(3, 0, 4), util.grid().at(2, 0, 4),
                util.grid().at(4, 0, 4)
        };
    }

    private static BlockState[] torchflowerStages() {
        return new BlockState[]{
                Blocks.TORCHFLOWER_CROP.defaultBlockState().setValue(TorchflowerCropBlock.AGE, 0),
                Blocks.TORCHFLOWER_CROP.defaultBlockState().setValue(TorchflowerCropBlock.AGE, 1),
                Blocks.TORCHFLOWER.defaultBlockState()
        };
    }

    private static BlockState[] pitcherLowerStages() {
        BlockState[] stages = new BlockState[5];
        for (int i = 0; i <= 4; i++) {
            stages[i] = Blocks.PITCHER_CROP.defaultBlockState()
                    .setValue(PitcherCropBlock.AGE, i)
                    .setValue(PitcherCropBlock.HALF, DoubleBlockHalf.LOWER);
        }
        return stages;
    }

    private static BlockState[] cropStages(Block block, IntegerProperty ageProp, int maxAge) {
        BlockState[] stages = new BlockState[maxAge + 1];
        for (int i = 0; i <= maxAge; i++) {
            stages[i] = block.defaultBlockState().setValue(ageProp, i);
        }
        return stages;
    }

    /** 设置作物方块；瓶子草需同步上/下半块。无粒子。 */
    private static void applyCrop(SceneBuilder scene, BlockPos p, BlockState state, Set<BlockPos> pitchers) {
        scene.world().setBlock(p, state, false);
        if (pitchers.contains(p)) {
            int age = state.getValue(PitcherCropBlock.AGE);
            BlockState upper = age >= 3
                    ? state.setValue(PitcherCropBlock.HALF, DoubleBlockHalf.UPPER)
                    : Blocks.AIR.defaultBlockState();
            scene.world().setBlock(p.above(), upper, false);
        }
    }

    /** 作物池里随机挑一个升一级，直到全部成熟。 */
    private static void growCropsRandomly(SceneBuilder scene, List<BlockPos> positions,
                                          Map<BlockPos, BlockState[]> growth, Set<BlockPos> pitchers,
                                          Random random, int tickPerStep) {
        Map<BlockPos, Integer> ages = new HashMap<>();
        for (BlockPos p : positions) ages.put(p, 0);

        while (true) {
            List<BlockPos> growable = new ArrayList<>();
            for (BlockPos p : positions) {
                if (ages.get(p) < growth.get(p).length - 1) growable.add(p);
            }
            if (growable.isEmpty()) break;

            BlockPos p = growable.get(random.nextInt(growable.size()));
            int next = ages.merge(p, 1, Integer::sum);
            applyCrop(scene, p, growth.get(p)[next], pitchers);
            scene.idle(tickPerStep);
        }
    }

    /** 每格独立堆叠的方块（如甘蔗），随机挑一根往上长一格，直到全部达 maxHeight。 */
    private static void growStacksRandomly(SceneBuilder scene, List<BlockPos> bases,
                                           BlockState block, int maxHeight,
                                           Random random, int tickPerStep) {
        Map<BlockPos, Integer> heights = new HashMap<>();
        for (BlockPos p : bases) heights.put(p, 1);

        while (true) {
            List<BlockPos> growable = new ArrayList<>();
            for (BlockPos p : bases) {
                if (heights.get(p) < maxHeight) growable.add(p);
            }
            if (growable.isEmpty()) break;

            BlockPos p = growable.get(random.nextInt(growable.size()));
            int newHeight = heights.merge(p, 1, Integer::sum);
            scene.world().setBlock(p.above(newHeight - 1), block, false);
            scene.idle(tickPerStep);
        }
    }
}
