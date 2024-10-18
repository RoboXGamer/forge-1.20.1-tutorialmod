package net.roboxgamer.tutorialmod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.roboxgamer.tutorialmod.TutorialMod;
import net.roboxgamer.tutorialmod.block.ModBlocks;

public class ModItemModelProvider extends ItemModelProvider {
  public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, TutorialMod.MODID, existingFileHelper);
  }
  
  @Override
  protected void registerModels() {
    simpleBlockItemBlockTexture(ModBlocks.EXAMPLE_BLOCK);
    simpleBlockItemBlockTexture(ModBlocks.RESOURCE_GENERATOR_BLOCK);
    simpleBlockItemBlockTexture(ModBlocks.MINI_CHEST_BLOCK);
    
  }
  
  private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
    return withExistingParent(item.getId().getPath(),
                              new ResourceLocation("item/generated")).texture("layer0",
                                                                              new ResourceLocation(TutorialMod.MODID,
                                                                                                   "item/" + item.getId().getPath()));
  }
  
  private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
    return withExistingParent(item.getId().getPath(),
                              new ResourceLocation("item/generated")).texture("layer0",
                                                                              new ResourceLocation(TutorialMod.MODID,
                                                                                                   "item/" + item.getId().getPath()));
  }
  
  private <T extends Block> ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<T> item) {
    return withExistingParent(item.getId().getPath(),
                              new ResourceLocation("item/generated")).texture("layer0",
                                                                              new ResourceLocation(TutorialMod.MODID,
                                                                                                   "block/" + item.getId().getPath()));
  }
}
