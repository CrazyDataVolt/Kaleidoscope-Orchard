package com.crazydatavolt.kaleidoscopeorchard.event;


import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.crazydatavolt.kaleidoscopeorchard.block.FruitTreeSaplingBlock;
import com.crazydatavolt.kaleidoscopeorchard.init.OrchardTag;
import com.crazydatavolt.kaleidoscopeorchard.register.BlockRegister;
import com.crazydatavolt.kaleidoscopeorchard.register.ItemRegister;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;

@SuppressWarnings("all")
@EventBusSubscriber(modid = KaleidoscopeOrchard.MODID)
public class ServerEvent {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack stack = player.getItemInHand(hand);

        if(level.getBlockState(pos).is(Blocks.MELON) && (stack.is(ItemTags.SWORDS) || stack.is(ItemTags.AXES) || stack.is(OrchardTag.CommonItems.C_KNIFE))){
            level.setBlock(pos, BlockRegister.OPEN_MELON_BLOCK.get().defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            level.playSound(null, pos, SoundEvents.BAMBOO_WOOD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            //注册可燃烧的方块
            FireBlock fire = (FireBlock) Blocks.FIRE;
            //树干
            fire.setFlammable(BlockRegister.FRUIT_TREE_LOG_BLOCK.get(), 5, 5);
            //树叶
            fire.setFlammable(BlockRegister.FRUIT_TREE_LEAVES_BLOCK.get(), 30, 60);
            fire.setFlammable(BlockRegister.APPLE_LEAVES_BLOCK.get(), 30, 60);
            fire.setFlammable(BlockRegister.ORANGE_LEAVES_BLOCK.get(), 30, 60);
            fire.setFlammable(BlockRegister.KIWI_LEAVES_BLOCK.get(), 30, 60);

            //注册树苗
            FruitTreeSaplingBlock.registerTransformation(ItemRegister.ORANGE.get(), BlockRegister.ORANGE_SAPLING_BLOCK);
            FruitTreeSaplingBlock.registerTransformation(ItemRegister.KIWI.get(), BlockRegister.KIWI_SAPLING_BLOCK);
            FruitTreeSaplingBlock.registerTransformation(ItemRegister.LEMON.get(), BlockRegister.LEMON_SAPLING_BLOCK);
        });
    }

    @SubscribeEvent
    public static void onBlockEntityAddBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(
                ModBlocks.DRINK_BE.get(),
                BlockRegister.STRAWBERRY_WINE_BLOCK.get(),
                BlockRegister.ORANGE_WINE_BLOCK.get(),
                BlockRegister.KIWI_WINE_BLOCK.get(),
                BlockRegister.BLUEBERRY_WINE_BLOCK.get(),
                BlockRegister.GLOW_HONEY_WINE_BLOCK.get()
        );
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        registerBucketFluidHandler(event, ItemRegister.STRAWBERRY_BUCKET.get());
        registerBucketFluidHandler(event, ItemRegister.ORANGE_BUCKET.get());
        registerBucketFluidHandler(event, ItemRegister.KIWI_BUCKET.get());
        registerBucketFluidHandler(event, ItemRegister.BLUEBERRY_BUCKET.get());
        registerBucketFluidHandler(event, ItemRegister.LEMON_BUCKET.get());
        registerBucketFluidHandler(event, ItemRegister.WATERMELON_BUCKET.get());
    }

    private static void registerBucketFluidHandler(RegisterCapabilitiesEvent event, Item targetItem) {
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidBucketWrapper(stack),
                targetItem
        );
    }
}
