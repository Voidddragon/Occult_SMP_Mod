
package occult.smp.Network.payloads;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;
import occult.smp.Sigil.SigilType;

/**
 * Server -> Client: Sync both sigil slots
 */
public record SyncBothSigilsPayload(SigilType primary, SigilType secondary) implements OccultPayload {
    
    public static final Id<SyncBothSigilsPayload> ID = 
        new Id<>(Identifier.of(OccultSmp.MOD_ID, "sync_both_sigils"));
    
    public static final PacketCodec<RegistryByteBuf, SyncBothSigilsPayload> CODEC = 
        PacketCodec.of(SyncBothSigilsPayload::write, SyncBothSigilsPayload::read);
    
    private static SyncBothSigilsPayload read(RegistryByteBuf buf) {
        SigilType primary = SigilType.valueOf(buf.readString());
        SigilType secondary = SigilType.valueOf(buf.readString());
        return new SyncBothSigilsPayload(primary, secondary);
    }
    
    private static void write(RegistryByteBuf buf, SyncBothSigilsPayload payload) {
        buf.writeString(payload.primary.name());
        buf.writeString(payload.secondary.name());
    }
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
