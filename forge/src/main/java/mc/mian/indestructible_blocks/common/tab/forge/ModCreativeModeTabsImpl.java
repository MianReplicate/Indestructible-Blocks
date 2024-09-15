package mc.mian.indestructible_blocks.common.tab.forge;

import mc.mian.indestructible_blocks.common.tab.ModCreativeModeTabs;
import mc.mian.indestructible_blocks.common.item.ModItems;
import mc.mian.indestructible_blocks.util.ModResources;
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
