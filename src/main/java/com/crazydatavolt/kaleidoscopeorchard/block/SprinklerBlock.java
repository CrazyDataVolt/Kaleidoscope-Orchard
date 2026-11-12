package com.crazydatavolt.kaleidoscopeorchard.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class SprinklerBlock extends Block {
    public SprinklerBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.isClientSide) return ItemInteractionResult.SUCCESS;
        if(stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BushBlock){
            tryPlantSeed(level, pos, stack, blockItem, player, hand);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    protected void tryPlantSeed(Level level, BlockPos pos, ItemStack stack, BlockItem blockItem, Player player, InteractionHand hand){
        BlockPos pos1 = pos.offset(8, 0, 8);
        BlockPos pos2 = pos.offset(-8, 0, -8);
        for (BlockPos targetPos : BlockPos.betweenClosed(pos1, pos2)) {
            if (level.getBlockState(targetPos).isEmpty()){
                BlockPos below = targetPos.below();
                BlockHitResult fakeHit = new BlockHitResult(
                        new Vec3(below.getX() + 0.5, below.getY() + 1.0, below.getZ() + 0.5),
                        Direction.UP, below, false
                );
                BlockPlaceContext context = new BlockPlaceContext(level, player, hand, stack, fakeHit);
                if(blockItem.place(context).consumesAction()){
                    stack.shrink(1);
                }
            }
            if(stack.getCount() <= 0) return;
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this,0);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos pos1 = pos.offset(8, -1, 8);
        BlockPos pos2 = pos.offset(-8, -1, -8);
        for (BlockPos targetPos : BlockPos.betweenClosed(pos1, pos2)) {
            BlockState targetState = level.getBlockState(targetPos);
            if (targetState.getBlock() instanceof FarmBlock){
                level.setBlock(targetPos, targetState.setValue(FarmBlock.MOISTURE, 7), Block.UPDATE_ALL);
            }
        }
        level.scheduleTick(pos, this,50);
    }
}
