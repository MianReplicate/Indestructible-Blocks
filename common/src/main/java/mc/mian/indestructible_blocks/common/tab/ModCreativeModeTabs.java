package mc.mian.indestructible_blocks.common.tab;

import dev.architectury.injectables.annotations.ExpectPlatform;
import mc.mian.indestructible_blocks.common.item.ModItems;
import mc.mian.indestructible_blocks.registry.DeferredRegistry;
import mc.mian.indestructible_blocks.registry.RegistrySupplier;
import mc.mian.indestructible_blocks.util.ModResources;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final DeferredRegistry<CreativeModeTab> TABS = DeferredRegistry.create(ModResources.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> INDESTRUCTIBLE_BLOCKS = TABS.register("indestructible_blocks", () -> createTab("indestructible_blocks"));
    public static ItemStack makeIcon() {
        return new ItemStack(ModItems.DESTRUCTIBILITY_EDITOR.get());
    }
    @ExpectPlatform
    public static CreativeModeTab createTab(String title){
        throw new RuntimeException("fuck off");
    }
}
