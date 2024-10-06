package mc.mian.indestructible_blocks.forge;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.config.ConfigHolder;
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
    public static final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static final IEventBus commonEventBus = MinecraftForge.EVENT_BUS;

    public IndestructibleBlocksForge() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);

        IndestructibleBlocks.config = ConfigHolder.SERVER;
        IndestructibleBlocks.init();

        commonEventBus.register(IndestructibleEvents.class);
    }
}