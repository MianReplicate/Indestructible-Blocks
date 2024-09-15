package mc.mian.indestructible_blocks.common.level;

import mc.mian.indestructible_blocks.api.OverrideState;
import mc.mian.indestructible_blocks.util.DestructibilityState;
import mc.mian.indestructible_blocks.util.ModResources;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Iterator;

public class IndestructibleBlocksSavedData extends SavedData implements OverrideState {
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
    public CompoundTag save(CompoundTag tag) {
        if (!state_overrides.isEmpty()) {

            ListTag listTag = new ListTag();
            state_overrides.forEach((blockPos, state) -> {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.put("block_pos", NbtUtils.writeBlockPos(blockPos));
                compoundTag.putString("state", state.getSetting());
                listTag.add(compoundTag);
            });
            tag.put("state_overrides", listTag);
        }
        return tag;
    }

    public static IndestructibleBlocksSavedData load(CompoundTag tag) {
        IndestructibleBlocksSavedData data = create();
        if (tag.contains("state_overrides", Tag.TAG_LIST)) {
            for (Tag override : tag.getList("state_overrides", Tag.TAG_COMPOUND)) {
                if(override instanceof CompoundTag overrideCompound){
                    IntArrayTag list = (IntArrayTag) overrideCompound.get("block_pos");
                    String setting = overrideCompound.getString("state");
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
        return dataStorage.computeIfAbsent(IndestructibleBlocksSavedData::load, IndestructibleBlocksSavedData::create, ModResources.MOD_ID);
    }
}
