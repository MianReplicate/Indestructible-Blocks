package mc.mian.templatemod.common.block.custom;

import mc.mian.templatemod.TemplateMod;
import mc.mian.templatemod.common.sound.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class MeowingBlock extends Block {
    public MeowingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            SoundEvent soundEvent = TemplateMod.config.meow.get() ? ModSoundEvents.TEMPLATE_SOUND_EVENT_2.get() : ModSoundEvents.TEMPLATE_SOUND_EVENT_3.get();
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.CONSUME;
        }
    }

}
