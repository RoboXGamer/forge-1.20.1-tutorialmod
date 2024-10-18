package net.roboxgamer.tutorialmod.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.roboxgamer.tutorialmod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
  public ModBlockLootTables() {
    super(Set.of(), FeatureFlags.REGISTRY.allFlags());
  }
  @Override
  protected void generate() {
    dropSelf(ModBlocks.EXAMPLE_BLOCK.get());
    dropSelf(ModBlocks.MINI_CHEST_BLOCK.get());
    dropSelf(ModBlocks.RESOURCE_GENERATOR_BLOCK.get());
  }
  
  @Override
  protected @NotNull Iterable<Block> getKnownBlocks() {
    return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
  }
}
