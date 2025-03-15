package mc.mian.indestructible_blocks.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class IndestructibleDataGenerators {
    private static final String PATH_ITEM_PREFIX = "textures/item";
    private static final String PATH_BLOCK_PREFIX = "textures/block";
    private static final String PATH_SUFFIX = ".png";

    @SubscribeEvent
    public static void generateData(GatherDataEvent.Client ev) {
        final DataGenerator gen = ev.getGenerator();
        final PackOutput packOutput = gen.getPackOutput();

//        addVirtualPackContents(efh);

        ev.addProvider(new IndestructibleLangProvider(packOutput));
        ev.addProvider(new IndestructibleModelProvider(packOutput));
    }
//
//    private static void addVirtualPackContents(ExistingFileHelper existingFileHelper) {
//        existingFileHelper.trackGenerated(
//                IndestructibleResources.modLoc(IndestructibleItems.DESTRUCTIBILITY_EDITOR.getId().getPath()), PackType.CLIENT_RESOURCES, PATH_SUFFIX, PATH_ITEM_PREFIX
//        );
//    }
}
