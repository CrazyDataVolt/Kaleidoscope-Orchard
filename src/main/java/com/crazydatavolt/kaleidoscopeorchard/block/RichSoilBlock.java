package com.crazydatavolt.kaleidoscopeorchard.block;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.crazydatavolt.kaleidoscopeorchard.config.ServerConfig;
import com.crazydatavolt.kaleidoscopeorchard.mixin.StemBlockAccessorMixin;
import com.crazydatavolt.kaleidoscopeorchard.register.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RichSoilBlock extends Block {

    public RichSoilBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        tryGrowPlant(level, pos, random);
    }

    public static void tryGrowPlant(ServerLevel level, BlockPos pos, RandomSource random) {
        if(random.nextDouble() < getChance()){
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            growPlant(aboveState, abovePos, level);
        }
    }

    private static float getChance(){
        return ServerConfig.RICH_SOIL_CHANCE.get().floatValue();
    }

    public static void growPlant(BlockState state, BlockPos pos, ServerLevel level) {
        if (state.getBlock() instanceof BonemealableBlock growable && growable.isValidBonemealTarget(level, pos, state)) {
            if (CommonHooks.canCropGrow(level, pos, state, true)) {
                growable.performBonemeal(level, level.random, pos, state);
                CommonHooks.fireCropGrowPost(level, pos, state);
            }
        } else if (state.getBlock() instanceof SugarCaneBlock){
            growSugarCane(level, pos);
        } else if (state.getBlock() instanceof StemBlockAccessorMixin stem){
            growMelon(stem, level, pos);
        }
    }

    public static void growSugarCane(ServerLevel level, BlockPos pos) {
        BlockPos top = pos;
        int height = 1;
        while (height < 5-1 && level.getBlockState(top.above()).getBlock() instanceof SugarCaneBlock) {
            top = top.above();
            height++;
        }
        if (level.isEmptyBlock(top.above())) {
            level.setBlock(top.above(), level.getBlockState(top), Block.UPDATE_ALL);
        }
    }

    public static void growMelon(StemBlockAccessorMixin stem, Level level, BlockPos pos){
        Direction[] directions = {Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH};

        for (Direction direction : directions) {
            BlockPos targetPos = pos.relative(direction);
            if (level.isEmptyBlock(targetPos)) {
                Registry<Block> registry = level.registryAccess().registryOrThrow(Registries.BLOCK);
                Block fruitBlock = registry.getOrThrow(stem.getFruit());
                Block attachedStemBlock = registry.getOrThrow(stem.getAttachedStem());

                level.setBlock(targetPos, fruitBlock.defaultBlockState(), Block.UPDATE_ALL);
                BlockState attachedState = attachedStemBlock.defaultBlockState();
                if (attachedState.hasProperty(HorizontalDirectionalBlock.FACING)) {
                    attachedState = attachedState.setValue(HorizontalDirectionalBlock.FACING, direction);
                }
                level.setBlockAndUpdate(pos, attachedState);
                return;
            }
        }
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility ability, boolean simulate) {
        if (ability.equals(ItemAbilities.HOE_TILL) && context.getLevel().getBlockState(context.getClickedPos().above()).isAir()) {
            return BlockRegister.RICH_SOIL_FARMLAND_BLOCK.get().defaultBlockState();
        }
        return null;
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        if(plant.getBlock() instanceof SugarCaneBlock){
            return TriState.TRUE;
        }
        return TriState.DEFAULT;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        KaleidoscopeOrchard.addTooltip(components, this.getDescriptionId());
    }
}
