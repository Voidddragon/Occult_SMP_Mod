
package occult.smp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.ModNetworking;
import occult.smp.Network.SigilSyncPackets;
import occult.smp.Sigil.SigilState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OccultSmp implements ModInitializer {
    public static final String MOD_ID = "occult-smp";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Occult SMP");
        
        // Register networking (payloads + handlers)
        ModNetworking.registerReceivers();
        
        // Register player join event to sync sigils
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            
            // Sync sigils from saved state when player joins
            server.execute(() -> {
                SigilState state = SigilState.get(player.getWorld());
                SigilSyncPackets.sendBothSigils(player);
                LOGGER.info("Synced sigils for player {} on join", player.getName().getString());
            });
        });
        
        // Tick cooldowns every server tick
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            server.getWorlds().forEach(world -> {
                SigilState state = SigilState.get(world);
                // Ensure state is loaded
            });
        });
        
        LOGGER.info("Occult SMP initialized successfully");
    }
}
