package occult.smp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import occult.smp.Network.SigilSyncPackets;
import occult.smp.Network.SyncSigilCooldownPayload;
import occult.smp.Sigil.ClientSigilState;
import occult.smp.Sigil.Gui.OccultHudConfig;
import occult.smp.Sigil.Gui.OpenHudSettings;
import occult.smp.Sigil.Gui.SigilGUI;
import occult.smp.Sigil.SigilKeyBindings;

public class OccultSmpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // HUD + keybind setup
        SigilGUI.register();
        SigilKeyBindings.register();
        OccultHudConfig.load();
        OpenHudSettings.register();
        SigilSyncPackets.registerClientReceivers();
        // Client tick for HUD updates
        ClientTickEvents.END_CLIENT_TICK.register(client -> ClientSigilState.clientTick());

        // ✅ Listen for cooldown sync from server
        ClientPlayNetworking.registerGlobalReceiver(
                SyncSigilCooldownPayload.ID,
                (payload, context) -> {
                    ClientSigilState.setCooldown(payload.type(), payload.slot(), payload.ticks());
                }
        );
    }
}
