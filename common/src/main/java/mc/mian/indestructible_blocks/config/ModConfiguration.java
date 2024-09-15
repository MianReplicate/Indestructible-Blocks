package mc.mian.indestructible_blocks.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class ModConfiguration {
    public final ForgeConfigSpec.ConfigValue<List<? extends String>> INDESTRUCTIBLE_BLOCK_LIST;
    public ModConfiguration(final ForgeConfigSpec.Builder builder) {
        builder.comment("This category holds general values that most people will want to change.");
        builder.push("General Settings");
        this.INDESTRUCTIBLE_BLOCK_LIST = buildStringList(builder, "Indestructible Blocks", "Put the namespace and id of any block here to make it invincible.");
    }

    private static ForgeConfigSpec.ConfigValue<List<? extends String>> buildStringList(final ForgeConfigSpec.Builder builder, String translationPath, String comment){
        return builder.comment(comment).translation(translationPath).defineList(translationPath, Lists.newArrayList(), String.class::isInstance);
    }
}
