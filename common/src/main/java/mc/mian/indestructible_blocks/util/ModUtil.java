package mc.mian.indestructible_blocks.util;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.api.OverrideStateScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.List;

public class ModUtil {

    // Determines checks if block is in the force removal list
    public static boolean isPendingRemoval(BlockState state){
        if(IndestructibleBlocks.pendingRemovalBlocks.stream().anyMatch(blockStateToRemove -> state == blockStateToRemove)){
            return true;
        }
        return false;
    }

    public static boolean isInConfig(BlockState state){
        List<String> indestructible_blocks = (List<String>) IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.get();
        String blockId = state.getBlockHolder().getRegisteredName();
        if(indestructible_blocks.stream().anyMatch(blockIdBeingCompared -> blockIdBeingCompared.equals(blockId))){
            return true;
        }
        return false;
    }

    public static boolean isInConfig(String blockId){
        List<String> indestructible_blocks = (List<String>) IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.get();
        if(indestructible_blocks.stream().anyMatch(blockIdBeingCompared -> blockIdBeingCompared.equals(blockId))){
            return true;
        }
        return false;
    }

    public static boolean playerTryToBreak(Player player, BlockState state, BlockPos pos){
        if(!ModUtil.isBlockPosRemovable(player.level().getChunk(pos), pos)){
            if(player.isCreative()){
                ModUtil.addToPendingRemoval(state);
            } else if(!player.isCreative()){
                player.displayClientMessage(Component.translatable("gui.indestructible_blocks.cannot_break"), true);
            }
        }
        return true;
    }

    // Changes indestructibility state of a blockId
    public static DestructibilityState setIndestructibilityState(String blockId, boolean indestructible){
        List<String> indestructible_blocks = (List<String>) IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.get();
        if(!isInConfig(blockId) && indestructible){
            indestructible_blocks.add(blockId);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.set(indestructible_blocks);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.save();
            return DestructibilityState.INDESTRUCTIBLE;
        }else if(isInConfig(blockId) && !indestructible){
            indestructible_blocks.remove(blockId);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.set(indestructible_blocks);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.save();
            return DestructibilityState.DESTRUCTIBLE;
        }
        return null;
    }

    // Allows the block to be removed regardless of its indestructibility state. Allows people in creative mode to destroy the block
    // This is not a guarantee that the block will be removed. This is meant to be used RIGHT when the block is removing before the mixin kicks in. Ex: Creative player attempting to break block
    // If you want a block to be able to be removed but isn't requested to be so, you should use the method below
    public static void addToPendingRemoval(BlockState state){
        if(IndestructibleBlocks.pendingRemovalBlocks.stream().noneMatch(blockStateToRemove -> state == blockStateToRemove)){
            IndestructibleBlocks.pendingRemovalBlocks.add(state);
        }
    }

    // Overrides the destructibility state of the block below regardless of configuration
    public static DestructibilityState changeOverride(ChunkAccess chunk, BlockPos pos){
        DestructibilityState state = ((OverrideStateScheduler) chunk).hasOverride(pos);
        if(state == null){
            state = isInConfig(chunk.getBlockState(pos)) ? DestructibilityState.DESTRUCTIBLE : DestructibilityState.INDESTRUCTIBLE;
        } else {
            state = state == DestructibilityState.DESTRUCTIBLE ? DestructibilityState.INDESTRUCTIBLE : DestructibilityState.DESTRUCTIBLE;
        }
        ((OverrideStateScheduler) chunk).putOverride(pos, state);
        return state;
    }

    public static boolean isBlockPosRemovable(ChunkAccess chunk, BlockPos pos){
        OverrideStateScheduler overrideStateScheduler = ((OverrideStateScheduler) chunk);
        DestructibilityState state = overrideStateScheduler.hasOverride(pos);
        if(state == null){
            return !isInConfig(chunk.getBlockState(pos));
        } else {
            return state != DestructibilityState.INDESTRUCTIBLE;
        }
    }
}
