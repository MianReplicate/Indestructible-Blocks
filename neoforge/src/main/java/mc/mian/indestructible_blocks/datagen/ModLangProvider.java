package mc.mian.indestructible_blocks.datagen;

import mc.mian.indestructible_blocks.common.item.ModItems;
import mc.mian.indestructible_blocks.util.ModResources;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLangProvider extends LanguageProvider {
    public static final String MOD_ID = ModResources.MOD_ID;
    
    public ModLangProvider(PackOutput output) {
        super(output, MOD_ID, "en_us");
    }

    public void addAdvancement(ResourceLocation advancementLocation, String title, String desc){
        add("advancement."+MOD_ID+":"+advancementLocation.getPath(), title);
        add("advancement."+MOD_ID+":"+advancementLocation.getPath()+".desc", desc);
    }

    public void addGuiMessage(String title, String translation){
        add("gui."+ MOD_ID+"."+title, translation);
    }

    public void addChatMessage(String title, String translation){
        add("chat.message."+ MOD_ID+"."+title, translation);
    }

    public void addDisconnectionReason(String title, String translation){
        add("disconnect."+ MOD_ID+"."+title, translation);
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

    public void addJukeboxSong(ResourceKey key, String translation){
        add(Util.makeDescriptionId("jukebox_song", key.location()), translation);
    }

    public void addItemGroup(String title, String translation){
        add("itemGroup."+ModResources.MOD_ID+"."+title, translation);
    }

    public void addSound(String title, String translation){
        add(ModResources.MOD_ID+".sound."+title, translation);
    }

    @Override
    protected void addTranslations() {
        addConfigOption("Indestructible Blocks", "Indestructible Blocks", "Put the namespace and id of any block here to make it invincible.");
        addConfigSection("General Settings", "General Settings", "This category holds general values that most people will want to change.");
        addGuiMessage("indestructibility_state", "New Indestructible State: %s");
        addGuiMessage("failed_to_change_state", "Failed to change indestructibility state of %s");
        addItem(ModItems.DESTRUCTIBILITY_EDITOR, "Meowing Disc");

        addItemGroup("indestructible_blocks", "Indestructible Blocks");
    }
}
