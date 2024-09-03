package mc.mian.indestructible_blocks.util;

public enum IndestructibilityState {
    DESTRUCTIBLE("Destructible"), INDESTRUCTIBLE("Indestructible");

    IndestructibilityState(final String setting){
        this.setting = setting;
    }

    private String setting;

    public String getSetting(){
        return this.setting;
    }
}
