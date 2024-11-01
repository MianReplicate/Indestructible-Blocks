package mc.mian.indestructible_blocks.common.item;

import mc.mian.indestructible_blocks.common.component.IndestructibleComponents;
import mc.mian.indestructible_blocks.common.item.custom.DestructibilityEditor;
import mc.mian.indestructible_blocks.registry.DeferredRegistry;
import mc.mian.indestructible_blocks.registry.RegistrySupplier;
import mc.mian.indestructible_blocks.util.DestructibilitySetting;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Function;

public class IndestructibleItems {
    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(IndestructibleResources.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> DESTRUCTIBILITY_EDITOR = registerItem("destructibility_editor",
            (properties) -> new DestructibilityEditor(properties
                    .stacksTo(1).rarity(Rarity.RARE).component(IndestructibleComponents.DESTRUCTIBILITY_SETTING.get(), DestructibilitySetting.BLOCK_ID.getSetting())));

    public static RegistrySupplier<Item> registerItem(String name, Function<Item.Properties, Item> itemFunc){
        return ITEMS.register(name, () -> itemFunc.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(IndestructibleResources.MOD_ID, name)))));
    }
}
