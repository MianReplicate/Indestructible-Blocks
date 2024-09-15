package mc.mian.indestructible_blocks.common.item;

import mc.mian.indestructible_blocks.common.item.custom.DestructibilityEditor;
import mc.mian.indestructible_blocks.registry.DeferredRegistry;
import mc.mian.indestructible_blocks.registry.RegistrySupplier;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class IndestructibleItems {
    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(IndestructibleResources.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> DESTRUCTIBILITY_EDITOR = ITEMS.register("destructibility_editor",
            () -> new DestructibilityEditor(new Item.Properties()
                    .stacksTo(1).rarity(Rarity.RARE)));
}
