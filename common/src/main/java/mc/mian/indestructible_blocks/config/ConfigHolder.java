package mc.mian.indestructible_blocks.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHolder {
    public static final ModConfigSpec SERVER_SPEC;
    public static final IndestructibleConfiguration SERVER;

    static{
        final Pair<IndestructibleConfiguration, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(IndestructibleConfiguration::new);
        SERVER = specPair.getLeft();
        SERVER_SPEC = specPair.getRight();
    }
}
