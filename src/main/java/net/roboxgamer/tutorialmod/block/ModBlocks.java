package net.roboxgamer.tutorialmod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.roboxgamer.tutorialmod.block.custom.ResourceGeneratorBlock;
import net.roboxgamer.tutorialmod.item.ModItems;

import java.util.function.Supplier;

import static net.roboxgamer.tutorialmod.TutorialMod.MODID;

public class ModBlocks {
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
  
  public static final RegistryObject<ResourceGeneratorBlock> RESOURCE_GENERATOR_BLOCK = registerBlock("resource_generator_block", ()->
      new ResourceGeneratorBlock(BlockBehaviour.Properties.of().sound(SoundType.AMETHYST).strength(1.0F, 1.0F)));
  
  public static final RegistryObject<Block> EXAMPLE_BLOCK = registerBlock(
      "example_block",
      ()-> new Block(BlockBehaviour.Properties.of().mapColor(
          MapColor.STONE))
  );
  
  private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn);
    return toReturn;
  }
  
  public static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
    ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
  }
  
  
  public static void register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }
}
