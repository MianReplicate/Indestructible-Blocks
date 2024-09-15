package mc.mian.indestructible_blocks.datagen;

import mc.mian.indestructible_blocks.common.item.IndestructibleItems;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class IndestructibleLangProvider extends LanguageProvider {
    public static final String MOD_ID = IndestructibleResources.MOD_ID;
    
    public IndestructibleLangProvider(PackOutput output) {
        super(output, MOD_ID, "en_us");
    }

    public void addGuiMessage(String title, String translation){
        add("gui."+ MOD_ID+"."+title, translation);
    }

    public void addConfigOption(String title, String tileTranslation, String tipTranslation){
     add(title, tileTranslation);
     add(title+".tooltip", tipTranslation);
    }
    
    public void addConfigSection(String title, String titleTranslation, String commentTranslation){
        add(MOD_ID + ".configuration."+title, titleTranslation);
        add(MOD_ID + ".configuration."+title+".button", "Edit");
        add(MOD_ID + ".configuration."+title+".tooltip", commentTranslation);
    }

    public void addItemGroup(String title, String translation){
        add("itemGroup."+ IndestructibleResources.MOD_ID+"."+title, translation);
    }

    public void addChatMessage(String title, String translation){
        add("chat."+ IndestructibleResources.MOD_ID+"."+title, translation);
    }

    @Override
    protected void addTranslations() {
        addConfigOption("Indestructible Blocks", "Indestructible Blocks", "Put the namespace and id of any block here to make it invincible.");
        addConfigSection("General Settings", "General Settings", "This category holds general values that most people will want to change.");
        addGuiMessage("indestructibility_state", "Changed \"%s\" Indestructible State: %s");
        addGuiMessage("failed_to_change_state", "Failed to change indestructibility state of \"%s\"");
        addGuiMessage("block_indestructibility_state", "Changed State of Selected Block: %s");
        addGuiMessage("failed_to_change_block_state", "Failed to change indestructibility state of %s");
        addChatMessage("set_multiple_block_state", "Successfully changed %s block(s)");

        addGuiMessage("cannot_break", "This block is unbreakable");
        addGuiMessage("setting_state", "Changed Indestructibility Setting: %s");
        addItem(IndestructibleItems.DESTRUCTIBILITY_EDITOR, "Destructibility Editor");

        addItemGroup("indestructible_blocks", "Indestructible Blocks");
    }
}
