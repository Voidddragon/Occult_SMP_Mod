
package occult.smp.Network.Handler;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.Network.Payload.CooldownSyncPayload;
import occult.smp.client.ClientSigilState;

public class CooldownSyncHandler implements ClientPlayNetworking.PlayPayloadHandler<CooldownSyncPayload> {
    
    @Override
    public void receive(CooldownSyncPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            ClientSigilState.setCooldown(
                payload.abilityKey(),
                payload.cooldown(),
                payload.maxCooldown()
            );
        });
    }
}
