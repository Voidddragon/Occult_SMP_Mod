
package occult.smp.Network.handlers.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import occult.smp.Network.payloads.SyncGuiConfigPayload;
import occult.smp.config.GuiConfig;
import occult.smp.util.PlayerConfigData;

/**
 * Handles GUI configuration synchronization from server
 */
public class SyncGuiConfigHandler implements ClientPlayNetworking.PlayPayloadHandler<SyncGuiConfigPayload> {
    
    @Override
    public void receive(SyncGuiConfigPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            MinecraftClient client = context.client();
            if (client.player != null) {
                PlayerConfigData configData = (PlayerConfigData) client.player;
                GuiConfig config = configData.occult$getGuiConfig();
                
                config.setScale(payload.scale());
                config.setXPosition(payload.xPosition());
                config.setYPosition(payload.yPosition());
            }
        });
    }
}
