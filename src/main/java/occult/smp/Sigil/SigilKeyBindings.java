package occult.smp.Sigil;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.Network.AbilityUseC2SPayload;
import org.lwjgl.glfw.GLFW;

public class SigilKeyBindings {
    public static KeyBinding usePrimaryAbility;
    public static KeyBinding useSecondaryAbility;

    public static void register() {
        usePrimaryAbility = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.occult_smp.use_primary_ability",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "key.categories.occult"
        ));

        useSecondaryAbility = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.occult_smp.use_secondary_ability",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "key.categories.occult"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (usePrimaryAbility.wasPressed()) {
                ClientPlayNetworking.send(new AbilityUseC2SPayload(0));
            }

            while (useSecondaryAbility.wasPressed()) {
                ClientPlayNetworking.send(new AbilityUseC2SPayload(1));
            }
        });
    }
}
