package mc.mian.indestructible_blocks.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import mc.mian.indestructible_blocks.common.command.custom.IBCommand;
import mc.mian.indestructible_blocks.util.ModResources;
import mc.mian.indestructible_blocks.util.ModUtil;
import net.fabricmc.api.ModInitializer;
import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.config.ConfigHolder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraftforge.fml.config.ModConfig;

public class IndestructibleBlocksFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register(ModResources.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        IndestructibleBlocks.config = ConfigHolder.SERVER;
        IndestructibleBlocks.init();

        PlayerBlockBreakEvents.BEFORE.register(((world, player, pos, state, blockEntity) -> ModUtil.playerTryToBreak(player, state, pos)));
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> IBCommand.register(dispatcher, registry));
    }
}