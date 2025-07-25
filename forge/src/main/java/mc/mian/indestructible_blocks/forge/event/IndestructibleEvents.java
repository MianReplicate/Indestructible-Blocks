package mc.mian.indestructible_blocks.forge.event;

import mc.mian.indestructible_blocks.common.command.custom.IBCommand;
import mc.mian.indestructible_blocks.util.IndestructibleUtil;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

public class IndestructibleEvents {
    @SubscribeEvent
    public static boolean playerDestroyEvent(final BlockEvent.BreakEvent event){
        boolean successful = IndestructibleUtil.playerTryToBreak(event.getPlayer(), event.getState(), event.getPos());
        return !successful;
    }

    @SubscribeEvent
    public static void registerCommandsEvent(final RegisterCommandsEvent event){
        IBCommand.register(event.getDispatcher(), event.getBuildContext());
    }
}
