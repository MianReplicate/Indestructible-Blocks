package mc.mian.indestructible_blocks;

import com.google.common.collect.Lists;
import mc.mian.indestructible_blocks.common.component.IndestructibleComponents;
import mc.mian.indestructible_blocks.common.item.IndestructibleItems;
import mc.mian.indestructible_blocks.common.tab.IndestructibleTabs;
import mc.mian.indestructible_blocks.config.IndestructibleConfiguration;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class IndestructibleBlocks {

    public static ArrayList<BlockState> pendingRemovalBlocks = Lists.newArrayList();
    public static final Logger LOGGER = LogManager.getLogger(IndestructibleResources.MOD_ID);
    public static IndestructibleConfiguration config;

    public static void init() {
        LOGGER.info("Muahahah, we shall make every block out of bedrock material!");
        IndestructibleTabs.TABS.register();
        IndestructibleComponents.DATA_COMPONENT_TYPES.register();
        IndestructibleItems.ITEMS.register();
    }
}