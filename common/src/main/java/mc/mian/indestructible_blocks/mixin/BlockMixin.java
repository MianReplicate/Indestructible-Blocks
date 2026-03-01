package mc.mian.indestructible_blocks.mixin;

import mc.mian.indestructible_blocks.util.IndestructibleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Block.class)
public class BlockMixin {
    private static void getDrops(ServerLevel level, BlockPos pos, CallbackInfoReturnable<List<ItemStack>> ci){
        if(!IndestructibleUtil.isBlockPosRemovable(level, pos))
            ci.setReturnValue(List.of()); // returns a list of empty loot for this block, should prevent indestructible blocks from spawning anything when "destroyed"
    }

    private static void cancelResources(LevelAccessor level, BlockPos pos, CallbackInfo ci){
        if(level instanceof ServerLevel serverLevel){
            if(!IndestructibleUtil.isBlockPosRemovable(serverLevel, pos))
                ci.cancel(); // literally just cancels dropping the resources lol
        } else {
            ci.cancel();
        }
    }

    @Inject(cancellable = true, at = @At("TAIL"), method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;)Ljava/util/List;")
    private static void getDrops(BlockState state, ServerLevel level, BlockPos pos, BlockEntity blockEntity, CallbackInfoReturnable<List<ItemStack>> cir){
        getDrops(level, pos, cir);
    }

    @Inject(cancellable = true, at = @At("TAIL"), method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;")
    private static void getDrops(BlockState state, ServerLevel level, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfoReturnable<List<ItemStack>> cir){
        getDrops(level, pos, cir);
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;)V")
    private static void dropResources(BlockState state, LevelAccessor level, BlockPos pos, BlockEntity blockEntity, CallbackInfo ci){
        cancelResources(level, pos, ci);
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V")
    private static void dropResources(BlockState state, Level level, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfo ci){
        cancelResources(level, pos, ci);
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V")
    private static void dropResources(BlockState state, Level level, BlockPos pos, CallbackInfo ci){
        cancelResources(level, pos, ci);
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V")
    private static void popResource(Level level, BlockPos pos, ItemStack stack, CallbackInfo ci){
        cancelResources(level, pos, ci);
    }
}
