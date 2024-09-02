package mc.mian.templatemod.common.sound;

import mc.mian.templatemod.registry.DeferredRegistry;
import mc.mian.templatemod.registry.RegistrySupplier;
import mc.mian.templatemod.registry.RegistrySupplierHolder;
import mc.mian.templatemod.util.ModResources;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final DeferredRegistry<SoundEvent> SOUND_EVENTS = DeferredRegistry.create(ModResources.MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplierHolder<SoundEvent, SoundEvent> TEMPLATE_SOUND_EVENT = SOUND_EVENTS.registerForHolder(
            "template_sound_event", () -> SoundEvent.createVariableRangeEvent(ModResources.TEMPLATE_SOUND));
    public static final RegistrySupplier<SoundEvent> TEMPLATE_SOUND_EVENT_2 = SOUND_EVENTS.register(
            "template_sound_event_2", () -> SoundEvent.createVariableRangeEvent(ModResources.TEMPLATE_SOUND_2));
    public static final RegistrySupplier<SoundEvent> TEMPLATE_SOUND_EVENT_3 = SOUND_EVENTS.register(
            "template_sound_event_3", () -> SoundEvent.createVariableRangeEvent(ModResources.TEMPLATE_SOUND_3));
}
