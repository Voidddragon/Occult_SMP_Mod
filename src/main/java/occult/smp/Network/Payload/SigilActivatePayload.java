
package occult.smp.Network.Payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public record SigilActivatePayload(
    boolean isPrimary
) implements CustomPayload {
    
    public static final CustomPayload.Id<SigilActivatePayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "sigil_activate"));
    
    public static final PacketCodec<RegistryByteBuf, SigilActivatePayload> CODEC = 
        PacketCodec.tuple(
            PacketCodecs.BOOL, SigilActivatePayload::isPrimary,
            SigilActivatePayload::new
        );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
