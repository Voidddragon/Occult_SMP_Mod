package occult.smp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.Network.OccultServerEvents;
import occult.smp.Network.SettingsSyncPackets;
import occult.smp.Network.SigilSyncPackets;
import occult.smp.Sigil.Gui.OccultHudConfig;
import occult.smp.Sigil.Gui.OpenHudSettings;
import occult.smp.Sigil.Gui.SigilGUI;
import occult.smp.Sigil.SigilKeyBindings;
import occult.smp.Network.SyncSigilCooldownPayload;
import occult.smp.Sigil.ClientSigilState;

public class OccultSmpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Your existing setup
        SigilGUI.register();
        SigilKeyBindings.register();
        OccultHudConfig.load();
        ClientHudState.setHud(new HudVisibility(true));
        OpenHudSettings.register();
        SigilSyncPackets.registerClient();
        OccultServerEvents.register();
        SettingsSyncPackets.registerClient();

        // Tick down cooldowns every client tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> ClientSigilState.clientTick());

        // Listen for cooldown sync packets from the server
        ClientPlayNetworking.registerGlobalReceiver(SyncSigilCooldownPayload.ID,
                (payload, context) -> {
                    ClientSigilState.setCooldown(payload.type(), payload.slot(), payload.ticks());
                }
        );

    }
}
