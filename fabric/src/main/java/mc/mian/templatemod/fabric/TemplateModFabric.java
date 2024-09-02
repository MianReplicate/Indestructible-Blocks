package mc.mian.templatemod.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import mc.mian.templatemod.util.ModResources;
import net.fabricmc.api.ModInitializer;
import mc.mian.templatemod.TemplateMod;
import mc.mian.templatemod.config.ConfigHolder;
import net.neoforged.fml.config.ModConfig;

public class TemplateModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(ModResources.MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        TemplateMod.config = ConfigHolder.SERVER;
        TemplateMod.init();
    }
}