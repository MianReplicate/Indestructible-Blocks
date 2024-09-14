package mc.mian.indestructible_blocks.neoforge.event;

import mc.mian.indestructible_blocks.common.command.custom.IBCommand;
import mc.mian.indestructible_blocks.util.ModUtil;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class ModEvents {
    @SubscribeEvent
    public static void playerDestroyEvent(final BlockEvent.BreakEvent event){
        boolean successful = ModUtil.playerTryToBreak(event.getPlayer(), event.getState(), event.getPos());
        event.setCanceled(!successful);
    }

    @SubscribeEvent
    public static void registerCommandsEvent(final RegisterCommandsEvent event){
        IBCommand.register(event.getDispatcher(), event.getBuildContext());
    }
}
