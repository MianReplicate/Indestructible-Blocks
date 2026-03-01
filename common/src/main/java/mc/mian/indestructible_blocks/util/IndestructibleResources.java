package mc.mian.indestructible_blocks.util;

import net.minecraft.resources.Identifier;

public class IndestructibleResources {
    public static final String MOD_ID = "indestructible_blocks";

    public static Identifier modLoc(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
