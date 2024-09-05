package mc.mian.indestructible_blocks.mixin;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.api.OverrideStateScheduler;
import mc.mian.indestructible_blocks.util.DestructibilityState;
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

import java.util.HashMap;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {

    @Inject(method = "write", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getBlendingData()Lnet/minecraft/world/level/levelgen/blending/BlendingData;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void writeScheduledRandomTicks(ServerLevel serverLevel, ChunkAccess chunkAccess, CallbackInfoReturnable<CompoundTag> cir, ChunkPos chunkPos, CompoundTag tag) {
        HashMap<BlockPos, DestructibilityState> overrides = ((OverrideStateScheduler) chunkAccess).getOverrides();

        if (!overrides.isEmpty()) {
            CompoundTag indestructible_blocks = new CompoundTag();

            ListTag listTag = new ListTag();
            overrides.forEach((blockPos, state) -> {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.put("block_pos", NbtUtils.writeBlockPos(blockPos));
                compoundTag.putString("state", state.getSetting());
                listTag.add(compoundTag);
                IndestructibleBlocks.LOGGER.info("saving");
            });
            indestructible_blocks.put("state_overrides", listTag);

            tag.put("indestructible_blocks", indestructible_blocks);
            IndestructibleBlocks.LOGGER.info("put!");
        }
    }


    @Inject(method = "read", at = @At("RETURN"))
    private static void readScheduledRandomTicks(ServerLevel level, PoiManager poiManager, RegionStorageInfo regionStorageInfo, ChunkPos pos, CompoundTag tag, CallbackInfoReturnable<ProtoChunk> cir) {
        IndestructibleBlocks.LOGGER.info("reading");
        if (tag.contains("indestructible_blocks")) {
            IndestructibleBlocks.LOGGER.info("contains tag");
            CompoundTag indestructible_blocks = tag.getCompound("indestructible_blocks");
            if (indestructible_blocks.contains("state_overrides", Tag.TAG_LIST)) {
                IndestructibleBlocks.LOGGER.info("contains list");
                for (Tag override : indestructible_blocks.getList("state_overrides", Tag.TAG_COMPOUND)) {
                    IndestructibleBlocks.LOGGER.info("grabbing override");
                    if(override instanceof CompoundTag overrideCompound){
                        IndestructibleBlocks.LOGGER.info("is instance");
                        IntArrayTag list = (IntArrayTag) overrideCompound.get("block_pos");
                        String setting = overrideCompound.getString("state");
                        ((OverrideStateScheduler) cir.getReturnValue()).putOverride(new BlockPos(list.get(0).getAsInt(), list.get(1).getAsInt(), list.get(2).getAsInt()), DestructibilityState.getEnum(setting));
                        IndestructibleBlocks.LOGGER.info("putting");
                    }
                }
            }
        }
    }
}
