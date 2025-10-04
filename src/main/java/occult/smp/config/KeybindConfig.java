
package occult.smp.config;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindConfig {
    
    public static KeyBinding PRIMARY_ABILITY;
    public static KeyBinding SECONDARY_ABILITY;
    public static KeyBinding OPEN_HUD_SETTINGS;
    
    public static void register() {
        PRIMARY_ABILITY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult-smp.primary_ability",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.occult-smp.abilities"
        ));
        
        SECONDARY_ABILITY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult-smp.secondary_ability",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            "category.occult-smp.abilities"
        ));
        
        OPEN_HUD_SETTINGS = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.occult-smp.open_hud_settings",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.occult-smp.gui"
        ));
    }
}
