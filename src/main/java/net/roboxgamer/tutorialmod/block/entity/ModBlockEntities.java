package net.roboxgamer.tutorialmod.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.roboxgamer.tutorialmod.TutorialMod;
import net.roboxgamer.tutorialmod.block.ModBlocks;
import net.roboxgamer.tutorialmod.block.entity.custom.MiniChestBlockEntity;
import net.roboxgamer.tutorialmod.block.entity.custom.ResourceGeneratorBlockEntity;

public class ModBlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
      DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TutorialMod.MODID);
  
  public static final RegistryObject<BlockEntityType<ResourceGeneratorBlockEntity>> RESOURCE_GENERATOR_BE =
      BLOCK_ENTITIES.register("resource_generator_be", () -> BlockEntityType.Builder.
          of(ResourceGeneratorBlockEntity::new,
             ModBlocks.RESOURCE_GENERATOR_BLOCK.get())
          .build(null));
  
  public static final RegistryObject<BlockEntityType<MiniChestBlockEntity>> MINI_CHEST_BE =
      BLOCK_ENTITIES.register("mini_chest_be", () -> BlockEntityType.Builder.
          of(MiniChestBlockEntity::new,
             ModBlocks.MINI_CHEST_BLOCK.get())
          .build(null));
  
  public static void register(IEventBus eventBus) {
    BLOCK_ENTITIES.register(eventBus);
  }
}
