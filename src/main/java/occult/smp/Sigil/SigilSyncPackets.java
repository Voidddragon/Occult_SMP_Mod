
package occult.smp.Sigil;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.payloads.SyncBothSigilsPayload;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;

/**
 * Utility class for syncing sigil state to clients
 */
public class SigilSyncPackets {
    
    public static void syncToClient(ServerPlayerEntity player) {
        Sigils.PlayerSigils sigils = Sigils.get(player);
        
        SyncBothSigilsPayload payload = new SyncBothSigilsPayload(
            sigils.getPrimary(),
            sigils.getSecondary()
        );
        
        ServerPlayNetworking.send(player, payload);
    }
    
    public static void syncSlotToClient(ServerPlayerEntity player, AbilitySlot slot) {
        syncToClient(player); // For now, just sync both
    }
}
