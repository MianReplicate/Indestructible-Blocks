package mc.mian.indestructible_blocks.neoforge.event;

import mc.mian.indestructible_blocks.util.ModUtil;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class ModEvents {
    @SubscribeEvent
    public static void playerDestroyEvent(final BlockEvent.BreakEvent event){
        ModUtil.playerTryToBreak(event.getPlayer(), event.getState(), event.getPos());
    }
}
