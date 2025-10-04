
package occult.smp.Network.Handler;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import occult.smp.ClientHudState;
import occult.smp.Network.Payload.SigilSyncPayload;
import occult.smp.Sigil.SigilType;

public class SigilSyncHandler implements ClientPlayNetworking.PlayPayloadHandler<SigilSyncPayload> {
    
    @Override
    public void receive(SigilSyncPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            SigilType primary = SigilType.fromString(payload.primarySigil());
            SigilType secondary = SigilType.fromString(payload.secondarySigil());
            
            ClientHudState.setPrimarySigil(primary);
            ClientHudState.setSecondarySigil(secondary);
        });
    }
}
