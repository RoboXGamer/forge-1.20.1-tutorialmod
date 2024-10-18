package net.roboxgamer.tutorialmod.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.roboxgamer.tutorialmod.block.entity.ModBlockEntities;
import net.roboxgamer.tutorialmod.menu.MiniChestMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MiniChestBlockEntity extends BlockEntity implements MenuProvider {
  public static final Component TITLE = Component.translatable("block.tutorialmod.mini_chest_block");
  
  public MiniChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
    super(ModBlockEntities.MINI_CHEST_BE.get(),pPos, pBlockState);
  }
  
  ItemStackHandler inv = new ItemStackHandler(1){
    @Override
    protected void onContentsChanged(int slot) {
      MiniChestBlockEntity.this.setChanged();
    }
  };
  
  public ItemStackHandler getInvHandler(){
    return this.inv;
  }
  
  public SimpleContainer getInvContainer() {
    var t = new SimpleContainer(inv.getSlots());
    for (int i = 0; i < inv.getSlots(); i++) {
      t.setItem(i, inv.getStackInSlot(i));
    }
    return t;
  }
  
  LazyOptional<IItemHandler> lazyInvHandler = LazyOptional.empty();
  
  @Override
  public void onLoad() {
    super.onLoad();
    lazyInvHandler = LazyOptional.of(() -> inv);
  }
  
  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    lazyInvHandler.invalidate();
  }
  
  @Override
  public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    if (cap == ForgeCapabilities.ITEM_HANDLER) {
      return lazyInvHandler.cast();
    }
    return super.getCapability(cap, side);
  }
  
  @Override
  protected void saveAdditional(@NotNull CompoundTag tag) {
    super.saveAdditional(tag);
    tag.put("inv",this.inv.serializeNBT());
  }
  
  @Override
  public void load(@NotNull CompoundTag tag) {
    super.load(tag);
    this.inv.deserializeNBT(tag.getCompound("inv"));
  }
  
  @Override
  public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }
  
  @Override
  public @NotNull CompoundTag getUpdateTag() {
    var t = new CompoundTag();
    t.put("inv", this.inv.serializeNBT());
    return t;
  }
  
  // Menu
  @Override
  public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory,
                                                    @NotNull Player player) {
    return new MiniChestMenu(containerId, playerInventory, this);
  }
  
  @Override
  public @NotNull Component getDisplayName() {
    return TITLE;
  }
}
