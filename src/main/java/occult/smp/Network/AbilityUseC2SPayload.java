package occult.smp.Network;

import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AbilityUseC2SPayload(int slotIndex) implements CustomPayload {
    public static final Id<AbilityUseC2SPayload> ID =
            new Id<>(Identifier.of("occult-smp", "use_ability"));
    public static final PacketCodec<net.minecraft.network.RegistryByteBuf, AbilityUseC2SPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.VAR_INT, AbilityUseC2SPayload::slotIndex, AbilityUseC2SPayload::new);

    @Override public Id<? extends CustomPayload> getId() { return ID; }
}
