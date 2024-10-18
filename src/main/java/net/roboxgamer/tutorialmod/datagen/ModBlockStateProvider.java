package net.roboxgamer.tutorialmod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.roboxgamer.tutorialmod.TutorialMod;
import net.roboxgamer.tutorialmod.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
  public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, TutorialMod.MODID, exFileHelper);
  }
  
  @Override
  protected void registerStatesAndModels() {
    blockWithItem(ModBlocks.EXAMPLE_BLOCK);
    blockWithItem(ModBlocks.RESOURCE_GENERATOR_BLOCK);
    
    blockWithItem(ModBlocks.MINI_CHEST_BLOCK);
  }
  
  private <T extends Block> void blockWithItem(RegistryObject<T> blockRegistryObject) {
    simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
  }
}
