package mc.mian.indestructible_blocks.util;

import net.minecraft.resources.ResourceLocation;

public class IndestructibleResources {
    public static final String MOD_ID = "indestructible_blocks";

    public static ResourceLocation modLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
