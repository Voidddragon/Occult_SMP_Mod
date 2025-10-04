
package occult.smp.Network.Handler;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import occult.smp.Network.Payload.HudConfigPayload;
import occult.smp.config.PlayerConfigData;

public class HudConfigHandler implements ServerPlayNetworking.PlayPayloadHandler<HudConfigPayload> {
    
    @Override
    public void receive(HudConfigPayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            PlayerConfigData.saveHudConfig(
                context.player().getUuid(),
                payload.x(),
                payload.y(),
                payload.scale(),
                payload.enabled()
            );
        });
    }
}
