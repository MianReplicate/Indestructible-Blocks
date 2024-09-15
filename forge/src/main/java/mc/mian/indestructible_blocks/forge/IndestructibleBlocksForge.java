package mc.mian.indestructible_blocks.forge;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.config.ConfigHolder;
import mc.mian.indestructible_blocks.datagen.IndestructibleDataGenerators;
import mc.mian.indestructible_blocks.forge.event.IndestructibleEvents;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(IndestructibleResources.MOD_ID)
public class IndestructibleBlocksForge {
    public static IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();;
    public IndestructibleBlocksForge() {
        IEventBus eventBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);

        IndestructibleBlocks.config = ConfigHolder.SERVER;
        IndestructibleBlocks.init();

        eventBus.register(IndestructibleEvents.class);
        modEventBus.register(IndestructibleDataGenerators.class);
    }
}