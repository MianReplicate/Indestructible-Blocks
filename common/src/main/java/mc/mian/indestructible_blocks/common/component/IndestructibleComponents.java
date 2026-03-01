package mc.mian.indestructible_blocks.common.component;

import mc.mian.indestructible_blocks.registry.DeferredRegistry;
import mc.mian.indestructible_blocks.registry.RegistrySupplier;
import mc.mian.indestructible_blocks.util.DestructibilitySetting;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public class IndestructibleComponents {
    public static final DeferredRegistry<DataComponentType<?>> DATA_COMPONENT_TYPES
            = DeferredRegistry.create(IndestructibleResources.MOD_ID, Registries.DATA_COMPONENT_TYPE);
    public static final RegistrySupplier<DataComponentType<DestructibilitySetting>> DESTRUCTIBILITY_SETTING
            = DATA_COMPONENT_TYPES.register("destructibility_setting", () ->
            DataComponentType.<DestructibilitySetting>builder().persistent(DestructibilitySetting.CODEC).networkSynchronized(DestructibilitySetting.STREAM_CODEC)
                    .build());
}
