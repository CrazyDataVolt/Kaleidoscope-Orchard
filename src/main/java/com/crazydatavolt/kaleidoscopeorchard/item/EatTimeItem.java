package com.crazydatavolt.kaleidoscopeorchard.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EatTimeItem extends Item {
    int time;

    public EatTimeItem(Properties properties, int eatTime) {
        super(properties);
        time = eatTime;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return time;
    }
}
