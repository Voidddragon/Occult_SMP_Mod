package occult.smp.Network;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.SigilType;
import occult.smp.Sigil.Sigils;
import occult.smp.Sigil.Abilities;
import occult.smp.HudVisibilityData;
import occult.smp.KeybindSettingsData;
import occult.smp.HudVisibility;
import occult.smp.KeybindSettings;

public class OccultServerEvents {
    private static int tickCounter = 0;

    public static void register() {
        // ✅ On player join: sync sigil, HUD, keybinds
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();

            SigilType sigil = Sigils.getActive(player);
            SigilSyncPackets.sendTo(player, sigil);

            HudVisibility hud = ((HudVisibilityData) player).occult_smp$getHudVisibility();
            KeybindSettings keybinds = ((KeybindSettingsData) player).occult_smp$getKeybindSettings();

            SettingsSyncPackets.sendTo(player, hud, keybinds); // ✅ Send both via packet
        });

        // ✅ Every second: apply sigil effects
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            if (tickCounter >= 20) {
                tickCounter = 0;
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    Abilities.applySigilEffect(player);
                }
            }
        });
    }
}
