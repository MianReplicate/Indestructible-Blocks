package mc.mian.indestructible_blocks.util;

import java.util.Arrays;
import java.util.Objects;

public enum DestructibilitySetting {
    ONE_BLOCK("One Block"), BLOCK_ID("Block Id");

    DestructibilitySetting(final String setting){
        this.setting = setting;
    }

    private final String setting;

    public String getSetting(){
        return this.setting;
    }

    public static DestructibilitySetting getEnum(String setting){
        return Objects.requireNonNull(Arrays.stream(DestructibilitySetting.values())
                        .filter(setting1 -> setting1.getSetting().equals(setting)))
                .findFirst().get();
    }
}
