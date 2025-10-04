
package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import occult.smp.Network.Payload.SyncBothSigilsPayload;
import occult.smp.Sigil.SigilState;

public class SyncBothSigilsHandler implements ClientPlayNetworking.PlayPayloadHandler<SyncBothSigilsPayload> {
    @Override
    public void receive(SyncBothSigilsPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            MinecraftClient client = context.client();
            if (client.player != null && client.world != null) {
                SigilState state = SigilState.get(client.world);
                state.setPrimarySigil(client.player.getUuid(), payload.primary());
                state.setSecondarySigil(client.player.getUuid(), payload.secondary());
                System.out.println("[Occult Debug] Client received sigil sync - Primary: " + 
                    payload.primary() + ", Secondary: " + payload.secondary());
            }
        });
    }
}
