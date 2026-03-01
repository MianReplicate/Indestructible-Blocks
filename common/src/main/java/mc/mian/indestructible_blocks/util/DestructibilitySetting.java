package mc.mian.indestructible_blocks.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Arrays;

public enum DestructibilitySetting {
    ONE_BLOCK(0, "One Block"), BLOCK_ID(1, "Block Id");

    private final int id;
    private final String display;

    public static final Codec<DestructibilitySetting> CODEC =
            Codec.INT.xmap(DestructibilitySetting::getSetting, DestructibilitySetting::getId).stable();
    public static final StreamCodec<ByteBuf, DestructibilitySetting> STREAM_CODEC =
            ByteBufCodecs.INT.map(DestructibilitySetting::getSetting, DestructibilitySetting::getId);

    DestructibilitySetting(final int id, final String display){
        this.id = id;
        this.display = display;
    }

    public int getId(){
        return this.id;
    }

    public String getDisplay(){
        return this.display;
    }

    public static DestructibilitySetting getSetting(int setting){
        return Arrays.stream(DestructibilitySetting.values())
                .filter(setting1 -> setting1.getId() == setting)
                .findFirst().orElse(ONE_BLOCK);
    }
}
