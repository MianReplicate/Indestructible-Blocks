package mc.mian.templatemod.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import javax.annotation.Nullable;

public class ModConfiguration {
    public final ModConfigSpec.BooleanValue meow;
    public ModConfiguration(final ModConfigSpec.Builder builder) {
        builder.comment("This category holds general values that most people will want to change.");
        builder.push("General Settings");
        this.meow = buildBoolean(builder, "meow", true, "meow!");
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
}
