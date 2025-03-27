package mc.mian.indestructible_blocks.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Arrays;

public enum DestructibilityState {
    DESTRUCTIBLE(0, "Destructible"), INDESTRUCTIBLE(1, "Indestructible");

    private final int id;
    private final String display;

    public static final Codec<DestructibilityState> CODEC =
            Codec.INT.xmap(DestructibilityState::getState, DestructibilityState::getId).stable();
    public static final StreamCodec<ByteBuf, DestructibilityState> STREAM_CODEC =
            ByteBufCodecs.INT.map(DestructibilityState::getState, DestructibilityState::getId);

    DestructibilityState(final int id, final String display){
        this.id = id;
        this.display = display;
    }

    public int getId(){
        return this.id;
    }

    public String getDisplay(){
        return this.display;
    }

    public static DestructibilityState getState(int setting){
        return Arrays.stream(DestructibilityState.values())
                .filter(setting1 -> setting1.getId() == setting)
                .findFirst().orElse(DESTRUCTIBLE);
    }
}
