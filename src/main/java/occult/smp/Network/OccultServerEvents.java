package occult.smp.Network;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.SigilState;

public class OccultServerEvents {
    
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            SigilSyncPackets.syncAll(player);
        });
        
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            // Optional: Clean up player data on disconnect if needed
        });
        
        ServerLifecycleEvents.SERVER_STARTED.register(OccultServerEvents::onServerStart);
        ServerLifecycleEvents.SERVER_STOPPING.register(OccultServerEvents::onServerStop);
    }
    
    private static void onServerStart(MinecraftServer server) {
        
    }
    
    private static void onServerStop(MinecraftServer server) {
        
    }
}
