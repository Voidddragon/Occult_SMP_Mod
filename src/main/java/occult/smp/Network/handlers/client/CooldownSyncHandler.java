
package occult.smp.Network.handlers.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.Network.payloads.CooldownSyncPayload;
import occult.smp.Sigil.Gui.SigilGUI;

/**
 * Handles cooldown synchronization from server
 */
public class CooldownSyncHandler implements ClientPlayNetworking.PlayPayloadHandler<CooldownSyncPayload> {
    
    @Override
    public void receive(CooldownSyncPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            SigilGUI.setPrimaryCooldown(payload.primaryCooldown());
            SigilGUI.setSecondaryCooldown(payload.secondaryCooldown());
        });
    }
}
