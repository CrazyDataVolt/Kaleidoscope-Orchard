package com.crazydatavolt.kaleidoscopeorchard.item;

import com.github.ysbbbbbb.kaleidoscopecookery.api.item.IHasContainer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import com.github.ysbbbbbb.kaleidoscopecookery.item.TeacupItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class HotCocoItem extends TeacupItem implements IHasContainer {

    public HotCocoItem(Block block) {
        super(block, List.of());
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            List<MobEffectInstance> harmfulEffects = entity.getActiveEffects().stream()
                    .filter(effect -> effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL)
                    .toList();
            if (!harmfulEffects.isEmpty()) {
                MobEffectInstance randomEffect = harmfulEffects.get(level.random.nextInt(harmfulEffects.size()));
                entity.removeEffect(randomEffect.getEffect());
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public Item getContainerItem() {
        return super.getContainerItem();
    }
}
