package mc.mian.indestructible_blocks.common.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mc.mian.indestructible_blocks.api.OverrideState;
import mc.mian.indestructible_blocks.util.DestructibilityState;
import mc.mian.indestructible_blocks.util.IndestructibleResources;
import mc.mian.indestructible_blocks.util.IndestructibleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.SavedDataStorage;

import java.util.HashMap;

public class IndestructibleSavedData extends SavedData implements OverrideState {
    private HashMap<BlockPos, DestructibilityState> state_overrides;

    // BlockPos from String whoa
    public static final Codec<BlockPos> STRING_BLOCK_POS_CODEC = Codec.STRING.flatXmap(
            s -> {
                if (s.trim().isEmpty())
                    return DataResult.error(() -> "Expected value");
                if (!s.startsWith("["))
                    return DataResult.error(() -> "Expected '['");
                if (!s.endsWith("]"))
                    return DataResult.error(() -> "Expected ']'");

                s = s.substring(1, s.length() - 1);
                var list = s.split(",");
                if (list.length != 3)
                    return DataResult.error(() -> "Expected length 3");
                return DataResult
                        .success(Integer.parseInt(list[0].trim()))
                        .flatMap(x -> DataResult.success(Integer.parseInt(list[1].trim()))
                                .flatMap(y -> DataResult.success(Integer.parseInt(list[2].trim()))
                                        .map(z -> new BlockPos(x, y, z))));
            },
            pos -> DataResult.success("[" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "]")
    );

    public static final Codec<HashMap<BlockPos, DestructibilityState>> LIST_BLOCK_INDESTRUCTIBILITY_STATE_CODEC =
            Codec.unboundedMap(STRING_BLOCK_POS_CODEC, DestructibilityState.CODEC).xmap(
                    HashMap::new,
                    hashMap -> hashMap
            );

    public static final Codec<IndestructibleSavedData> SAVED_DATA_CODEC = RecordCodecBuilder.create(builder ->
        builder.group(
                LIST_BLOCK_INDESTRUCTIBILITY_STATE_CODEC.optionalFieldOf("state_overrides", new HashMap<>()).forGetter(IndestructibleSavedData::getOverrides)
        ).apply(builder, IndestructibleSavedData::new));

    public static final SavedDataType<IndestructibleSavedData> TYPE = new SavedDataType<>(
            // Best to preface the identifier with your mod id followed by an underscore
            // Slashes will throw an error as the folders are not present
            // Will resolve to `saves/<world_name>/data/examplemod_example.dat`
            IndestructibleResources.modLoc(IndestructibleResources.MOD_ID + "_block_data"),
            // Constructor for the new instance
            IndestructibleSavedData::new,
            // Codec factory to encode and decode the data
            SAVED_DATA_CODEC,
            DataFixTypes.SAVED_DATA_FORCED_CHUNKS
    );

    public IndestructibleSavedData(){
        this.state_overrides = new HashMap<>();
    }

    private IndestructibleSavedData(HashMap<BlockPos, DestructibilityState> state_overrides) {
        this.state_overrides = state_overrides;

        LIST_BLOCK_INDESTRUCTIBILITY_STATE_CODEC.fieldOf("state_overrides").forGetter(IndestructibleSavedData::getOverrides);
    }


    @Override
    public void putOverride(BlockPos pos, DestructibilityState setting) {
        this.removeOverride(pos);
        this.state_overrides.put(pos.immutable(), setting);
        this.setDirty(true);
    }

    @Override
    public void removeOverride(BlockPos pos){
        state_overrides.keySet().removeIf(blockPos -> blockPos.equals(pos));
        this.setDirty(true);
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

    public static IndestructibleSavedData getOrCreate(SavedDataStorage dataStorage){
        return dataStorage.computeIfAbsent(TYPE);
    }
}