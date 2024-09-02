package mc.mian.templatemod.common.tab.fabric;

import mc.mian.templatemod.common.tab.ModCreativeModeTabs;
import mc.mian.templatemod.common.item.ModItems;
import mc.mian.templatemod.util.ModResources;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeModeTabsImpl {
    public static CreativeModeTab createTab(String title){
        return FabricItemGroup.builder()
                .icon(ModCreativeModeTabs::makeIcon)
                .title(Component.translatable("itemGroup."+ ModResources.MOD_ID+"."+title))
                .displayItems((itemDisplayParameters, output) -> ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                .build();
    }
}
