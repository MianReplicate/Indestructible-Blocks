package mc.mian.indestructible_blocks.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class IndestructibleConfiguration {
    public final ForgeConfigSpec.ConfigValue<List<? extends String>> INDESTRUCTIBLE_BLOCK_LIST;
    public IndestructibleConfiguration(final ForgeConfigSpec.Builder builder) {
        builder.comment("This category holds general values that most people will want to change.");
        builder.push("General Settings");
        this.INDESTRUCTIBLE_BLOCK_LIST = buildStringList(builder, "Indestructible Blocks", "Put the namespace and id of any block here to make it invincible.");
    }

    private static ForgeConfigSpec.ConfigValue<List<? extends String>> buildStringList(final ForgeConfigSpec.Builder builder, String translationPath, String comment){
        return builder.comment(comment).translation(translationPath).defineListAllowEmpty(translationPath, Lists.newArrayList(), String.class::isInstance);
    }
}
