package com.crazydatavolt.kaleidoscopeorchard.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StemBlock.class)
public interface StemBlockAccessorMixin {
    @Accessor
    ResourceKey<Block> getFruit();
    @Accessor
    ResourceKey<Block> getAttachedStem();
}
