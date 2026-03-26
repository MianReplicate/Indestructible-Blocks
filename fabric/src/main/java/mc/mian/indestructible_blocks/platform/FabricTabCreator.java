package mc.mian.indestructible_blocks.platform;

import mc.mian.indestructible_blocks.common.item.IndestructibleItems;
import mc.mian.indestructible_blocks.common.tab.IndestructibleTabs;
import mc.mian.indestructible_blocks.platform.services.ITabCreator;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class FabricTabCreator implements ITabCreator {
    public CreativeModeTab createTab(String title){
        return FabricCreativeModeTab.builder()
                .icon(IndestructibleTabs::makeIcon)
                .title(Component.translatable("itemGroup."+ IndestructibleResources.MOD_ID+"."+title))
                .displayItems((itemDisplayParameters, output) -> IndestructibleItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                .build();
    }
}
