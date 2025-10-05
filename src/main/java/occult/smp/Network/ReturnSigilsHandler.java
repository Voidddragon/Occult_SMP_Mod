
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payloads.ReturnSigilsPayload;
import occult.smp.util.SigilUtils;

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
