package mc.mian.indestructible_blocks.mixin;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.api.OverrideState;
import mc.mian.indestructible_blocks.common.level.IndestructibleSavedData;
import mc.mian.indestructible_blocks.util.IndestructibleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunk.class)
public class LevelChunkMixin {

    @Shadow @Final private Level level;

    @Inject(method = "setBlockState", at = @At("HEAD"))
    private void remove(BlockPos pos, BlockState newState, boolean isMoving, CallbackInfoReturnable<BlockState> cir) {
        if(!level.isClientSide){
            BlockState currentState = level.getBlockState(pos);
            if(newState.getBlock() != currentState.getBlock()){
                ServerLevel serverLevel = (ServerLevel) level;
                if(!IndestructibleUtil.isBlockPosRemovable(serverLevel, pos)){
                    cir.cancel();
                    level.sendBlockUpdated(pos, Blocks.AIR.defaultBlockState(), currentState, 3);
                } else {
                    IndestructibleBlocks.pendingRemovalBlocks.remove(currentState);
                    OverrideState overrideState = IndestructibleSavedData.getOrCreate(serverLevel.getDataStorage());
                    if(overrideState.hasOverride(pos) != null){
                        overrideState.removeOverride(pos);
                    }
                }
            }
        }
    }
}