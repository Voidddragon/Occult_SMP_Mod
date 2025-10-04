
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payload.CooldownSyncPayload;
import occult.smp.Network.Payload.SigilSyncPayload;
import occult.smp.Sigil.SigilState;

public class SigilSyncPackets {
    
    public static void sendSigils(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        
        SigilSyncPayload payload = new SigilSyncPayload(
            state.getPrimarySigil(player).asString(),
            state.getSecondarySigil(player).asString()
        );
        
        ServerPlayNetworking.send(player, payload);
    }
    
    public static void sendCooldown(ServerPlayerEntity player, String abilityKey, long cooldown, long maxCooldown) {
        CooldownSyncPayload payload = new CooldownSyncPayload(abilityKey, cooldown, maxCooldown);
        ServerPlayNetworking.send(player, payload);
    }
    
    public static void syncAll(ServerPlayerEntity player) {
        sendSigils(player);
        
        SigilState state = SigilState.get(player.getWorld());
        long currentTime = System.currentTimeMillis();
        
        long primaryCooldown = Math.max(0, state.getPrimaryCooldown(player.getUuid()) - currentTime);
        long secondaryCooldown = Math.max(0, state.getSecondaryCooldown(player.getUuid()) - currentTime);
        
        if (primaryCooldown > 0) {
            sendCooldown(player, "primary", primaryCooldown, primaryCooldown);
        }
        
        if (secondaryCooldown > 0) {
            sendCooldown(player, "secondary", secondaryCooldown, secondaryCooldown);
        }
    }
}
