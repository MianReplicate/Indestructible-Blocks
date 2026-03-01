package mc.mian.indestructible_blocks.platform;

import mc.mian.indestructible_blocks.platform.services.IRegistryCreator;
import mc.mian.indestructible_blocks.registry.DeferredRegistry;
import mc.mian.indestructible_blocks.registry.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class FabricRegistryCreator implements IRegistryCreator {
    public <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static class Impl<T> extends DeferredRegistry<T> {

        private final String modid;
        private final Registry<T> registry;
        private final List<RegistrySupplier<T>> entries;
        private final ResourceKey resourceKey;

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this.modid = modid;
            this.registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(resourceKey.identifier()).orElseThrow(() -> new NullPointerException("Registry " + resourceKey + " not found!")).value();
            this.entries = new ArrayList<>();
            this.resourceKey = resourceKey;
        }

        @Override
        public void register() {
            this.registry.registryLifecycle();
        }

        @Override
        public <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier) {
            Identifier registeredId = Identifier.fromNamespaceAndPath(this.modid, id);
            RegistrySupplier<R> registrySupplier = new RegistrySupplier<>(registeredId, Registry.register(this.registry, registeredId, supplier.get()));
            this.entries.add((RegistrySupplier<T>) registrySupplier);
            return registrySupplier;
        }

        @Override
        public Collection<RegistrySupplier<T>> getEntries() {
            return this.entries;
        }
    }
}
