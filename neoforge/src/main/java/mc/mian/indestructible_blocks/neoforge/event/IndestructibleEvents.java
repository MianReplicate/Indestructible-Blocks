package mc.mian.indestructible_blocks.neoforge.event;

import mc.mian.indestructible_blocks.common.command.custom.IBCommand;
import mc.mian.indestructible_blocks.util.IndestructibleUtil;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

public class IndestructibleEvents {
    @SubscribeEvent
    public static void playerDestroyEvent(final BreakBlockEvent event){
        if(event.getLevel().isClientSide())
            return;

        boolean successful = IndestructibleUtil.playerTryToBreak(event.getPlayer(), event.getState(), event.getPos());
        event.setCanceled(!successful);
    }

    @SubscribeEvent
    public static void registerCommandsEvent(final RegisterCommandsEvent event){
        IBCommand.register(event.getDispatcher(), event.getBuildContext());
    }
}
