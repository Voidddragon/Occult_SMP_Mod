
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payload.CooldownSyncPayload;
import occult.smp.Network.Payload.SigilSyncPayload;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;

public class SigilSyncPackets {
    
    public static void sendBothSigils(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        
        SigilType primary = state.getPrimarySigil(player);
        SigilType secondary = state.getSecondarySigil(player);
        
        SigilSyncPayload payload = new SigilSyncPayload(
            primary.asString(),
            secondary.asString()
        );
        
        ServerPlayNetworking.send(player, payload);
    }
    
    public static void sendCooldowns(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        
        long primaryCooldown = state.getPrimaryCooldown(player.getUuid());
        long secondaryCooldown = state.getSecondaryCooldown(player.getUuid());
        
        CooldownSyncPayload payload = new CooldownSyncPayload(
            primaryCooldown,
            secondaryCooldown
        );
        
        ServerPlayNetworking.send(player, payload);
    }
    
    public static void syncAll(ServerPlayerEntity player) {
        sendBothSigils(player);
        sendCooldowns(player);
    }
}
