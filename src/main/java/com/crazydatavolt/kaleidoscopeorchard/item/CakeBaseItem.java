package com.crazydatavolt.kaleidoscopeorchard.item;

import com.crazydatavolt.kaleidoscopeorchard.register.ItemRegister;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

public class CakeBaseItem extends Item {

    public CakeBaseItem(Properties properties) {
        super(properties.food(ItemRegister.buildFoodValue(12,2.4f).build()));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 64;
    }
}
