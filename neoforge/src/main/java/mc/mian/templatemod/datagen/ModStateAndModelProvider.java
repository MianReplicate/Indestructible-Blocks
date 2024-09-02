package mc.mian.templatemod.datagen;

import mc.mian.templatemod.common.block.ModBlocks;
import mc.mian.templatemod.util.ModResources;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModStateAndModelProvider extends BlockStateProvider {
    public ModStateAndModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ModResources.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile crystal_block_model = cubeAll(ModBlocks.TEMPLATE_BLOCK.get());
        simpleBlockWithItem(ModBlocks.TEMPLATE_BLOCK.get(), crystal_block_model);
    }
}
