
package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payloads.*;
import occult.smp.config.GuiConfig;
import occult.smp.config.KeybindConfig;
import occult.smp.util.PlayerConfigData;

public class ModNetworking {
    
    /**
     * Register all payload types (call this from both client and server)
     */
    public static void registerPayloads() {
        // Register S2C payloads
        // REMOVED: SigilSyncPayload - use SyncBothSigilsPayload instead
        PayloadTypeRegistry.playS2C().register(
            CooldownSyncPayload.ID, 
            CooldownSyncPayload.CODEC
        );
        PayloadTypeRegistry.playS2C().register(
            SyncBothSigilsPayload.ID,
            SyncBothSigilsPayload.CODEC
        );
        PayloadTypeRegistry.playS2C().register(
            SyncKeybindConfigPayload.ID,
            SyncKeybindConfigPayload.CODEC
        );
        PayloadTypeRegistry.playS2C().register(
            SyncGuiConfigPayload.ID,
            SyncGuiConfigPayload.CODEC
        );
        
        // Register C2S payloads
        PayloadTypeRegistry.playC2S().register(
            AbilityActivatePayload.ID, 
            AbilityActivatePayload.CODEC
        );
        PayloadTypeRegistry.playC2S().register(
            ReturnSigilsPayload.ID,
            ReturnSigilsPayload.CODEC
        );
    }

    /**
     * Register server-side packet receivers (call this from server initialization)
     */
    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(
            AbilityActivatePayload.ID,
            new AbilityActivateHandler()
        );
        
        ServerPlayNetworking.registerGlobalReceiver(
            ReturnSigilsPayload.ID,
            new ReturnSigilsHandler()
        );
    }

    /**
     * Register client-side packet receivers (call this from client initialization)
     */
    public static void registerClientReceivers() {
        // REMOVED: SigilSyncHandler
        ClientPlayNetworking.registerGlobalReceiver(
            CooldownSyncPayload.ID,
            new CooldownSyncHandler()
        );
        
        ClientPlayNetworking.registerGlobalReceiver(
            SyncBothSigilsPayload.ID,
            new SyncBothSigilsHandler()
        );
        
        ClientPlayNetworking.registerGlobalReceiver(
            SyncKeybindConfigPayload.ID,
            new SyncKeybindConfigHandler()
        );
        
        ClientPlayNetworking.registerGlobalReceiver(
            SyncGuiConfigPayload.ID,
            new SyncGuiConfigHandler()
        );
    }
    
    /**
     * Sync sigils to a specific client
     */
    public static void syncSigilsToClient(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        SigilType primary = state.getPrimarySigil(player.getUuid());
        SigilType secondary = state.getSecondarySigil(player.getUuid());
        
        ServerPlayNetworking.send(player, new SyncBothSigilsPayload(primary, secondary));
        System.out.println("[Occult Debug] Sent sigil sync packet - Primary: " + primary + ", Secondary: " + secondary);
    }
    
    /**
     * Sync configs and sigils when player joins
     */
    public static void syncConfigsToPlayer(ServerPlayerEntity player) {
        PlayerConfigData configData = (PlayerConfigData) player;
        
        KeybindConfig keybindConfig = configData.occult$getKeybindConfig();
        ServerPlayNetworking.send(player, new SyncKeybindConfigPayload(
            keybindConfig.getDropSigilsKey(),
            keybindConfig.getPrimaryAbilityKey(),
            keybindConfig.getSecondaryAbilityKey()
        ));
        
        GuiConfig guiConfig = configData.occult$getGuiConfig();
        ServerPlayNetworking.send(player, new SyncGuiConfigPayload(
            guiConfig.getScale(),
            guiConfig.getXPosition(),
            guiConfig.getYPosition()
        ));
        
        // Use centralized sync method
        SigilSyncPackets.syncToClient(player);
    }
}
