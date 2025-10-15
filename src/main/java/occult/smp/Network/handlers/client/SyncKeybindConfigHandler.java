
package occult.smp.Network.handlers.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import occult.smp.Network.payloads.SyncKeybindConfigPayload;
import occult.smp.config.KeybindConfig;
import occult.smp.util.PlayerConfigData;
import org.lwjgl.glfw.GLFW;

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
                
                // Convert string key names to GLFW key codes
                config.setDropSigilsKey(getKeyCode(payload.dropSigilsKey()));
                config.setPrimaryAbilityKey(getKeyCode(payload.primaryAbilityKey()));
                config.setSecondaryAbilityKey(getKeyCode(payload.secondaryAbilityKey()));
            }
        });
    }
    
    private int getKeyCode(String keyName) {
        // Convert key name to GLFW key code
        return switch (keyName.toUpperCase()) {
            case "R" -> GLFW.GLFW_KEY_R;
            case "V" -> GLFW.GLFW_KEY_V;
            case "B" -> GLFW.GLFW_KEY_B;
            // Add more key mappings as needed
            default -> GLFW.GLFW_KEY_UNKNOWN;
        };
    }
}
