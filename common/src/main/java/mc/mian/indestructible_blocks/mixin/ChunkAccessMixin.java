package mc.mian.indestructible_blocks.mixin;

import mc.mian.indestructible_blocks.api.OverrideStateScheduler;
import mc.mian.indestructible_blocks.util.DestructibilityState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.*;

@Mixin(ChunkAccess.class)
public class ChunkAccessMixin implements OverrideStateScheduler {

    @Unique
    private final HashMap<BlockPos, DestructibilityState> state_overrides = new HashMap<>();

    @Override
    public void putOverride(BlockPos pos, DestructibilityState setting) {
        this.state_overrides.put(pos.immutable(), setting);
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
        BlockPos givenPos = this.state_overrides.keySet().stream().filter(listPos -> listPos.equals(pos)).findFirst().orElse(null);
        if(givenPos != null){
            return this.state_overrides.get(givenPos);
        }
        return null;
    }

    @Override
    public HashMap<BlockPos, DestructibilityState> getOverrides() {
        return this.state_overrides;
    }
}