
package occult.smp.Network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ReturnSigilsPayload() implements CustomPayload {
    public static final CustomPayload.Id<ReturnSigilsPayload> ID =
            new CustomPayload.Id<>(Identifier.of("occult_smp", "return_sigils"));

    public static final PacketCodec<RegistryByteBuf, ReturnSigilsPayload> CODEC =
            PacketCodec.of(ReturnSigilsPayload::write, ReturnSigilsPayload::new);

    private ReturnSigilsPayload(RegistryByteBuf buf) {
        this();
    }

    private void write(RegistryByteBuf buf) {
        // No data needed
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
