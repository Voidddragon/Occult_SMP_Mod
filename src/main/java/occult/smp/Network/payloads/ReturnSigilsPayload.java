
package occult.smp.Network.payloads;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

/**
 * Client -> Server: Request to return equipped sigils to inventory
 */
public record ReturnSigilsPayload() implements OccultPayload {
    
    public static final Id<ReturnSigilsPayload> ID = 
        new Id<>(Identifier.of(OccultSmp.MOD_ID, "return_sigils"));
    
    public static final PacketCodec<RegistryByteBuf, ReturnSigilsPayload> CODEC = 
        PacketCodec.of(ReturnSigilsPayload::write, ReturnSigilsPayload::read);
    
    private static ReturnSigilsPayload read(RegistryByteBuf buf) {
        return new ReturnSigilsPayload();
    }
    
    private static void write(RegistryByteBuf buf, ReturnSigilsPayload payload) {
        // Empty payload
    }
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
