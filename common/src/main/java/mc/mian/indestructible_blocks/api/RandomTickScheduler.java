package mc.mian.indestructible_blocks.api;

import net.minecraft.core.BlockPos;

import java.util.List;

public interface RandomTickScheduler {
    void scheduleRandomTick(BlockPos pos);
    void removeRandomTick(BlockPos pos);
    boolean hasRandomTick(BlockPos pos);
    List<BlockPos> getScheduledRandomTicks();
}
