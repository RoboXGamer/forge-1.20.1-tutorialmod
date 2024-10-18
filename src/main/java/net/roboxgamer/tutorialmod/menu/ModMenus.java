package net.roboxgamer.tutorialmod.menu;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.roboxgamer.tutorialmod.TutorialMod.MODID;

public class ModMenus
{
  public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
  
  public static final RegistryObject<MenuType<MiniChestMenu>> MINI_CHEST_MENU = registerMenuType(
      "mini_chest_menu",
      MiniChestMenu::new
  );
  
  public static void register(IEventBus eventBus){
    MENUS.register(eventBus);
  }
  
  private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
    return MENUS.register(name, () -> IForgeMenuType.create(factory));
  }
}
