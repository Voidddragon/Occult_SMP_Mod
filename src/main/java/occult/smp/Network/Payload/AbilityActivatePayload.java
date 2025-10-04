
package occult.smp.Network.Payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public record AbilityActivatePayload(
    int slotIndex
) implements CustomPayload {
    
    public static final CustomPayload.Id<AbilityActivatePayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "ability_activate"));
    
    public static final PacketCodec<RegistryByteBuf, AbilityActivatePayload> CODEC = 
        PacketCodec.tuple(
            PacketCodecs.VAR_INT, AbilityActivatePayload::slotIndex,
            AbilityActivatePayload::new
        );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
