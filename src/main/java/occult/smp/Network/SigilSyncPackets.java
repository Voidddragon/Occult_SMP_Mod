
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payload.SyncBothSigilsPayload;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;

public class SigilSyncPackets {
    
    /**
     * Sync both sigils to a client
     */
    public static void syncToClient(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        SigilType primary = state.getPrimarySigil(player.getUuid());
        SigilType secondary = state.getSecondarySigil(player.getUuid());
        
        ServerPlayNetworking.send(player, new SyncBothSigilsPayload(primary, secondary));
    }
    
    /**
     * Sync to all players (for admin commands, etc.)
     */
    public static void syncToAll(ServerPlayerEntity targetPlayer) {
        for (ServerPlayerEntity player : targetPlayer.getServer().getPlayerManager().getPlayerList()) {
            syncToClient(player);
        }
    }
}
