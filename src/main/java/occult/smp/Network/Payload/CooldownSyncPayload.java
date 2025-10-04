
package occult.smp.Network.Payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public record CooldownSyncPayload(
    String abilityKey,
    long cooldown,
    long maxCooldown
) implements CustomPayload {
    
    public static final CustomPayload.Id<CooldownSyncPayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "cooldown_sync"));
    
    public static final PacketCodec<RegistryByteBuf, CooldownSyncPayload> CODEC = 
        PacketCodec.tuple(
            PacketCodecs.STRING, CooldownSyncPayload::abilityKey,
            PacketCodecs.VAR_LONG, CooldownSyncPayload::cooldown,
            PacketCodecs.VAR_LONG, CooldownSyncPayload::maxCooldown,
            CooldownSyncPayload::new
        );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
