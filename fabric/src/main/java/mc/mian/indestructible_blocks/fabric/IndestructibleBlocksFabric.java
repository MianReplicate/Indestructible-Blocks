package mc.mian.indestructible_blocks.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import mc.mian.indestructible_blocks.common.command.custom.IBCommand;
import mc.mian.indestructible_blocks.util.IndestructibleUtil;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.fabricmc.api.ModInitializer;
import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.config.ConfigHolder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.neoforged.fml.config.ModConfig;

public class IndestructibleBlocksFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(IndestructibleResources.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        IndestructibleBlocks.config = ConfigHolder.SERVER;
        IndestructibleBlocks.init();

        PlayerBlockBreakEvents.BEFORE.register(((world, player, pos, state, blockEntity) -> IndestructibleUtil.playerTryToBreak(player, state, pos)));
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> IBCommand.register(dispatcher, registry));
    }
}