package mc.mian.indestructible_blocks.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import mc.mian.indestructible_blocks.common.command.custom.IBCommand;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import mc.mian.indestructible_blocks.util.IndestructibleUtil;
import net.fabricmc.api.ModInitializer;
import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.config.ConfigHolder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraftforge.fml.config.ModConfig;

public class IndestructibleBlocksFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register(IndestructibleResources.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        IndestructibleBlocks.config = ConfigHolder.SERVER;
        IndestructibleBlocks.init();

        PlayerBlockBreakEvents.BEFORE.register(((world, player, pos, state, blockEntity) -> IndestructibleUtil.playerTryToBreak(player, state, pos)));
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> IBCommand.register(dispatcher, registry));
    }
}