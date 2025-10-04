
package occult.smp.Network.Payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public record SigilSyncPayload(
    String primarySigil,
    String secondarySigil
) implements CustomPayload {
    
    public static final CustomPayload.Id<SigilSyncPayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "sigil_sync"));
    
    public static final PacketCodec<RegistryByteBuf, SigilSyncPayload> CODEC = 
        PacketCodec.tuple(
            PacketCodecs.STRING, SigilSyncPayload::primarySigil,
            PacketCodecs.STRING, SigilSyncPayload::secondarySigil,
            SigilSyncPayload::new
        );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
