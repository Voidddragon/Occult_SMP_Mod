package occult.smp;

import net.fabricmc.api.ClientModInitializer;
import occult.smp.Network.OccultServerEvents;
import occult.smp.Network.SettingsSyncPackets;
import occult.smp.Network.SigilSyncPackets;
import occult.smp.Sigil.Gui.OccultHudConfig;
import occult.smp.Sigil.Gui.OpenHudSettings;
import occult.smp.Sigil.Gui.SigilGUI;
import occult.smp.Sigil.SigilKeyBindings;



public class OccultSmpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SigilGUI.register();
        SigilKeyBindings.register();
        OccultHudConfig.load();
        OpenHudSettings.register();
        SigilSyncPackets.registerClient();
        OccultServerEvents.register();
        SettingsSyncPackets.registerClient();
    }
}
