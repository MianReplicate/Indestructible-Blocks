package mc.mian.indestructible_blocks.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import mc.mian.indestructible_blocks.util.ModResources;
import mc.mian.indestructible_blocks.util.ModUtil;
import net.fabricmc.api.ModInitializer;
import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.config.ConfigHolder;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.neoforged.fml.config.ModConfig;

public class IndestructibleBlocksFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(ModResources.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        IndestructibleBlocks.config = ConfigHolder.SERVER;
        IndestructibleBlocks.init();

        PlayerBlockBreakEvents.BEFORE.register(((world, player, pos, state, blockEntity) -> ModUtil.playerTryToBreak(player, state)));
    }
}