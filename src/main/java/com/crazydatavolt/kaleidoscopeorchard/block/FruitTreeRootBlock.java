package com.crazydatavolt.kaleidoscopeorchard.block;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.crazydatavolt.kaleidoscopeorchard.init.OrchardTag;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.RootedDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.List;

public class FruitTreeRootBlock extends RootedDirtBlock {
    private static final int maxNutrition = 10;
    private static final int normalNutrition = 4;
    public static final IntegerProperty NUTRITION = IntegerProperty.create("nutrition", 0, maxNutrition);
    public FruitTreeRootBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.ROOTED_DIRT));
        registerDefaultState(this.stateDefinition.any().setValue(NUTRITION, 2));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isClientSide) return;

        BlockPos pos1 = pos.offset(1, 0, 1);
        BlockPos pos2 = pos.offset(-1, -1, -1);

        boolean water = false;
        boolean fertility = false;
        int richSoil = 0;

        for (BlockPos targetPos : BlockPos.betweenClosed(pos1, pos2)) {
            if(level.getBlockState(targetPos).is(OrchardTag.Blocks.RICH_SOIL)){
                richSoil = richSoil + 1;
            }
            if (richSoil == 6){
                fertility = true;
                break;
            }
        }

        for (BlockPos targetPos : BlockPos.betweenClosed(pos1, pos2)) {
            if(!water){
                water = absorbWater(level,targetPos);
            }
            if(!fertility){
                fertility = absorbFertility(level,targetPos);
            }
            if (water && fertility) break;
        }

        int nut = state.getValue(NUTRITION);
        if (water && fertility) {
            level.setBlock(pos, state.setValue(NUTRITION, Math.min(nut+1, maxNutrition)), Block.UPDATE_ALL);
        } else if(!water && !fertility) {
            level.setBlock(pos, state.setValue(NUTRITION, Math.max(nut-1, 0)), Block.UPDATE_ALL);
        } else {
            if(nut > normalNutrition){
                level.setBlock(pos, state.setValue(NUTRITION, nut-1), Block.UPDATE_ALL);
            } else {
                level.setBlock(pos, state.setValue(NUTRITION, Math.min(nut+1, normalNutrition)), Block.UPDATE_ALL);
            }
        }
    }

    private Boolean absorbWater(Level level, BlockPos targetPos){
        BlockState state = level.getBlockState(targetPos);

        if (state.is(Blocks.WATER)) {
            level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);
            return true;
        } else if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.setBlock(targetPos, state.setValue(BlockStateProperties.WATERLOGGED, false), 3);
            return true;
        } else if (state.getBlock() instanceof LiquidBlockContainer){
            level.destroyBlock(targetPos, true);
            level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);
            return true;
        }
        return false;
    }

    private Boolean absorbFertility(Level level, BlockPos targetPos){
        BlockState state = level.getBlockState(targetPos);
        if (state.is(OrchardTag.Blocks.RICH_SOIL)) {
            level.setBlock(targetPos, Blocks.PODZOL.defaultBlockState(), 3);
            return true;
        }else if (state.is(OrchardTag.Blocks.NUTRIENT_SOIL)) {
            level.setBlock(targetPos, Blocks.DIRT.defaultBlockState(), 3);
            return true;
        } else if (state.is(OrchardTag.Blocks.COMMON_SOIL)) {
            level.setBlock(targetPos, Blocks.COARSE_DIRT.defaultBlockState(), 3);
            return true;
        }
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NUTRITION);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}
