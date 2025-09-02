package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.SigilType;
import occult.smp.Sigil.Sigils;
import occult.smp.HudVisibilityData;
import occult.smp.KeybindSettingsData;
import occult.smp.HudVisibility;
import occult.smp.KeybindSettings;

public class OccultServerEvents {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();

            // ✅ Sync equipped sigil
            SigilType sigil = Sigils.getActive(player);
            SigilSyncPackets.sendTo(player, sigil);

            // ✅ Sync HUD visibility
            HudVisibility hud = ((HudVisibilityData) player).occult_smp$getHudVisibility();
            // TODO: send hud to client via packet

            // ✅ Sync keybind settings
            KeybindSettings keybinds = ((KeybindSettingsData) player).occult_smp$getKeybindSettings();
            // TODO: send keybinds to client via packet
        });
    }
}
