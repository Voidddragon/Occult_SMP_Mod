
package occult.smp.Network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;

public record TriggerAbilityPayload(AbilitySlot slot) implements CustomPayload {
    public static final CustomPayload.Id<TriggerAbilityPayload> ID =
            new CustomPayload.Id<>(Identifier.of("occult_smp", "trigger_ability"));

    public static final PacketCodec<RegistryByteBuf, TriggerAbilityPayload> CODEC =
            PacketCodec.of(TriggerAbilityPayload::write, TriggerAbilityPayload::new);

    private TriggerAbilityPayload(RegistryByteBuf buf) {
        this(AbilitySlot.valueOf(buf.readString()));
    }

    private void write(RegistryByteBuf buf) {
        buf.writeString(slot.name());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
