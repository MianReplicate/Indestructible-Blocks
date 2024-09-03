package mc.mian.indestructible_blocks.common.item.custom;

import mc.mian.indestructible_blocks.util.ModUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DestructibilityEditor extends Item {

    public DestructibilityEditor(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if(!context.getLevel().isClientSide){
            BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
            Player player = context.getPlayer();
            if(player != null){
                ModUtil.IndestructibilityState state = ModUtil.setIndestructibilityState(blockState.getBlockHolder().getRegisteredName(), !ModUtil.isInConfig(blockState));
                if(state != null){
                    player.displayClientMessage(Component.translatable("gui.indestructible_blocks.indestructibility_state", state.toString()), true);
                } else {
                    player.displayClientMessage(Component.translatable("gui.indestructible_blocks.failed_to_change_state", blockState.getBlockHolder().getRegisteredName()), true);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }
}
