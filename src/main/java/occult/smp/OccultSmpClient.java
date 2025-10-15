
package occult.smp;

import net.fabricmc.api.ClientModInitializer;
import occult.smp.Network.ModNetworking;
import occult.smp.Sigil.Gui.OpenHudSettings;
import occult.smp.client.KeybindManager;

public class OccultSmpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OccultSmp.LOGGER.info("Initializing Occult SMP Client");
        
        // Register client-side networking
        ModNetworking.registerClientReceivers();
        
        // Register keybindings
        KeybindManager.register();
        
        // Register HUD settings keybinding
        OpenHudSettings.register();
        
        OccultSmp.LOGGER.info("Occult SMP Client initialized");
    }
}
