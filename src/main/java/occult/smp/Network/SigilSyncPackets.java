
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payload.SyncBothSigilsPayload;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;
import occult.smp.util.OccultDebug;

public class SigilSyncPackets {
    
    public static void syncToClient(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        SigilType primary = state.getPrimarySigil(player.getUuid());
        SigilType secondary = state.getSecondarySigil(player.getUuid());
        
        ServerPlayNetworking.send(player, new SyncBothSigilsPayload(primary, secondary));
        OccultDebug.logSigilSync(player.getName().getString(), primary, secondary);
    }
    
    public static void syncToAll(ServerPlayerEntity anyPlayer) {
        for (ServerPlayerEntity player : anyPlayer.getServer().getPlayerManager().getPlayerList()) {
            syncToClient(player);
        }
    }
}
