package mc.mian.indestructible_blocks.common.level;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.api.OverrideStateScheduler;
import mc.mian.indestructible_blocks.util.DestructibilityState;
import mc.mian.indestructible_blocks.util.ModResources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Iterator;

public class IndestructibleBlocksSavedData extends SavedData implements OverrideStateScheduler {
    private final HashMap<BlockPos, DestructibilityState> state_overrides = new HashMap<>();

    @Override
    public void putOverride(BlockPos pos, DestructibilityState setting) {
        this.removeOverride(pos);
        this.state_overrides.put(pos.immutable(), setting);
        this.setDirty(true);
    }

    @Override
    public void removeOverride(BlockPos pos){
        for(Iterator<BlockPos> iterator = state_overrides.keySet().iterator(); iterator.hasNext();){
            BlockPos blockPos = iterator.next();
            if(blockPos.equals(pos)){
                iterator.remove();
            }
        }
    }

    @Override
    public DestructibilityState hasOverride(BlockPos pos){
        BlockPos givenPos = this.state_overrides.keySet().stream().filter(listPos -> listPos.equals(pos)
        ).findFirst().orElse(null);
        if(givenPos != null){
            return this.state_overrides.get(givenPos);
        }
        return null;
    }

    @Override
    public HashMap<BlockPos, DestructibilityState> getOverrides() {
        return this.state_overrides;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        if (!state_overrides.isEmpty()) {

            ListTag listTag = new ListTag();
            state_overrides.forEach((blockPos, state) -> {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.put("block_pos", NbtUtils.writeBlockPos(blockPos));
                compoundTag.putString("state", state.getSetting());
                listTag.add(compoundTag);
                IndestructibleBlocks.LOGGER.info("saving");
            });
            tag.put("state_overrides", listTag);
            IndestructibleBlocks.LOGGER.info("put!");
        }
        return tag;
    }

    public static IndestructibleBlocksSavedData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        IndestructibleBlocksSavedData data = create();
        if (tag.contains("state_overrides", Tag.TAG_LIST)) {
            for (Tag override : tag.getList("state_overrides", Tag.TAG_COMPOUND)) {
                IndestructibleBlocks.LOGGER.info("grabbing override");
                if(override instanceof CompoundTag overrideCompound){
                    IndestructibleBlocks.LOGGER.info("found override tag");
                    IntArrayTag list = (IntArrayTag) overrideCompound.get("block_pos");
                    String setting = overrideCompound.getString("state");
                    IndestructibleBlocks.LOGGER.info("putting "+list+" with "+setting);
                    data.putOverride(new BlockPos(list.get(0).getAsInt(), list.get(1).getAsInt(), list.get(2).getAsInt()), DestructibilityState.getEnum(setting));
                }
            }
        }
        return data;
    }

    public static IndestructibleBlocksSavedData create(){
        return new IndestructibleBlocksSavedData();
    }

    public static IndestructibleBlocksSavedData getOrCreate(DimensionDataStorage dataStorage){
        return dataStorage.computeIfAbsent(new Factory<>(IndestructibleBlocksSavedData::create, IndestructibleBlocksSavedData::load, DataFixTypes.SAVED_DATA_FORCED_CHUNKS), ModResources.MOD_ID);
    }
}
