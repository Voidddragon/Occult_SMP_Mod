
package occult.smp.Network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;
import occult.smp.Sigil.SigilType;

public record SyncSigilCooldownPayload(SigilType type, AbilitySlot slot, int ticks) implements CustomPayload {
    public static final CustomPayload.Id<SyncSigilCooldownPayload> ID =
            new CustomPayload.Id<>(Identifier.of("occult_smp", "sync_sigil_cooldown"));

    public static final PacketCodec<RegistryByteBuf, SyncSigilCooldownPayload> CODEC =
            PacketCodec.of(SyncSigilCooldownPayload::write, SyncSigilCooldownPayload::new);

    private SyncSigilCooldownPayload(RegistryByteBuf buf) {
        this(
            SigilType.valueOf(buf.readString()),
            AbilitySlot.valueOf(buf.readString()),
            buf.readInt()
        );
    }

    private void write(RegistryByteBuf buf) {
        buf.writeString(type.name());
        buf.writeString(slot.name());
        buf.writeInt(ticks);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
