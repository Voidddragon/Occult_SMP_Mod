
package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.client.KeybindManager;
import occult.smp.config.GuiConfig;

public class SyncGuiConfigHandler {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(SyncGuiConfigPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                GuiConfig config = new GuiConfig();
                config.setScale(payload.scale());
                config.setXPosition(payload.xPosition());
                config.setYPosition(payload.yPosition());
                
                KeybindManager.updateGuiConfig(config);
            });
        });
    }
}
