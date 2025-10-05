
package occult.smp.Network.payloads;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

/**
 * Server -> Client: Sync ability cooldowns
 */
public record CooldownSyncPayload(int primaryCooldown, int secondaryCooldown) implements OccultPayload {
    
    public static final Id<CooldownSyncPayload> ID = 
        new Id<>(Identifier.of(OccultSmp.MOD_ID, "cooldown_sync"));
    
    public static final PacketCodec<RegistryByteBuf, CooldownSyncPayload> CODEC = 
        PacketCodec.of(CooldownSyncPayload::write, CooldownSyncPayload::read);
    
    private static CooldownSyncPayload read(RegistryByteBuf buf) {
        return new CooldownSyncPayload(buf.readInt(), buf.readInt());
    }
    
    private static void write(RegistryByteBuf buf, CooldownSyncPayload payload) {
        buf.writeInt(payload.primaryCooldown);
        buf.writeInt(payload.secondaryCooldown);
    }
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
