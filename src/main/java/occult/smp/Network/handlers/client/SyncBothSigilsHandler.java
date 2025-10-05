
package occult.smp.Network.handlers.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import occult.smp.Network.payloads.SyncBothSigilsPayload;
import occult.smp.Sigil.SigilState;

/**
 * Handles sigil synchronization from server
 */
public class SyncBothSigilsHandler implements ClientPlayNetworking.PlayPayloadHandler<SyncBothSigilsPayload> {
    
    @Override
    public void receive(SyncBothSigilsPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            MinecraftClient client = context.client();
            if (client.player != null) {
                SigilState state = SigilState.getClientInstance();
                state.setPrimarySigil(client.player.getUuid(), payload.primary());
                state.setSecondarySigil(client.player.getUuid(), payload.secondary());
            }
        });
    }
}
