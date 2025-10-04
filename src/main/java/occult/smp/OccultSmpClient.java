
package occult.smp;

import net.fabricmc.api.ClientModInitializer;
import occult.smp.Network.ModNetworking;
import occult.smp.Sigil.Gui.OpenHudSettings;
import occult.smp.Sigil.Gui.SigilGUI;
import occult.smp.client.KeybindManager;

public class OccultSmpClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        OccultSmp.LOGGER.info("Initializing Occult SMP Client");
        
        // Register client-side packet receivers
        ModNetworking.registerClientReceivers();
        
        // Register keybinds
        KeybindManager.register();
        
        // Register GUI screens
        SigilGUI.register();
        OpenHudSettings.register();
        
        OccultSmp.LOGGER.info("Occult SMP Client initialized successfully");
    }
}
