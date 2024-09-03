package mc.mian.indestructible_blocks.common.component;

import com.mojang.serialization.Codec;
import mc.mian.indestructible_blocks.registry.DeferredRegistry;
import mc.mian.indestructible_blocks.registry.RegistrySupplier;
import mc.mian.indestructible_blocks.util.DestructibilitySetting;
import mc.mian.indestructible_blocks.util.ModResources;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public class ModComponents {
    public static final DeferredRegistry<DataComponentType<?>> DATA_COMPONENT_TYPES
            = DeferredRegistry.create(ModResources.MOD_ID, Registries.DATA_COMPONENT_TYPE);
    public static final RegistrySupplier<DataComponentType<Object>> DESTRUCTIBILITY_SETTING
            = DATA_COMPONENT_TYPES.register("destructibility_setting", () ->
            DataComponentType.builder().persistent(Codec.unit(DestructibilitySetting.BLOCK_ID))
                    .build());
}
