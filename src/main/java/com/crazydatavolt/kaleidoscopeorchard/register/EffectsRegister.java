package com.crazydatavolt.kaleidoscopeorchard.register;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.crazydatavolt.kaleidoscopeorchard.effect.GoldenDefenceEffect;
import com.crazydatavolt.kaleidoscopeorchard.effect.HitRecoverEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredRegister;


public class EffectsRegister {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS=
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, KaleidoscopeOrchard.MODID);

    public static final Holder<MobEffect> HIT_RECOVER_EFFECT = MOB_EFFECTS.register("hit_recover",()->
            new HitRecoverEffect(MobEffectCategory.BENEFICIAL,0xA8F7FF));

    public static final Holder<MobEffect> GD_EFFECT = MOB_EFFECTS.register("golden_defence",()->
            new GoldenDefenceEffect(MobEffectCategory.BENEFICIAL,0xA8F7FF)
                    .addAttributeModifier(Attributes.MAX_ABSORPTION, KaleidoscopeOrchard.path("effect.golden_defence"), 4.0, AttributeModifier.Operation.ADD_VALUE)
    );
}
