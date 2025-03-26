package mc.mian.indestructible_blocks.common.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import mc.mian.indestructible_blocks.api.OverrideState;
import mc.mian.indestructible_blocks.util.DestructibilityState;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class IndestructibleSavedData extends SavedData implements OverrideState {
    private final HashMap<BlockPos, DestructibilityState> state_overrides = new HashMap<>();

    public static final SavedDataType<IndestructibleSavedData> TYPE = new SavedDataType<>(
            // Best to preface the identifier with your mod id followed by an underscore
            // Slashes will throw an error as the folders are not present
            // Will resolve to `saves/<world_name>/data/examplemod_example.dat`
            IndestructibleResources.MOD_ID,
            // Constructor for the new instance
            IndestructibleSavedData::new,
            // Codec factory to encode and decode the data
            ctx -> RecordCodecBuilder.create(instance -> instance.group(
                    RecordCodecBuilder.point(ctx.levelOrThrow()),
                    StateCodec.CODEC.listOf().optionalFieldOf("state_overrides", List.of())
                            .forGetter()
            ).apply(instance, IndestructibleSavedData::new)),
            DataFixTypes.SAVED_DATA_FORCED_CHUNKS
    );
    public static final Codec<Raids> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    Raids.RaidWithId.CODEC.listOf().optionalFieldOf("raids", List.of()).forGetter((raids) -> raids.raidMap.int2ObjectEntrySet().stream().map(Raids.RaidWithId::from).toList()),
                    Codec.INT.fieldOf("next_id").forGetter((raids) -> raids.nextId),
                    Codec.INT.fieldOf("tick").forGetter((raids) -> raids.tick)).apply(instance, Raids::new));

    private record StateCodec(BlockPos blockPos, String state){
        public static final Codec<Map<BlockPos, String>> CODEC =
                Codec.unboundedMap(BlockPos.CODEC, Codec.STRING);
//                RecordCodecBuilder.create((instance) ->
//                        instance.group(BlockPos.CODEC.fieldOf("pos").forGetter(StateCodec::pos),
//                                Codec.STRING.fieldOf("state").forGetter(StateCodec::stateToString)));

        private StateCodec(BlockPos blockPos, String state){
            this.blockPos = blockPos;
            this.state = state;
        }

        public static StateCodec from(Object2ObjectMap.Entry<BlockPos, String> entry) {
            return new StateCodec(entry.getKey(), entry.getValue());
        }

        public BlockPos pos() {
            return this.blockPos;
        }

        public String state() {
            return this.state;
        }
    }

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
            });
            tag.put("state_overrides", listTag);
        }
        return tag;
    }

    public static IndestructibleSavedData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        IndestructibleSavedData data = create();
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

    public static IndestructibleSavedData create(){
        return new IndestructibleSavedData();
    }

    public static IndestructibleSavedData getOrCreate(DimensionDataStorage dataStorage){
        return dataStorage.computeIfAbsent(TYPE);
    }
}
