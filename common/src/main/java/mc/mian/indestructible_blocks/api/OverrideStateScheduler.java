package mc.mian.indestructible_blocks.api;

import mc.mian.indestructible_blocks.util.DestructibilityState;
import net.minecraft.core.BlockPos;

import java.util.HashMap;

public interface OverrideStateScheduler {
    void putOverride(BlockPos pos, DestructibilityState setting);
    void removeOverride(BlockPos pos);
    DestructibilityState hasOverride(BlockPos pos);
    HashMap<BlockPos, DestructibilityState> getOverrides();
}
