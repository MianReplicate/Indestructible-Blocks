package mc.mian.indestructible_blocks.common.item.custom;

import mc.mian.indestructible_blocks.util.DestructibilitySetting;
import mc.mian.indestructible_blocks.util.DestructibilityState;
import mc.mian.indestructible_blocks.util.ModUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;


public class DestructibilityEditor extends Item {

    public DestructibilityEditor(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if(!context.getLevel().isClientSide()){
            BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
            Player player = context.getPlayer();
            if(player != null){
                if(player.isCrouching()) {
                    context.getItemInHand().use(context.getLevel(), player, context.getHand());
                    return InteractionResult.PASS;
                }

                CompoundTag tag = context.getItemInHand().getOrCreateTag();
                String string_setting = tag.getString("destructibility_setting").isBlank() ? DestructibilitySetting.BLOCK_ID.getSetting() : tag.getString("destructibility_setting");
                DestructibilitySetting setting = DestructibilitySetting.getEnum(string_setting);
                if(setting == DestructibilitySetting.BLOCK_ID){
                    DestructibilityState state = ModUtil.setIndestructibilityState(blockState.getBlockHolder().unwrapKey().get().location().toString(), !ModUtil.isInConfig(blockState));
                    if(state != null){
                        player.displayClientMessage(Component.translatable("gui.indestructible_blocks.indestructibility_state", blockState.getBlockHolder().unwrapKey().get().location().toString(), state.toString()), true);
                    } else {
                        player.displayClientMessage(Component.translatable("gui.indestructible_blocks.failed_to_change_state", blockState.getBlockHolder().unwrapKey().get().location().toString()), true);
                    }
                } else if(setting == DestructibilitySetting.ONE_BLOCK){
                    DestructibilityState newState = ModUtil.changeOverride((ServerLevel) context.getLevel(), context.getClickedPos());
                    if(newState != null){
                        player.displayClientMessage(Component.translatable("gui.indestructible_blocks.block_indestructibility_state", newState.getSetting()), true);
                    } else {
                        player.displayClientMessage(Component.translatable("gui.indestructible_blocks.failed_to_change_block_state", context.getClickedPos()), true);
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(player.isCrouching() && !level.isClientSide()){
            ItemStack item = player.getItemInHand(usedHand);
            CompoundTag tag = item.getOrCreateTag();
            DestructibilitySetting set = DestructibilitySetting.BLOCK_ID.getSetting().equals(tag.getString("destructibility_setting")) || tag.getString("destructibility_setting").isBlank() ? DestructibilitySetting.ONE_BLOCK: DestructibilitySetting.BLOCK_ID;
            tag.putString("destructibility_setting", set.getSetting());
            item.setTag(tag);
            player.displayClientMessage(Component.translatable("gui.indestructible_blocks.setting_state", set.getSetting()), true);
            return InteractionResultHolder.consume(item);
        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }
}
