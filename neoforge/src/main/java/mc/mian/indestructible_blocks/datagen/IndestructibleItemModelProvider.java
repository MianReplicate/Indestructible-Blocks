package mc.mian.indestructible_blocks.datagen;

import mc.mian.indestructible_blocks.common.item.IndestructibleItems;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IndestructibleItemModelProvider extends ItemModelProvider {
    public IndestructibleItemModelProvider(PackOutput output, ExistingFileHelper efh) {
        super(output, IndestructibleResources.MOD_ID, efh);
    }

    @Override
    protected void registerModels() {
        basicItem(IndestructibleItems.DESTRUCTIBILITY_EDITOR.get());
    }
}
