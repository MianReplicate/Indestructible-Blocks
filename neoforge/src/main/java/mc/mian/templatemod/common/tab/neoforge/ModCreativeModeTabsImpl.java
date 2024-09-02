package mc.mian.templatemod.common.tab.neoforge;

import mc.mian.templatemod.common.tab.ModCreativeModeTabs;
import mc.mian.templatemod.common.item.ModItems;
import mc.mian.templatemod.util.ModResources;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeModeTabsImpl {
    public static CreativeModeTab createTab(String title){
        return CreativeModeTab.builder()
                .icon(ModCreativeModeTabs::makeIcon)
                .title(Component.translatable("itemGroup."+ ModResources.MOD_ID+"."+title))
                .displayItems((itemDisplayParameters, output) -> ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                .build();
    }
}
