
package occult.smp.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.Network.Payload.SigilActivatePayload;
import occult.smp.config.KeybindConfig;

public class KeybindManager {
    
    public static void register() {
        KeybindConfig.register();
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            
            while (KeybindConfig.PRIMARY_ABILITY.wasPressed()) {
                ClientPlayNetworking.send(new SigilActivatePayload(true));
            }
            
            while (KeybindConfig.SECONDARY_ABILITY.wasPressed()) {
                ClientPlayNetworking.send(new SigilActivatePayload(false));
            }
            
            while (KeybindConfig.OPEN_HUD_SETTINGS.wasPressed()) {
                // Open HUD settings screen
            }
        });
    }
}
