package net.roboxgamer.tutorialmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.roboxgamer.tutorialmod.TutorialMod;
import net.roboxgamer.tutorialmod.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
  public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, TutorialMod.MODID, existingFileHelper);
  }
  
  @Override
  protected void addTags(HolderLookup.Provider pProvider) {
    this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .add(ModBlocks.RESOURCE_GENERATOR_BLOCK.get())
        .add(ModBlocks.EXAMPLE_BLOCK.get());
    
    this.tag(BlockTags.MINEABLE_WITH_AXE)
        .add(ModBlocks.MINI_CHEST_BLOCK.get())
        .add(ModBlocks.EXAMPLE_BLOCK.get());
  }
}
