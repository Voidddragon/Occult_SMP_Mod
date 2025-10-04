
package occult.smp;

import net.fabricmc.api.ClientModInitializer;
import occult.smp.Network.ModNetworking;
import occult.smp.Sigil.Gui.OpenHudSettings;
import occult.smp.Sigil.Gui.SigilGUI;
import occult.smp.client.KeybindManager;

public class OccultSmpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Initialize keybinds
        KeybindManager.initialize();
        
        // Register HUD settings keybind
        OpenHudSettings.register();
        
        // Register client-side network handlers
        ModNetworking.registerClientHandlers();
        
        // Register HUD overlay
        SigilGUI.registerHudOverlay();
    }
}
