
package occult.smp.Network.Payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public record ReturnSigilsPayload() implements CustomPayload {
    public static final CustomPayload.Id<ReturnSigilsPayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "return_sigils"));
    
    public static final PacketCodec<RegistryByteBuf, ReturnSigilsPayload> CODEC = 
        PacketCodec.unit(new ReturnSigilsPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
