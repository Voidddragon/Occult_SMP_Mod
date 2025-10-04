
package occult.smp.Network.Handler;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.ClientHudState;
import occult.smp.Network.Payload.CooldownSyncPayload;

public class CooldownSyncHandler implements ClientPlayNetworking.PlayPayloadHandler<CooldownSyncPayload> {
    
    @Override
    public void receive(CooldownSyncPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            ClientHudState.setPrimaryCooldown(payload.primaryCooldown());
            ClientHudState.setSecondaryCooldown(payload.secondaryCooldown());
        });
    }
}
