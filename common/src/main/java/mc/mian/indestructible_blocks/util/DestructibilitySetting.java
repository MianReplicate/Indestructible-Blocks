package mc.mian.indestructible_blocks.util;

public enum DestructibilitySetting {
    ONE_BLOCK("One Block"), BLOCK_ID("Block Id");

    DestructibilitySetting(final String setting){
        this.setting = setting;
    }

    private String setting;

    public String getSetting(){
        return this.setting;
    }
}
