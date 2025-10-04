
package occult.smp.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import occult.smp.Network.Payload.AbilityActivatePayload;
import occult.smp.Network.Payload.ReturnSigilsPayload;
import org.lwjgl.glfw.GLFW;

public class KeybindManager {
    private static KeyBinding primaryAbilityKey;
    private static KeyBinding secondaryAbilityKey;
    private static KeyBinding dropSigilsKey;
    
    public static void register() {
        primaryAbilityKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult-smp.primary_ability",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            "category.occult-smp.abilities"
        ));
        
        secondaryAbilityKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult-smp.secondary_ability",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            "category.occult-smp.abilities"
        ));
        
        dropSigilsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult-smp.drop_sigils",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.occult-smp.abilities"
        ));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            
            // Primary ability activation
            while (primaryAbilityKey.wasPressed()) {
                ClientPlayNetworking.send(new AbilityActivatePayload(true));
            }
            
            // Secondary ability activation
            while (secondaryAbilityKey.wasPressed()) {
                ClientPlayNetworking.send(new AbilityActivatePayload(false));
            }
            
            // Drop sigils
            while (dropSigilsKey.wasPressed()) {
                ClientPlayNetworking.send(new ReturnSigilsPayload());
            }
        });
    }
    
    public static KeyBinding getPrimaryAbilityKey() {
        return primaryAbilityKey;
    }
    
    public static KeyBinding getSecondaryAbilityKey() {
        return secondaryAbilityKey;
    }
    
    public static KeyBinding getDropSigilsKey() {
        return dropSigilsKey;
    }
}
