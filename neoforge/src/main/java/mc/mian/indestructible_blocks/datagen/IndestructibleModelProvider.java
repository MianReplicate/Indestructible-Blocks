package mc.mian.indestructible_blocks.datagen;

import mc.mian.indestructible_blocks.common.item.IndestructibleItems;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class IndestructibleModelProvider extends ModelProvider {
    public IndestructibleModelProvider(PackOutput output) {
        super(output, IndestructibleResources.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(IndestructibleItems.DESTRUCTIBILITY_EDITOR.get(), ModelTemplates.FLAT_ITEM);
    }
}
