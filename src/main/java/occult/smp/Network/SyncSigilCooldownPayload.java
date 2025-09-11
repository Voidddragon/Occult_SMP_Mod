package occult.smp.Network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;
import occult.smp.Sigil.SigilType;

public record SyncSigilCooldownPayload(SigilType type, AbilitySlot slot, int ticks) implements CustomPayload {
    public static final Id<SyncSigilCooldownPayload> ID =
            new Id<>(Identifier.of(OccultSmp.MOD_ID, "sync_sigil_cooldown"));

    public static final PacketCodec<PacketByteBuf, SyncSigilCooldownPayload> CODEC =
            PacketCodec.of((value, buf) -> {
                buf.writeEnumConstant(value.type);
                buf.writeEnumConstant(value.slot);
                buf.writeInt(value.ticks);
            }, buf -> new SyncSigilCooldownPayload(
                    buf.readEnumConstant(SigilType.class),
                    buf.readEnumConstant(AbilitySlot.class),
                    buf.readInt()
            ));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
