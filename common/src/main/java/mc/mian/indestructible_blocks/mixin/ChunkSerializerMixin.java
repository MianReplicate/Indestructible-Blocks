package mc.mian.indestructible_blocks.mixin;

import mc.mian.indestructible_blocks.api.RandomTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {

    @Inject(method = "write", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getBlendingData()Lnet/minecraft/world/level/levelgen/blending/BlendingData;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void writeScheduledRandomTicks(ServerLevel serverLevel, ChunkAccess chunkAccess, CallbackInfoReturnable<CompoundTag> cir, ChunkPos chunkPos, CompoundTag tag) {
        List<BlockPos> scheduledRandomTicks = ((RandomTickScheduler) chunkAccess).getScheduledRandomTicks();

        if (!scheduledRandomTicks.isEmpty()) {
            CompoundTag indestructible_blocks = new CompoundTag();

            ListTag listTag = new ListTag();
            scheduledRandomTicks.forEach(scheduledRandomTick -> listTag.add(NbtUtils.writeBlockPos(scheduledRandomTick)));
            indestructible_blocks.put("scheduled_random_ticks", listTag);

            tag.put("indestructible_blocks", indestructible_blocks);
        }
    }


    @Inject(method = "read", at = @At("RETURN"))
    private static void readScheduledRandomTicks(ServerLevel level, PoiManager poiManager, RegionStorageInfo regionStorageInfo, ChunkPos pos, CompoundTag tag, CallbackInfoReturnable<ProtoChunk> cir) {
        if (tag.contains("indestructible_blocks")) {
            CompoundTag corgiLibTag = tag.getCompound("indestructible_blocks");
            if (corgiLibTag.contains("scheduled_random_ticks", Tag.TAG_LIST)) {
                for (Tag scheduledTick : tag.getList("scheduled_random_ticks", Tag.TAG_COMPOUND)) {
                    if(scheduledTick instanceof IntArrayTag tag1){
                        ((RandomTickScheduler) cir.getReturnValue()).getScheduledRandomTicks().add(new BlockPos(tag1.get(0).getAsInt(), tag1.get(1).getAsInt(), tag1.get(2).getAsInt()));
                    }
                }
            }
        }
    }
}
