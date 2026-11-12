package com.crazydatavolt.kaleidoscopeorchard.block;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.crazydatavolt.kaleidoscopeorchard.register.BlockRegister;
import com.crazydatavolt.kaleidoscopeorchard.register.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class FruitTreeSaplingBlock extends FruitSaplingBlock {
    private static final Map<Item, Supplier<Block>> TRANSFORMATIONS = new HashMap<>();
    public FruitTreeSaplingBlock(TreeGrower treeGrower) {
        super(treeGrower);
    }

    public static void registerTransformation(ItemLike item, Supplier<Block> targetBlockSupplier) {
        TRANSFORMATIONS.put(item.asItem(), targetBlockSupplier);
    }//注册方法

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        Item item = stack.getItem();
        if (TRANSFORMATIONS.containsKey(item)) {
            if (!level.isClientSide) {
                Block targetBlock = TRANSFORMATIONS.get(item).get();
                level.setBlock(pos, targetBlock.defaultBlockState(), Block.UPDATE_ALL);
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
                level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        if(!flag.hasShiftDown()){
            KaleidoscopeOrchard.addTooltip(components, this.getDescriptionId());
            KaleidoscopeOrchard.addTooltip(components, this.getDescriptionId(), "shift");
        }else{
            KaleidoscopeOrchard.addTooltip(components, this.getDescriptionId(), "1");
            KaleidoscopeOrchard.addTooltip(components, this.getDescriptionId(), "2");
            KaleidoscopeOrchard.addTooltip(components, this.getDescriptionId(), "3");
            KaleidoscopeOrchard.addTooltip(components, this.getDescriptionId(), "4");
            KaleidoscopeOrchard.addTooltip(components, this.getDescriptionId(), "5");
        }
    }
}
