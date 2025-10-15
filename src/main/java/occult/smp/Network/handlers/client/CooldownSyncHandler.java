
package occult.smp.Network.handlers.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.ClientHudState;
import occult.smp.Network.payloads.CooldownSyncPayload;

public class CooldownSyncHandler {
    
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(CooldownSyncPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientHudState.setPrimaryCooldown(payload.primaryCooldown());
                ClientHudState.setSecondaryCooldown(payload.secondaryCooldown());
            });
        });
    }
}
