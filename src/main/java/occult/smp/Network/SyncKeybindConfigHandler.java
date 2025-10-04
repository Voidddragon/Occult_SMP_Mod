
package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.client.KeybindManager;
import occult.smp.config.KeybindConfig;

public class SyncKeybindConfigHandler {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(SyncKeybindConfigPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                KeybindConfig config = new KeybindConfig();
                config.setDropSigilsKey(payload.dropSigilsKey());
                config.setPrimaryAbilityKey(payload.primaryAbilityKey());
                config.setSecondaryAbilityKey(payload.secondaryAbilityKey());
                
                KeybindManager.updateKeybinds(config);
            });
        });
    }
}
