package mc.mian.indestructible_blocks.mixin;

import com.google.common.collect.Lists;
import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.api.RandomTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(ChunkAccess.class)
public class ChunkAccessMixin implements RandomTickScheduler {

    @Unique
    private final List<BlockPos> scheduledRandomTick = new ArrayList<>();

    @Override
    public void scheduleRandomTick(BlockPos pos) {
        IndestructibleBlocks.LOGGER.info("Scheduled "+pos.toString()+" to be removable");
        this.scheduledRandomTick.add(pos.immutable());
    }

    @Override
    public void removeRandomTick(BlockPos pos){
        IndestructibleBlocks.LOGGER.info(pos.toString()+" is no longer removable");
        for(Iterator<BlockPos> iterator = scheduledRandomTick.iterator(); iterator.hasNext();){
            BlockPos blockPos = iterator.next();
            if(blockPos.equals(pos)){
                iterator.remove();
            }
        }
    }

    @Override
    public boolean hasRandomTick(BlockPos pos){
        return this.scheduledRandomTick.stream().filter(listPos -> listPos.equals(pos)).findFirst().orElse(null) != null;
    }

    @Override
    public List<BlockPos> getScheduledRandomTicks() {
        return this.scheduledRandomTick;
    }
}