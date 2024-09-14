package mc.mian.indestructible_blocks.util;

import java.util.Arrays;
import java.util.Objects;

public enum DestructibilityState {
    DESTRUCTIBLE("Destructible"), INDESTRUCTIBLE("Indestructible");

    DestructibilityState(final String setting){
        this.setting = setting;
    }

    private final String setting;

    public String getSetting(){
        return this.setting;
    }

    public static DestructibilityState getEnum(String setting){
        return Objects.requireNonNull(Arrays.stream(DestructibilityState.values())
                .filter(setting1 -> setting1.getSetting().equals(setting)))
                .findFirst().get();
    }
}
