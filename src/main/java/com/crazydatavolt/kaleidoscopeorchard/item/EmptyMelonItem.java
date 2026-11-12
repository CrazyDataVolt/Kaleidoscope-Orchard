package com.crazydatavolt.kaleidoscopeorchard.item;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.google.common.base.Suppliers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class EmptyMelonItem extends BlockItem implements Equipable {
    private final Supplier<ItemAttributeModifiers> defaultModifiers;

    public EmptyMelonItem(Block block, Properties properties) {
        super(block, properties);
        this.defaultModifiers = Suppliers.memoize(() -> ItemAttributeModifiers.builder()
                .add(Attributes.ARMOR,
                        new AttributeModifier(
                                KaleidoscopeOrchard.path("empty_melon"),
                                2.0,
                                AttributeModifier.Operation.ADD_VALUE
                        ), EquipmentSlotGroup.HEAD
                ).build());
    }


    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, level, player, hand);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return this.defaultModifiers.get();
    }
}
