
package occult.smp;

import net.fabricmc.api.ClientModInitializer;
import occult.smp.Network.ModNetworking;

public class OccultSmpClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        OccultSmp.LOGGER.info("Initializing Occult SMP Client");
        
        // Register client-side packet receivers
        ModNetworking.registerClientReceivers();
        
        OccultSmp.LOGGER.info("Occult SMP Client initialized successfully");
    }
}
