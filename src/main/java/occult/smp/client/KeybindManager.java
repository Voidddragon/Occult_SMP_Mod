
package occult.smp.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import occult.smp.Network.payloads.AbilityActivatePayload;
import occult.smp.Network.payloads.ReturnSigilsPayload;
import org.lwjgl.glfw.GLFW;

/**
 * Manages client-side keybindings for sigil abilities
 */
public class KeybindManager {
    private static KeyBinding primaryAbilityKey;
    private static KeyBinding secondaryAbilityKey;
    private static KeyBinding returnSigilsKey;
    
    /**
     * Register all keybindings
     */
    public static void register() {
        primaryAbilityKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult-smp.primary_ability",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.occult-smp.abilities"
        ));
        
        secondaryAbilityKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult-smp.secondary_ability",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            "category.occult-smp.abilities"
        ));
        
        returnSigilsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult-smp.return_sigils",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.occult-smp.abilities"
        ));
        
        registerHandlers();
    }
    
    /**
     * Register tick handlers for keybindings
     */
    private static void registerHandlers() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            
            // Primary ability
            while (primaryAbilityKey.wasPressed()) {
                ClientPlayNetworking.send(new AbilityActivatePayload(true));
            }
            
            // Secondary ability
            while (secondaryAbilityKey.wasPressed()) {
                ClientPlayNetworking.send(new AbilityActivatePayload(false));
            }
            
            // Return sigils
            while (returnSigilsKey.wasPressed()) {
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
    
    public static KeyBinding getReturnSigilsKey() {
        return returnSigilsKey;
    }
}
