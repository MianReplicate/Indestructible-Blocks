package mc.mian.indestructible_blocks.mixin;

import com.mojang.datafixers.util.Pair;
import mc.mian.indestructible_blocks.IndestructibleBlocks;
import mc.mian.indestructible_blocks.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;
import java.util.List;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Shadow @Final public Level level;

    @Redirect(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"))
    private Iterator iterator(List instance) {
        Iterator<Pair<ItemStack, BlockPos>> iterator = instance.iterator();
        while(iterator.hasNext()){
            Pair<ItemStack, BlockPos> pair = iterator.next();
            boolean removable = ModUtil.isBlockPosRemovable((ServerLevel) this.level, pair.getSecond());
            if(!removable)
                iterator.remove();

        }
        return instance.iterator();
    }
}
