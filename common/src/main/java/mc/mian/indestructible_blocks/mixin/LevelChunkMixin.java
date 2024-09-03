package mc.mian.indestructible_blocks.mixin;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LevelChunk.class)
public class LevelChunkMixin {

    @Shadow @Final private Level level;

    @Inject(method = "setBlockState", at = @At("HEAD"), cancellable = true)
    private void remove(BlockPos pos, BlockState newState, boolean isMoving, CallbackInfoReturnable<BlockState> cir) {
        if(!level.isClientSide){
            BlockState currentState = level.getBlockState(pos);
            if(newState.getBlock() != currentState.getBlock()){
                if(ModUtil.isInConfig(currentState) && !ModUtil.isRequestedToBeForceRemoved(currentState)){
                    cir.cancel();
                } else {
                    IndestructibleBlocks.blocksToRemove.remove(currentState);
                }
            }
        }
    }
}