
package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payload.*;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;
import occult.smp.config.GuiConfig;
import occult.smp.config.KeybindConfig;
import occult.smp.config.PlayerConfigData;

public class ModNetworking {
    
    /**
     * Register all payloads first, then register handlers
     */
    public static void registerReceivers() {
        // First, register all payload types
        registerPayloads();
        
        // Then, register all handlers
        registerServerReceivers();
        registerClientReceivers();
    }
    
    public static void registerPayloads() {
        // Register S2C payloads
        PayloadTypeRegistry.playS2C().register(
            SigilSyncPayload.ID, 
            SigilSyncPayload.CODEC
        );
        PayloadTypeRegistry.playS2C().register(
            CooldownSyncPayload.ID, 
            CooldownSyncPayload.CODEC
        );
        
        // Register C2S payloads
        PayloadTypeRegistry.playC2S().register(
            SigilActivatePayload.ID, 
            SigilActivatePayload.CODEC
        );
        PayloadTypeRegistry.playC2S().register(
            HudConfigPayload.ID, 
            HudConfigPayload.CODEC
        );
    }

    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(
            SigilActivatePayload.ID,
            new SigilActivateHandler()
        );
        
        ServerPlayNetworking.registerGlobalReceiver(
            HudConfigPayload.ID,
            new HudConfigHandler()
        );
    }

    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(
            SigilSyncPayload.ID,
            new SigilSyncHandler()
        );
        
        ClientPlayNetworking.registerGlobalReceiver(
            CooldownSyncPayload.ID,
            new CooldownSyncHandler()
        );
    }
    
    public static void registerClientHandlers() {
        // Empty as handlers are registered in registerClientReceivers
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
    
    // Helper method to sync configs when player joins
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
        
        // ⭐ Also sync sigils when player joins
        syncSigilsToClient(player);
    }
}
