
package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.ClientHudState;
import occult.smp.Sigil.SigilType;

public class SyncBothSigilsHandler {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(SyncBothSigilsPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientHudState.setBothSigils(payload.primarySigil(), payload.secondarySigil());
            });
        });
    }
}
