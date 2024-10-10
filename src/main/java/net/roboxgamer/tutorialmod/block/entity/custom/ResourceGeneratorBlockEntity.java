package net.roboxgamer.tutorialmod.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.roboxgamer.tutorialmod.TutorialMod;
import net.roboxgamer.tutorialmod.block.entity.ModBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResourceGeneratorBlockEntity extends BlockEntity {
  private int tc = 0;
  
  private int addedCount = 0;
  
  public ResourceGeneratorBlockEntity(BlockPos pos, BlockState blockState) {
    super(ModBlockEntities.RESOURCE_GENERATOR_BE.get(), pos, blockState);
  }
  
  class CustomItemStackHandler extends ItemStackHandler {
    public CustomItemStackHandler(int size) {
      super(size);
    }
    
    @Override
    protected void onContentsChanged(int slot) {
      ResourceGeneratorBlockEntity.this.setChanged();
    }
  }
  
  CustomItemStackHandler inv = new CustomItemStackHandler(1000){
    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
      return stack;
    }
  };
  CustomItemStackHandler filterInv = new CustomItemStackHandler(1);
  
  public void setItem(ItemStack stack) {
    this.filterInv.setStackInSlot(0, stack);
  }
  
  public ItemStackHandler getInv() {
    return this.inv;
  }
  
  public ItemStackHandler getFilterInv() {
    return this.filterInv;
  }
  
  public ItemStack getFilterItem() {
    return this.filterInv.getStackInSlot(0);
  }
  
  public ItemStack getFilterItemCopy(){
    return this.filterInv.getStackInSlot(0).copy();
  }
  
  public void tick() {
    this.tc++;
    
    Level level = this.getLevel();
    if (this.level == null || this.level.isClientSide() || !(this.level instanceof ServerLevel slevel))
      return;
    
    if (everyMinute()) {
      this.tc = 0;
      addedCount = 0;
      if (getFilterItem().isEmpty()) return;
      
      // Try to generate 5000 items of the filter item set
      int totalToAdd = 5000;
      
      for (int i = 0; i < inv.getSlots(); i++) {
        if (addedCount >= totalToAdd) break; // Stop once we've added 5000 items
        ItemStack stack = inv.getStackInSlot(i);
        
        // If the slot is empty, add a new stack of Gold Ingots
        if (stack.isEmpty()) {
          // Calculate how many we can add to this empty slot
          int toAdd = Math.min(totalToAdd - addedCount, 64); // Add up to 64 per slot
          inv.setStackInSlot(i, new ItemStack(getFilterItemCopy().getItemHolder(), toAdd));
          addedCount += toAdd;
          
        } else if (stack.getItem() == getFilterItemCopy().getItem() && stack.getCount() < stack.getMaxStackSize()) {
          int currentCount = stack.getCount();
          int maxAddable = stack.getMaxStackSize() - currentCount;
          int toAdd = Math.min(totalToAdd - addedCount, maxAddable);
          
          // Add to the existing stack rather than replacing it
          stack.grow(toAdd);
          addedCount += toAdd;
        }
      }
      
      // Log the amount of generated items
      if (addedCount > 0) {
        TutorialMod.LOGGER.debug("Generated {} items!", addedCount);
      }
    }
  }
  
  private boolean everyMinute() {
    return (this.tc % (20 * 60)) == 0;
  }
  
  public IItemHandler getCapabilityHandler(Direction side) {
    return this.inv;
  }
  
  @Override
  protected void saveAdditional(@NotNull CompoundTag tag) {
    super.saveAdditional(tag);
    tag.put("inv", this.inv.serializeNBT());
    tag.put("filterInv", this.filterInv.serializeNBT());
  }
  
  @Override
  public void load(@NotNull CompoundTag tag) {
    super.load(tag);
    this.inv.deserializeNBT(tag.getCompound("inv"));
    this.filterInv.deserializeNBT(tag.getCompound("filterInv"));
  }
  
  @Override
  public @NotNull CompoundTag getUpdateTag() {
    return this.saveWithoutMetadata();
  }
  
  @Override
  public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }
}
