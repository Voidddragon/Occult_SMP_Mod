package occult.smp.Sigil.Gui;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class OpenHudSettings {
    private static KeyBinding openHudSettings;

    public static void register() {
        openHudSettings = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.occult.open_hud_settings",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_H,
                        "key.categories.occult"
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openHudSettings.wasPressed()) {
                client.setScreen(OccultHudConfigScreen.create(client.currentScreen));
            }
        });
    }
}
