package mc.mian.indestructible_blocks.datagen;

import mc.mian.indestructible_blocks.common.item.ModItems;
import mc.mian.indestructible_blocks.util.ModResources;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper efh) {
        super(output, ModResources.MOD_ID, efh);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.DESTRUCTIBILITY_EDITOR.get());
    }
}
