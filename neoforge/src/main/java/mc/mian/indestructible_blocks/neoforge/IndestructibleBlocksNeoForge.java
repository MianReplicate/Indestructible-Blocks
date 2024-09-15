package mc.mian.indestructible_blocks.neoforge;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.config.ConfigHolder;
import mc.mian.indestructible_blocks.datagen.IndestructibleDataGenerators;
import mc.mian.indestructible_blocks.neoforge.event.IndestructibleEvents;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(IndestructibleResources.MOD_ID)
public class IndestructibleBlocksNeoForge {
    public static IEventBus modEventBus;
    public IndestructibleBlocksNeoForge(IEventBus modEventBusParam) {
        modEventBus = modEventBusParam;
        IEventBus eventBus = NeoForge.EVENT_BUS;

        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        IndestructibleBlocks.config = ConfigHolder.SERVER;
        IndestructibleBlocks.init();

        eventBus.register(IndestructibleEvents.class);
        modEventBus.register(IndestructibleDataGenerators.class);
    }
}