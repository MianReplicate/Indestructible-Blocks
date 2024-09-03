package mc.mian.indestructible_blocks.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.compress.utils.Lists;

import javax.annotation.Nullable;
import java.util.List;

public class ModConfiguration {
    public final ModConfigSpec.ConfigValue<List<? extends String>> INDESTRUCTIBLE_BLOCK_LIST;
    public ModConfiguration(final ModConfigSpec.Builder builder) {
        builder.comment("This category holds general values that most people will want to change.");
        builder.push("General Settings");
        this.INDESTRUCTIBLE_BLOCK_LIST = buildStringList(builder, "Indestructible Blocks", "Put the namespace and id of any block here to make it invincible.");
    }

    private static ModConfigSpec.IntValue buildInt(final ModConfigSpec.Builder builder, String translationPath, int defaultValue, int min, int max, @Nullable String comment) {
        return comment == null ? builder.translation(translationPath).defineInRange(translationPath, defaultValue, min, max) : builder.comment(comment).translation(translationPath).defineInRange(translationPath, defaultValue, min, max);
    }

    private static ModConfigSpec.DoubleValue buildDouble(final ModConfigSpec.Builder builder, String translationPath, double defaultValue, double min, double max, String comment) {
        return builder.comment(comment).translation(translationPath).defineInRange(translationPath, defaultValue, min, max);
    }

    private static ModConfigSpec.ConfigValue buildString(final ModConfigSpec.Builder builder, String translationPath, String defaultValue, String comment) {
        return builder.comment(comment).translation(translationPath).define(translationPath, defaultValue);
    }

    private static ModConfigSpec.BooleanValue buildBoolean(final ModConfigSpec.Builder builder, String translationPath, boolean defaultValue, String comment) {
        return builder.comment(comment).translation(translationPath).define(translationPath, defaultValue);
    }

    private static ModConfigSpec.EnumValue buildEnum(final ModConfigSpec.Builder builder, String translationPath, Enum defaultValue, String comment) {
        return builder.comment(comment).translation(translationPath).defineEnum(translationPath, defaultValue);
    }

    private static ModConfigSpec.ConfigValue<List<? extends String>> buildStringList(final ModConfigSpec.Builder builder, String translationPath, String comment){
        return builder.comment(comment).translation(translationPath).defineListAllowEmpty(translationPath, Lists.newArrayList(), String::new, String.class::isInstance);
    }
}
