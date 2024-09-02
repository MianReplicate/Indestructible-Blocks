package mc.mian.templatemod.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHolder {
    public static final ModConfigSpec SERVER_SPEC;
    public static final ModConfiguration SERVER;

    static{
        final Pair<ModConfiguration, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ModConfiguration::new);
        SERVER = specPair.getLeft();
        SERVER_SPEC = specPair.getRight();
    }
}
