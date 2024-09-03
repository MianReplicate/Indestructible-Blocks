package mc.mian.indestructible_blocks.neoforge.event;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.util.ModUtil;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class ModEvents {
    @SubscribeEvent
    public static void playerDestroyEvent(final BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        IndestructibleBlocks.LOGGER.info("Checking if block is indestructgible");
        if(player.isCreative() && ModUtil.isInConfig(event.getState())){
            IndestructibleBlocks.LOGGER.info("Forcing break");
            ModUtil.forceDestructibility(event.getState());
        }
    }
}
