package mc.mian.indestructible_blocks.fabric.event;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.util.ModUtil;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class ModEvents {
    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register(((world, player, pos, state, blockEntity) -> {
            IndestructibleBlocks.LOGGER.info("Checking if block is indestructgible");
            if(player.isCreative() && ModUtil.isInConfig(state)){
                IndestructibleBlocks.LOGGER.info("Forcing break");
                ModUtil.forceDestructibility(state);
            }
            return true;
        }));
    }
}
