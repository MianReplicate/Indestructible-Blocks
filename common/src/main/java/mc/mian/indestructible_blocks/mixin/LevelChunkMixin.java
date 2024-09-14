package mc.mian.indestructible_blocks.mixin;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.api.OverrideState;
import mc.mian.indestructible_blocks.common.level.IndestructibleBlocksSavedData;
import mc.mian.indestructible_blocks.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.MinecraftServer;
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

    @Inject(method = "setBlockState", at = @At("HEAD"), cancellable = true)
    private void remove(BlockPos pos, BlockState newState, boolean isMoving, CallbackInfoReturnable<BlockState> cir) {
        if(!level.isClientSide){
            BlockState currentState = level.getBlockState(pos);
            if(newState.getBlock() != currentState.getBlock()){
                ServerLevel serverLevel = (ServerLevel) level;
                if(!ModUtil.isPendingRemoval(currentState) && !ModUtil.isBlockPosRemovable(serverLevel, pos)){
                    cir.cancel();
                    level.sendBlockUpdated(pos, Blocks.AIR.defaultBlockState(), currentState, 3);
                } else {
                    IndestructibleBlocks.pendingRemovalBlocks.remove(currentState);
                    OverrideState overrideState = IndestructibleBlocksSavedData.getOrCreate(serverLevel.getDataStorage());
                    if(overrideState.hasOverride(pos) != null){
                        overrideState.removeOverride(pos);
                    }
                }
            }
        }
    }
}