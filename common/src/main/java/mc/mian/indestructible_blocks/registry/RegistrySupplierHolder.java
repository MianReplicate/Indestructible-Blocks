package mc.mian.indestructible_blocks.registry;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RegistrySupplierHolder<R, T extends R> implements Holder<R>, Supplier<T> {
    protected final ResourceKey<R> key;
    private @Nullable Holder<R> holder = null;

    public static <R, T extends R> RegistrySupplierHolder<R, T> create(ResourceKey<? extends Registry<R>> registryKey, ResourceLocation valueName) {
        return create(ResourceKey.create(registryKey, valueName));
    }

    public static <R, T extends R> RegistrySupplierHolder<R, T> create(ResourceLocation registryName, ResourceLocation valueName) {
        return create(ResourceKey.createRegistryKey(registryName), valueName);
    }

    public static <R, T extends R> RegistrySupplierHolder<R, T> create(ResourceKey<R> key) {
        return new RegistrySupplierHolder(key);
    }

    protected RegistrySupplierHolder(ResourceKey<R> key) {
        this.key = Objects.requireNonNull(key);
        this.bind(false);
    }

    public T value() {
        this.bind(true);
        if (this.holder == null) {
            throw new NullPointerException("Trying to access unbound value: " + this.key);
        } else {
            return (T) this.holder.value();
        }
    }

    public T get() {
        return this.value();
    }

    public Optional<T> asOptional() {
        return this.isBound() ? Optional.of(this.value()) : Optional.empty();
    }

    protected @Nullable Registry<R> getRegistry() {
        return (Registry) BuiltInRegistries.REGISTRY.get(this.key.registry());
    }

    protected final void bind(boolean throwOnMissingRegistry) {
        if (this.holder == null) {
            Registry<R> registry = this.getRegistry();
            if (registry != null) {
                this.holder = registry.getHolder(this.key).orElse(null);
            } else if (throwOnMissingRegistry) {
                String var10002 = String.valueOf(this);
                throw new IllegalStateException("Registry not present for " + var10002 + ": " + this.key.registry());
            }

        }
    }

    public ResourceLocation getId() {
        return this.key.location();
    }

    public ResourceKey<R> getKey() {
        return this.key;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof RegistrySupplierHolder<?, ?> rh && rh.key == this.key;
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "DeferredHolder{%s}", this.key);
    }

    public boolean isBound() {
        this.bind(false);
        return this.holder != null && this.holder.isBound();
    }

    public boolean is(ResourceLocation location) {
        return location.equals(this.key.location());
    }

    public boolean is(ResourceKey<R> resourceKey) {
        return resourceKey == this.key;
    }

    public boolean is(Predicate<ResourceKey<R>> predicate) {
        return predicate.test(this.key);
    }

    public boolean is(TagKey<R> tagKey) {
        this.bind(false);
        return this.holder != null && this.holder.is(tagKey);
    }

    public Stream<TagKey<R>> tags() {
        this.bind(false);
        return this.holder != null ? this.holder.tags() : Stream.empty();
    }

    public Either<ResourceKey<R>, R> unwrap() {
        return Either.left(this.key);
    }

    public Optional<ResourceKey<R>> unwrapKey() {
        return Optional.of(this.key);
    }

    public Holder.Kind kind() {
        return Holder.Kind.REFERENCE;
    }

    public boolean canSerializeIn(HolderOwner<R> owner) {
        this.bind(false);
        return this.holder != null && this.holder.canSerializeIn(owner);
    }
}
