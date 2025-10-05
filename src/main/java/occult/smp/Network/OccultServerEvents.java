
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.util.OccultDebug;
import occult.smp.util.SigilUtils;

public class OccultServerEvents {
    
    public static void register() {
        // Player join event
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            OccultDebug.logPlayerJoin(player.getName().getString());
            ModNetworking.syncPlayerData(player);
        });
        
        // Player disconnect event
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            OccultDebug.logPlayerLeave(player.getName().getString());
            SigilUtils.returnSigilsToPlayer(player);
        });
    }
}
