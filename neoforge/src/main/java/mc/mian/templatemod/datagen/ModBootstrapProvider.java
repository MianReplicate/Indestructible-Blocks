package mc.mian.templatemod.datagen;

import mc.mian.templatemod.datagen.bootstrap.ModJukeboxSongsProvider;
import mc.mian.templatemod.util.ModResources;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModBootstrapProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.JUKEBOX_SONG, ModJukeboxSongsProvider::bootstrap);

    public ModBootstrapProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of(ModResources.MOD_ID));
    }
}
