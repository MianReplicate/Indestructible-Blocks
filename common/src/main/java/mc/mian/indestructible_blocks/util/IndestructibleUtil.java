package mc.mian.indestructible_blocks.util;

import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.api.OverrideState;
import mc.mian.indestructible_blocks.common.level.IndestructibleSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class IndestructibleUtil {

    // Determines checks if block is in the force removal list
    public static boolean isPendingRemoval(BlockState state){
        return IndestructibleBlocks.pendingRemovalBlocks.stream().anyMatch(blockStateToRemove -> state == blockStateToRemove);
    }

    public static boolean isInConfig(BlockState state){
        return isInConfig(state.getBlockHolder().getRegisteredName());
    }

    public static boolean isInConfig(String blockId){
        List<String> indestructible_blocks = (List<String>) IndestructibleBlocks.config.INDESTRUCTIBLE_BLOCK_LIST.get();
        return indestructible_blocks.stream().anyMatch(blockIdBeingCompared -> blockIdBeingCompared.equals(blockId));
    }

    public static boolean playerTryToBreak(Player player, BlockState state, BlockPos pos){
        if(!IndestructibleUtil.isBlockPosRemovable((ServerLevel) player.level(), pos)){
            if(player.isCreative()){
                IndestructibleUtil.addToPendingRemoval(state);
            } else if(!player.isCreative()){
                player.displayClientMessage(Component.translatable("gui.indestructible_blocks.cannot_break"), true);
                return false;
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
    public static DestructibilityState changeOverride(ServerLevel level, BlockPos pos){
        OverrideState overrideState = IndestructibleSavedData.getOrCreate(level.getDataStorage());
        DestructibilityState state = overrideState.hasOverride(pos);
        if(state == null){
            state = isInConfig(level.getBlockState(pos)) ? DestructibilityState.DESTRUCTIBLE : DestructibilityState.INDESTRUCTIBLE;
        } else {
            state = state == DestructibilityState.DESTRUCTIBLE ? DestructibilityState.INDESTRUCTIBLE : DestructibilityState.DESTRUCTIBLE;
        }
        return changeOverride(level, pos, state);
    }

    public static DestructibilityState changeOverride(ServerLevel level, BlockPos pos, DestructibilityState state){
        OverrideState overrideState = IndestructibleSavedData.getOrCreate(level.getDataStorage());
        overrideState.putOverride(pos, state);
        return state;
    }

    public static boolean isBlockPosRemovable(ServerLevel level, BlockPos pos){
        OverrideState overrideState = IndestructibleSavedData.getOrCreate(level.getDataStorage());
        DestructibilityState state = overrideState.hasOverride(pos);
        if(state == null){
            return !isInConfig(level.getBlockState(pos));
        } else {
            return state != DestructibilityState.INDESTRUCTIBLE;
        }
    }
}
