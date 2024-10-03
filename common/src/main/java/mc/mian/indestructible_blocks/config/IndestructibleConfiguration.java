package mc.mian.indestructible_blocks.config;

import com.google.common.collect.Lists;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class IndestructibleConfiguration {
    public final ModConfigSpec.ConfigValue<List<? extends String>> INDESTRUCTIBLE_BLOCK_LIST;
    public IndestructibleConfiguration(final ModConfigSpec.Builder builder) {
        builder.comment("This category holds general values that most people will want to change.");
        builder.push("General Settings");
        this.INDESTRUCTIBLE_BLOCK_LIST = buildStringList(builder, "Indestructible Blocks", "Put the namespace and id of any block here to make it invincible.");
    }

    private static ModConfigSpec.ConfigValue<List<? extends String>> buildStringList(final ModConfigSpec.Builder builder, String translationPath, String comment){
        return builder.comment(comment).translation(translationPath).defineListAllowEmpty(translationPath, Lists.newArrayList(), String::new, String.class::isInstance);
    }
}
