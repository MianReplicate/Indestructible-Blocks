package mc.mian.templatemod.common.tab;

import dev.architectury.injectables.annotations.ExpectPlatform;
import mc.mian.templatemod.common.item.ModItems;
import mc.mian.templatemod.registry.DeferredRegistry;
import mc.mian.templatemod.registry.RegistrySupplier;
import mc.mian.templatemod.util.ModResources;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final DeferredRegistry<CreativeModeTab> TABS = DeferredRegistry.create(ModResources.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> TEMPLATE_MOD = TABS.register("template_mod", () -> createTab("template_mod"));
    public static ItemStack makeIcon() {
        return new ItemStack(ModItems.TEMPLATE_ITEM.get());
    }
    @ExpectPlatform
    public static CreativeModeTab createTab(String title){
        throw new RuntimeException("fuck off");
    }
}
