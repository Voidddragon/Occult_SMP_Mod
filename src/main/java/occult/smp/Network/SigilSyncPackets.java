
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.payloads.SyncBothSigilsPayload;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;
import occult.smp.util.OccultDebug;

/**
 * Utility class for syncing sigil data to clients
 */
public class SigilSyncPackets {
    
    /**
     * Sync both sigil slots to a specific client
     */
    public static void syncToClient(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        
        SigilType primary = state.getPrimarySigil(player.getUuid());
        SigilType secondary = state.getSecondarySigil(player.getUuid());
        
        // Default to NONE if null
        if (primary == null) primary = SigilType.NONE;
        if (secondary == null) secondary = SigilType.NONE;
        
        OccultDebug.logSigilSync(player.getName().getString(), primary, secondary);
        
        ServerPlayNetworking.send(player, new SyncBothSigilsPayload(primary, secondary));
    }
    
    /**
     * Sync sigils to all online players
     */
    public static void syncToAllClients(ServerPlayerEntity sourcePlayer) {
        SigilState state = SigilState.get(sourcePlayer.getWorld());
        
        for (ServerPlayerEntity player : sourcePlayer.getServer().getPlayerManager().getPlayerList()) {
            syncToClient(player);
        }
    }
}
