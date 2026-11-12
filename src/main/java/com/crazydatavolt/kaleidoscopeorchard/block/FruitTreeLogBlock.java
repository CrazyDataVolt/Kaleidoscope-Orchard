package com.crazydatavolt.kaleidoscopeorchard.block;

import com.crazydatavolt.kaleidoscopeorchard.register.BlockRegister;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class FruitTreeLogBlock extends RotatedPillarBlock {
    Block block;

    public FruitTreeLogBlock(Block block) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));
        this.block = block;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility ability, boolean simulate) {
        if (ability.equals(ItemAbilities.AXE_STRIP)) {
            return block.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        }
        return null;
    }
}
