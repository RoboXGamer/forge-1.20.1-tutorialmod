package net.roboxgamer.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemStackHandler;
import net.roboxgamer.tutorialmod.block.entity.custom.ResourceGeneratorBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResourceGeneratorBlock extends Block implements EntityBlock {
  public ResourceGeneratorBlock(Properties properties) {
    super(properties);
  }
  
  @Override
  public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
    return new ResourceGeneratorBlockEntity(pos, state);
  }
  
  @Override
  public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
    return level.isClientSide ? null : ((level1, pos, state, blockEntity) -> ((ResourceGeneratorBlockEntity) blockEntity).tick());
  }
  
  @Override
  public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
    if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
      BlockEntity be = level.getBlockEntity(pos);
      if (be instanceof ResourceGeneratorBlockEntity blockEntity) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStackHandler inventory = blockEntity.getFilterInv();
        if (stack.isEmpty()) {
          // Get the item from the block entity and give it to the player
          if (blockEntity.getFilterItem().isEmpty()) {
            player.sendSystemMessage(Component.literal("No item in the block entity"));
            return InteractionResult.SUCCESS;
          }
          
          ItemStack extracted = inventory.extractItem(0,player.isCrouching() ? inventory.getSlotLimit(0) : 1 , false);
          player.setItemInHand(hand,extracted);
        } else {
          //  Set the item in the block entity to the item in the player's hand (assuming the blockentity can store the item)
          ItemStack toInsert = stack.copy();
          toInsert.setCount(1);
          
          ItemStack leftOver = inventory.insertItem(0,toInsert,false);
          
          ItemStack remainder = stack.copy();
          remainder.setCount(remainder.getCount() - 1);
          remainder.grow(leftOver.getCount());
          player.setItemInHand(hand,remainder);
        }
        return InteractionResult.SUCCESS;
      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide());
  }
}