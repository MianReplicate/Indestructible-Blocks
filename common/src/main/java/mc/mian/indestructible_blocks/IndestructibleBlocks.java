package mc.mian.indestructible_blocks;

import mc.mian.indestructible_blocks.common.component.ModComponents;
import mc.mian.indestructible_blocks.common.tab.ModCreativeModeTabs;
import mc.mian.indestructible_blocks.common.item.ModItems;
import mc.mian.indestructible_blocks.config.ModConfiguration;
import mc.mian.indestructible_blocks.util.ModResources;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.compress.utils.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class IndestructibleBlocks {

    //TODO: Make a similar savable list to this but one that allows blocks to be removed regardless of their indestructibility state
    public static ArrayList<BlockState> pendingRemovalBlocks = Lists.newArrayList();
    public static final Logger LOGGER = LogManager.getLogger(ModResources.MOD_ID);
    public static ModConfiguration config;

    public static void init() {
        LOGGER.info("Muahahah, we shall make every block out of bedrock material!");
        ModCreativeModeTabs.TABS.register();
        ModComponents.DATA_COMPONENT_TYPES.register();
        ModItems.ITEMS.register();
    }
}