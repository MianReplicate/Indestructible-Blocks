package mc.mian.indestructible_blocks;

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

    public static ArrayList<BlockState> pendingRemovalBlocks = Lists.newArrayList();
    public static final Logger LOGGER = LogManager.getLogger(ModResources.MOD_ID);
    public static ModConfiguration config;

    public static void init() {
        LOGGER.info("Muahahah, we shall make every block out of bedrock material!");
        ModCreativeModeTabs.TABS.register();
        ModItems.ITEMS.register();
    }
}