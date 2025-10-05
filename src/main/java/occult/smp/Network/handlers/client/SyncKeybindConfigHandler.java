
package occult.smp.Network.handlers.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import occult.smp.Network.payloads.SyncKeybindConfigPayload;
import occult.smp.config.KeybindConfig;
import occult.smp.util.PlayerConfigData;

/**
 * Handles keybind configuration synchronization from server
 */
public class SyncKeybindConfigHandler implements ClientPlayNetworking.PlayPayloadHandler<SyncKeybindConfigPayload> {
    
    @Override
    public void receive(SyncKeybindConfigPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            MinecraftClient client = context.client();
            if (client.player != null) {
                PlayerConfigData configData = (PlayerConfigData) client.player;
                KeybindConfig config = configData.occult$getKeybindConfig();
                
                config.setDropSigilsKey(payload.dropSigilsKey());
                config.setPrimaryAbilityKey(payload.primaryAbilityKey());
                config.setSecondaryAbilityKey(payload.secondaryAbilityKey());
            }
        });
    }
}
