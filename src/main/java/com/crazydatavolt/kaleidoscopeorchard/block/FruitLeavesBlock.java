package com.crazydatavolt.kaleidoscopeorchard.block;

import com.crazydatavolt.kaleidoscopeorchard.config.ServerConfig;
import com.crazydatavolt.kaleidoscopeorchard.register.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.*;
import java.util.function.Supplier;

public class FruitLeavesBlock extends FruitTreeLeavesBlock {
    private static final int maxAge = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    private final Supplier<Item> fruit;
    private final int minDrop = 1;
    private final int maxDrop = 2;

    public FruitLeavesBlock(Supplier<Item> fruit) {
        this.fruit = fruit;
        registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        int nut = foundNutrition(level, pos);
        float chance = 0.1f * nut;
        if (random.nextFloat() < chance) {
            int age = state.getValue(AGE);
            if (age < maxAge) {
                level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_ALL);
            } else {
                harvest(level, pos, state, nut);
            }
        }
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        int age = state.getValue(AGE);

        if (age == maxAge) {
            int nut = foundNutrition(level, pos);
            harvest(level, pos, state, nut);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private void harvest(Level level, BlockPos pos, BlockState state, int nutritionValue) {
        int addValue = Math.max(nutritionValue - 6, 0);
        int dropCount = level.random.nextIntBetweenInclusive(minDrop, maxDrop+addValue);
        if (dropCount > 0) {
            popResource(level, pos, new ItemStack(fruit.get(), dropCount));
        }
        level.setBlock(pos, state.setValue(AGE, 0), Block.UPDATE_ALL);
        level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
    }


    //以下算法由AI编写
    private int foundNutrition(Level level, BlockPos pos) {
        Set<BlockPos> visited = new HashSet<>();
        Deque<BlockPos> stack = new ArrayDeque<>();
        int maxChebyshevDist = ServerConfig.FRUIT_TREE_LEAVES_FIND_ROOT_RANGE.getAsInt();   // 切比雪夫距离上限
        int maxBlocks = ServerConfig.FRUIT_TREE_LEAVES_FIND_ROOT_BLOCK_QUANTITY.getAsInt(); // 最大访问方块数
        int visitedCount = 0;

        stack.push(pos);
        visited.add(pos);
        visitedCount++;

        while (!stack.isEmpty() && visitedCount <= maxBlocks) {
            BlockPos current = stack.pop();
            BlockState state = level.getBlockState(current);

            // 检查树干且正下方为根
            if (state.getBlock() instanceof FruitTreeLogBlock) {
                BlockPos below = current.below();
                BlockState belowState = level.getBlockState(below);
                if (belowState.getBlock() instanceof FruitTreeRootBlock) {
                    return belowState.getValue(FruitTreeRootBlock.NUTRITION);
                }
            }

            // 计算当前节点的切比雪夫距离（与起点的最大坐标差）
            int dx = Math.abs(current.getX() - pos.getX());
            int dy = Math.abs(current.getY() - pos.getY());
            int dz = Math.abs(current.getZ() - pos.getZ());
            int chebDist = Math.max(dx, Math.max(dy, dz));
            if (chebDist >= maxChebyshevDist) {
                continue;  // 超过距离限制，不再扩展邻居
            }

            // 方向优先级：向下 > 北 > 南 > 西 > 东 > 向上
            // 栈为LIFO，故将优先方向放在数组末尾最后压入，使其最先弹出
            Direction[] priorityDirs = {
                    Direction.UP,       // 最低优先级，最先压入
                    Direction.EAST,
                    Direction.WEST,
                    Direction.SOUTH,
                    Direction.NORTH,
                    Direction.DOWN      // 最高优先级，最后压入
            };

            for (Direction dir : priorityDirs) {
                BlockPos neighbor = current.relative(dir);
                if (visited.contains(neighbor)) continue;

                BlockState neighborState = level.getBlockState(neighbor);
                if (neighborState.getBlock() instanceof FruitTreeLeavesBlock ||
                        neighborState.getBlock() instanceof FruitTreeLogBlock) {
                    visited.add(neighbor);
                    visitedCount++;
                    if (visitedCount > maxBlocks) break;
                    stack.push(neighbor);
                }
            }
        }
        return 0;
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}
