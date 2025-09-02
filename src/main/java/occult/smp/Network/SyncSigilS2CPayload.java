package occult.smp.Network;

import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.Sigil.SigilType;

public record SyncSigilS2CPayload(SigilType sigil) implements CustomPayload {
    public static final Id<SyncSigilS2CPayload> ID =
            new Id<>(Identifier.of("occult-smp", "sync_sigil"));
    public static final PacketCodec<net.minecraft.network.RegistryByteBuf, SyncSigilS2CPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING.xmap(SigilType::valueOf, SigilType::name),
                    SyncSigilS2CPayload::sigil,
                    SyncSigilS2CPayload::new
            );

    @Override public Id<? extends CustomPayload> getId() { return ID; }
}
