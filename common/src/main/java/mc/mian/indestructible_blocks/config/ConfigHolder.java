package mc.mian.indestructible_blocks.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHolder {
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final ModConfiguration SERVER;

    static{
        final Pair<ModConfiguration, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ModConfiguration::new);
        SERVER = specPair.getLeft();
        SERVER_SPEC = specPair.getRight();
    }
}
