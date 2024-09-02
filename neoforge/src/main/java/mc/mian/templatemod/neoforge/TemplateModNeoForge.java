package mc.mian.templatemod.neoforge;

import mc.mian.templatemod.TemplateMod;
import mc.mian.templatemod.config.ConfigHolder;
import mc.mian.templatemod.datagen.ModDataGenerators;
import mc.mian.templatemod.util.ModResources;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ModResources.MOD_ID)
public class TemplateModNeoForge {
    public static IEventBus modEventBus;
    public TemplateModNeoForge(IEventBus modEventBusParam) {
        modEventBus = modEventBusParam;
        IEventBus eventBus = NeoForge.EVENT_BUS;

        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        TemplateMod.config = ConfigHolder.SERVER;
        TemplateMod.init();

        modEventBus.register(ModDataGenerators.class);
    }
}