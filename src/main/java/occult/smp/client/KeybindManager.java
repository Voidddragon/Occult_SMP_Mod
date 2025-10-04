
package occult.smp.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;
import occult.smp.Network.ReturnSigilsPayload;
import occult.smp.Network.TriggerAbilityPayload;
import occult.smp.config.GuiConfig;
import occult.smp.config.KeybindConfig;

public class KeybindManager {
    private static KeybindConfig config = new KeybindConfig();
    private static GuiConfig guiConfig = new GuiConfig();
    
    private static KeyBinding dropSigilsKey;
    private static KeyBinding primaryAbilityKey;
    private static KeyBinding secondaryAbilityKey;
    
    public static void initialize() {
        // Register keybindings
        dropSigilsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult.drop_sigils",
            InputUtil.Type.KEYSYM,
            config.getDropSigilsKey(),
            "key.categories.occult"
        ));
        
        primaryAbilityKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult.primary_ability",
            InputUtil.Type.KEYSYM,
            config.getPrimaryAbilityKey(),
            "key.categories.occult"
        ));
        
        secondaryAbilityKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult.secondary_ability",
            InputUtil.Type.KEYSYM,
            config.getSecondaryAbilityKey(),
            "key.categories.occult"
        ));
        
        // Register tick event for keybind handling
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (dropSigilsKey.wasPressed()) {
                ClientPlayNetworking.send(new ReturnSigilsPayload());
            }
            
            while (primaryAbilityKey.wasPressed()) {
                ClientPlayNetworking.send(new TriggerAbilityPayload(AbilitySlot.PRIMARY));
            }
            
            while (secondaryAbilityKey.wasPressed()) {
                ClientPlayNetworking.send(new TriggerAbilityPayload(AbilitySlot.SECONDARY));
            }
        });
    }
    
    public static void updateKeybinds(KeybindConfig newConfig) {
        config = newConfig;
        
        // Update the actual keybindings
        dropSigilsKey.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(config.getDropSigilsKey()));
        primaryAbilityKey.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(config.getPrimaryAbilityKey()));
        secondaryAbilityKey.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(config.getSecondaryAbilityKey()));
        
        KeyBinding.updateKeysByCode();
    }
    
    public static KeybindConfig getConfig() {
        return config;
    }
    
    public static void updateGuiConfig(GuiConfig newConfig) {
        guiConfig = newConfig;
    }
    
    public static GuiConfig getGuiConfig() {
        return guiConfig;
    }
}
