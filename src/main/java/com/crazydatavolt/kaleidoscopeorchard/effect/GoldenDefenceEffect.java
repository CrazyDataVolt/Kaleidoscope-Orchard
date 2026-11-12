package com.crazydatavolt.kaleidoscopeorchard.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class GoldenDefenceEffect extends MobEffect {
    public GoldenDefenceEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        float currentAbsorption = livingEntity.getAbsorptionAmount();
        livingEntity.setAbsorptionAmount(currentAbsorption + 1);
        return true;
    }

    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 100 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
