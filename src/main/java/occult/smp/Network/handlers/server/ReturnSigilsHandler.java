
package occult.smp.Network.handlers.server;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.payloads.ReturnSigilsPayload;
import occult.smp.Network.SigilSyncPackets;
import occult.smp.util.SigilUtils;

/**
 * Handles requests to return equipped sigils to inventory
 */
public class ReturnSigilsHandler implements ServerPlayNetworking.PlayPayloadHandler<ReturnSigilsPayload> {
    
    @Override
    public void receive(ReturnSigilsPayload payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        
        context.server().execute(() -> {
            SigilUtils.returnSigilsToPlayer(player);
            SigilSyncPackets.syncToClient(player);
        });
    }
}
