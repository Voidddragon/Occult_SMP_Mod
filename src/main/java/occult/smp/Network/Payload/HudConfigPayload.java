
package occult.smp.Network.Payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public record HudConfigPayload(
    int x,
    int y,
    float scale,
    boolean enabled
) implements CustomPayload {
    
    public static final CustomPayload.Id<HudConfigPayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "hud_config"));
    
    public static final PacketCodec<RegistryByteBuf, HudConfigPayload> CODEC = 
        PacketCodec.tuple(
            PacketCodecs.VAR_INT, HudConfigPayload::x,
            PacketCodecs.VAR_INT, HudConfigPayload::y,
            PacketCodecs.FLOAT, HudConfigPayload::scale,
            PacketCodecs.BOOL, HudConfigPayload::enabled,
            HudConfigPayload::new
        );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
