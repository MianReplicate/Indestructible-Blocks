package mc.mian.indestructible_blocks.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class IndestructibleDataGenerators {

    @SubscribeEvent
    public static void generateData(GatherDataEvent.Client ev) {
        final CompletableFuture<HolderLookup.Provider> provider = ev.getLookupProvider();
        final DataGenerator gen = ev.getGenerator();
        final PackOutput packOutput = gen.getPackOutput();

        gen.addProvider(true, new IndestructibleLangProvider(packOutput));
        gen.addProvider(true, new IndestructibleModelProvider(packOutput));
    }
}
