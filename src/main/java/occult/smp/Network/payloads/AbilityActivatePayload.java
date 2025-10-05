
package occult.smp.Network.payloads;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

/**
 * Client -> Server: Trigger ability activation
 */
public record AbilityActivatePayload(boolean isPrimary) implements OccultPayload {
    
    public static final Id<AbilityActivatePayload> ID = 
        new Id<>(Identifier.of(OccultSmp.MOD_ID, "ability_activate"));
    
    public static final PacketCodec<RegistryByteBuf, AbilityActivatePayload> CODEC = 
        PacketCodec.of(AbilityActivatePayload::write, AbilityActivatePayload::read);
    
    private static AbilityActivatePayload read(RegistryByteBuf buf) {
        return new AbilityActivatePayload(buf.readBoolean());
    }
    
    private static void write(RegistryByteBuf buf, AbilityActivatePayload payload) {
        buf.writeBoolean(payload.isPrimary);
    }
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
