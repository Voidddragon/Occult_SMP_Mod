
package occult.smp;

import net.fabricmc.api.ModInitializer;
import occult.smp.Network.ModNetworking;
import occult.smp.Network.OccultServerEvents;
import occult.smp.Sigil.AbilitySlot.AbilityInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OccultSmp implements ModInitializer {
    public static final String MOD_ID = "occult-smp";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Occult SMP Mod");
        
        // Register network payloads
        ModNetworking.registerPayloads();
        
        // Register server-side packet receivers
        ModNetworking.registerServerReceivers();
        
        // Register server events
        OccultServerEvents.register();
        
        // Register abilities
        AbilityInitializer.registerAbilities();
        
        LOGGER.info("Occult SMP Mod initialized successfully");
    }
}
