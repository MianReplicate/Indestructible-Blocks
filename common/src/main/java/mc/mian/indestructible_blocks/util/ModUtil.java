package mc.mian.indestructible_blocks.util;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ModUtil {

    // Determines checks if block is in the force removal list
    public static boolean isRequestedToBeForceRemoved(BlockState state){
        if(IndestructibleBlocks.blocksToRemove.stream().anyMatch(blockStateToRemove -> state == blockStateToRemove)){
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

    public static boolean playerTryToBreak(Player player, BlockState state){
        if(player.isCreative() && ModUtil.isInConfig(state)){
            ModUtil.forceDestructibility(state);
        } else if(!player.isCreative()){
            player.displayClientMessage(Component.translatable("gui.indestructible_blocks.cannot_break"), true);
        }
        return true;
    }

    // Changes indestructibility state of a blockId
    public static IndestructibilityState setIndestructibilityState(String blockId, boolean indestructible){
        List<String> indestructible_blocks = (List<String>) IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.get();
        IndestructibleBlocks.LOGGER.info("changing indestructibility of "+blockId);
        if(!isInConfig(blockId) && indestructible){
            IndestructibleBlocks.LOGGER.info("now indestructible");
            indestructible_blocks.add(blockId);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.set(indestructible_blocks);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.save();
            return IndestructibilityState.INDESTRUCTIBLE;
        }else if(isInConfig(blockId) && !indestructible){
            IndestructibleBlocks.LOGGER.info("now not indestructible");
            indestructible_blocks.remove(blockId);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.set(indestructible_blocks);
            IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.save();
            return IndestructibilityState.DESTRUCTIBLE;
        }
        return null;
    }

    // Allows the block to be removed regardless of its indestructibility state. Allows people in creative mode to destroy the block
    public static void forceDestructibility(BlockState state){
        if(IndestructibleBlocks.blocksToRemove.stream().noneMatch(blockStateToRemove -> state == blockStateToRemove)){
            IndestructibleBlocks.blocksToRemove.add(state);
        }
    }
}
