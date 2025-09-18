package occult.smp.Network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.Sigil.SigilType;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;

public record SyncSigilS2CPayload(SigilType sigil, AbilitySlot slot, int ticks) implements CustomPayload {
    public static final Id<SyncSigilS2CPayload> ID =
            new Id<>(Identifier.of("occult-smp", "sync_sigil"));

    public static final PacketCodec<RegistryByteBuf, SyncSigilS2CPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING.xmap(SigilType::valueOf, SigilType::name), SyncSigilS2CPayload::sigil,
                    PacketCodecs.STRING.xmap(AbilitySlot::valueOf, AbilitySlot::name), SyncSigilS2CPayload::slot,
                    PacketCodecs.VAR_INT, SyncSigilS2CPayload::ticks,
                    SyncSigilS2CPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
