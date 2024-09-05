package mc.mian.indestructible_blocks.util;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.api.RandomTickScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
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
        if(ModUtil.isInConfig(state) && !ModUtil.isBlockPosRemovable(player.level().getChunk(pos), pos)){
            if(player.isCreative()){
                ModUtil.addToPendingRemoval(state);
            } else if(!player.isCreative()){
                player.displayClientMessage(Component.translatable("gui.indestructible_blocks.cannot_break"), true);
            }
        }
        return true;
    }

    // Changes indestructibility state of a blockId
    public static IndestructibilityState setIndestructibilityState(String blockId, boolean indestructible){
        List<String> indestructible_blocks = (List<String>) IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.get();
        if(!isInConfig(blockId) && indestructible){
            indestructible_blocks.add(blockId);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.set(indestructible_blocks);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.save();
            return IndestructibilityState.INDESTRUCTIBLE;
        }else if(isInConfig(blockId) && !indestructible){
            indestructible_blocks.remove(blockId);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.set(indestructible_blocks);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.save();
            return IndestructibilityState.DESTRUCTIBLE;
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

    // When the block correlating to this pos is removed, this pos will also be removed.
    public static IndestructibilityState setBlockPosRemovable(ChunkAccess chunk, BlockPos pos, boolean removable){
        IndestructibilityState state = removable ? IndestructibilityState.DESTRUCTIBLE : IndestructibilityState.INDESTRUCTIBLE;
        RandomTickScheduler randomTickScheduler = ((RandomTickScheduler) chunk);
        if(state == IndestructibilityState.DESTRUCTIBLE){
            if(isInConfig(chunk.getBlockState(pos))){
                randomTickScheduler.scheduleRandomTick(pos);
            } else {
                randomTickScheduler.removeRandomTick(pos);
            }
        } else {
            if(isInConfig(chunk.getBlockState(pos))){
                randomTickScheduler.removeRandomTick(pos);
            } else {
                randomTickScheduler.scheduleRandomTick(pos);
            }
        }
        return state;
    }

    public static boolean isBlockPosRemovable(ChunkAccess chunk, BlockPos pos){
        RandomTickScheduler randomTickScheduler = ((RandomTickScheduler) chunk);
        return (isInConfig(chunk.getBlockState(pos)) && randomTickScheduler.hasRandomTick(pos)) || (!isInConfig(chunk.getBlockState(pos)) && !randomTickScheduler.hasRandomTick(pos));
    }
}
