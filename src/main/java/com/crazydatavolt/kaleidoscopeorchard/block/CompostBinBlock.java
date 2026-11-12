package com.crazydatavolt.kaleidoscopeorchard.block;

import com.crazydatavolt.kaleidoscopeorchard.KaleidoscopeOrchard;
import com.crazydatavolt.kaleidoscopeorchard.config.ServerConfig;
import com.crazydatavolt.kaleidoscopeorchard.register.ItemRegister;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class CompostBinBlock extends HorizontalDirectionalBlock implements WorldlyContainerHolder {
    public static final int MIN_LEVEL = 0;
    public static final int MAX_LEVEL_1 = 11;
    public static final int MAX_LEVEL_2 = 16;
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", MIN_LEVEL, MAX_LEVEL_2);
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    VoxelShape SHAPE_BASE = Shapes.join(Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(2.0, 2.0, 2.0, 14.0, 14.0, 14.0),
            BooleanOp.ONLY_FIRST);
    VoxelShape SHAPE_CLOSE = Shapes.or(SHAPE_BASE, Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0));
    VoxelShape SHAPE_OPEN_NORTH = Shapes.or(SHAPE_BASE, Block.box(0.0, 14.0, 14.0, 16.0, 30.0, 16.0));
    VoxelShape SHAPE_OPEN_SOUTH = Shapes.or(SHAPE_BASE, Block.box(0.0, 14.0, 0.0, 16.0, 30.0, 2.0));
    VoxelShape SHAPE_OPEN_EAST  = Shapes.or(SHAPE_BASE, Block.box(0.0, 14.0, 0.0, 2.0, 30.0, 16.0));
    VoxelShape SHAPE_OPEN_WEST  = Shapes.or(SHAPE_BASE, Block.box(14.0, 14.0, 0.0, 16.0, 30.0, 16.0));

    public CompostBinBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.COMPOSTER));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LEVEL, 0).setValue(OPEN, false));
    }



    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        boolean open = state.getValue(OPEN);
        int levelVal = state.getValue(LEVEL);

        if (!tryPutIn(stack, pos, state, level, levelVal, open, player)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        return ItemInteractionResult.SUCCESS;
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int levelVal = state.getValue(LEVEL);
        boolean open = state.getValue(OPEN);

        if (!level.isClientSide) {
            if(open && levelVal == MAX_LEVEL_2){
                resetBin(level, pos, state);
                level.playSound(null, pos, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.addItem(ItemRegister.RICH_SOIL.toStack());
                return InteractionResult.SUCCESS;
            }
            boolean newOpen = !open;
            level.setBlock(pos, state.setValue(OPEN, newOpen), Block.UPDATE_ALL);
            level.playSound(null, pos, newOpen ? SoundEvents.WOODEN_TRAPDOOR_OPEN : SoundEvents.WOODEN_TRAPDOOR_CLOSE,
                    SoundSource.BLOCKS, 1.0F, 1.0F);
            if(newOpen){
                if(levelVal == MAX_LEVEL_2){
                    player.displayClientMessage(Component.literal("堆肥完成！"), true);
                }else if(levelVal >= MAX_LEVEL_1){
                    player.displayClientMessage(Component.literal("堆肥中断！"), true);
                    level.scheduleTick(pos, this, 0);
                }
            }else{
                if(levelVal < MAX_LEVEL_1){
                    player.displayClientMessage(Component.literal("堆肥材料不足！"), true);
                }else if(levelVal < MAX_LEVEL_2){
                    player.displayClientMessage(Component.literal("正在堆肥……"), true);
                    level.scheduleTick(pos, this, getTime());
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }


    public boolean tryPutIn(ItemStack stack, BlockPos pos, BlockState state, Level level, int levelVal, boolean isOpen, Player player){
        if (level.isClientSide) return false;
        if (!isOpen || levelVal >= MAX_LEVEL_1) return false;

        float chance = ComposterBlock.getValue(stack);
        if(chance>0){
            if(level.random.nextDouble() < chance * getChanceMultiplier()){
                int newLevelVal = levelVal+1;
                level.levelEvent(1500, pos, 1);
                level.playSound(null, pos, SoundEvents.COMPOSTER_FILL_SUCCESS, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, state.setValue(LEVEL, newLevelVal), Block.UPDATE_ALL);
                if(newLevelVal == MAX_LEVEL_1){
                    player.displayClientMessage(Component.literal("堆肥材料已满，盖上盖子开始堆肥！"), true);
                }
            } else {
                level.playSound(null, pos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            stack.shrink(1);
            return true;
        }
        return false;
    }

    private static void resetBin(LevelAccessor level, BlockPos pos, BlockState state) {
        level.setBlock(pos, state.setValue(LEVEL, MIN_LEVEL), Block.UPDATE_ALL);
    }


    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int levelVal = state.getValue(LEVEL);
        boolean open = state.getValue(OPEN);
        if(!open && levelVal>=MAX_LEVEL_1 && levelVal<MAX_LEVEL_2){
            level.setBlock(pos, state.setValue(LEVEL, levelVal+1), Block.UPDATE_ALL);
            level.scheduleTick(pos, this, getTime());
        }
    }

    private int getTime(){
        return ServerConfig.COMPOST_BIN_TIME.get();
    }

    private float getChanceMultiplier(){
        return ServerConfig.COMPOST_BIN_CHANCE_MULTPLIER.get().floatValue();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(state.getValue(OPEN)){
            switch(state.getValue(FACING)){
                case NORTH: return SHAPE_OPEN_NORTH;
                case SOUTH: return SHAPE_OPEN_SOUTH;
                case EAST:  return SHAPE_OPEN_EAST;
                default: return SHAPE_OPEN_WEST;
            }
        }
        return SHAPE_CLOSE;
    }

    @Override
    protected float getShadeBrightness(BlockState p_308911_, BlockGetter p_308952_, BlockPos p_308918_) {
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState p_309084_, BlockGetter p_309133_, BlockPos p_309097_) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(LEVEL, 0).setValue(OPEN, false).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL,OPEN,FACING);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        KaleidoscopeOrchard.addTooltip(components, this.getDescriptionId());
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    //============================================//

    @Override
    public WorldlyContainer getContainer(BlockState state, LevelAccessor level, BlockPos pos) {
        int i = state.getValue(LEVEL);
        return i == MAX_LEVEL_2 ? new OutputContainer(state, level, pos, new ItemStack(ItemRegister.RICH_SOIL.get())) : (i < MAX_LEVEL_1 ? new InputContainer(state, level, pos) : new EmptyContainer());
    }

    private BlockState tryHopperPutIn(LevelAccessor level, BlockPos pos, BlockState state, ItemStack stack) {
        if (level.isClientSide()) return state;
        int levelVal = state.getValue(LEVEL);
        if (levelVal >= MAX_LEVEL_1) return state;
        boolean open = state.getValue(OPEN);

        float chance = ComposterBlock.getValue(stack);
        if(chance>0){
            if(level.getRandom().nextDouble() < chance * getChanceMultiplier()){
                int newLevelVal = levelVal+1;
                if(newLevelVal == MAX_LEVEL_1 && !open){
                    level.scheduleTick(pos, this, getTime());
                }
                level.levelEvent(1500, pos, 1);
                level.playSound(null, pos, SoundEvents.COMPOSTER_FILL_SUCCESS, SoundSource.BLOCKS, 1.0F, 1.0F);
                stack.shrink(1);
                return state.setValue(LEVEL, newLevelVal);
            }
            level.playSound(null, pos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            stack.shrink(1);
        }
        return state;
    }

    static class OutputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public OutputContainer(BlockState state, LevelAccessor level, BlockPos pos, ItemStack stack) {
            super(stack);
            this.state = state;
            this.level = level;
            this.pos = pos;
        }

        public int getMaxStackSize() {
            return 1;
        }

        public int[] getSlotsForFace(Direction side) {
            return side == Direction.DOWN ? new int[]{0} : new int[0];
        }

        public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
            return false;
        }

        public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
            return true;
        }

        public void setChanged() {
            resetBin(level, pos, state);
            this.changed = true;
        }
    }

    class InputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public InputContainer(BlockState state, LevelAccessor level, BlockPos pos) {
            super(1);
            this.state = state;
            this.level = level;
            this.pos = pos;
        }

        public int getMaxStackSize() {
            return 1;
        }

        public int[] getSlotsForFace(Direction side) {
            return new int[]{0};
        }

        public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
            return ComposterBlock.getValue(itemStack) > 0.0F;
        }

        public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
            return false;
        }

        public void setChanged() {
            ItemStack itemstack = this.getItem(0);
            if (!itemstack.isEmpty()) {
                this.changed = true;
                BlockState blockstate = tryHopperPutIn(level, pos, state, itemstack);
                if (blockstate != state) {
                    level.setBlock(pos, blockstate, Block.UPDATE_ALL);
                }
                this.removeItemNoUpdate(0);
            }

        }
    }

    static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
        public EmptyContainer() {
            super(0);
        }

        public int[] getSlotsForFace(Direction side) {
            return new int[0];
        }

        public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
            return false;
        }

        public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
            return false;
        }
    }
}
