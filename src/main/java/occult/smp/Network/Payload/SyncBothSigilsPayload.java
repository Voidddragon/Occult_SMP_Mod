
package occult.smp.Network.Payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;
import occult.smp.Sigil.SigilType;

public record SyncBothSigilsPayload(SigilType primary, SigilType secondary) implements CustomPayload {
    public static final CustomPayload.Id<SyncBothSigilsPayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "sync_both_sigils"));
    
    public static final PacketCodec<RegistryByteBuf, SyncBothSigilsPayload> CODEC = PacketCodec.tuple(
        PacketCodecs.STRING.xmap(SigilType::valueOf, SigilType::name), SyncBothSigilsPayload::primary,
        PacketCodecs.STRING.xmap(SigilType::valueOf, SigilType::name), SyncBothSigilsPayload::secondary,
        SyncBothSigilsPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
