
package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payload.*;
import occult.smp.config.GuiConfig;
import occult.smp.config.KeybindConfig;
import occult.smp.util.PlayerConfigData;

public class ModNetworking {
    
    public static void registerPayloads() {
        // S2C Payloads
        PayloadTypeRegistry.playS2C().register(CooldownSyncPayload.ID, CooldownSyncPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncBothSigilsPayload.ID, SyncBothSigilsPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncKeybindConfigPayload.ID, SyncKeybindConfigPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncGuiConfigPayload.ID, SyncGuiConfigPayload.CODEC);
        
        // C2S Payloads
        PayloadTypeRegistry.playC2S().register(AbilityActivatePayload.ID, AbilityActivatePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ReturnSigilsPayload.ID, ReturnSigilsPayload.CODEC);
    }

    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(AbilityActivatePayload.ID, new AbilityActivateHandler());
        ServerPlayNetworking.registerGlobalReceiver(ReturnSigilsPayload.ID, new ReturnSigilsHandler());
    }

    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(CooldownSyncPayload.ID, new CooldownSyncHandler());
        ClientPlayNetworking.registerGlobalReceiver(SyncBothSigilsPayload.ID, new SyncBothSigilsHandler());
        ClientPlayNetworking.registerGlobalReceiver(SyncKeybindConfigPayload.ID, new SyncKeybindConfigHandler());
        ClientPlayNetworking.registerGlobalReceiver(SyncGuiConfigPayload.ID, new SyncGuiConfigHandler());
    }
    
    /**
     * Sync all player data on join
     */
    public static void syncPlayerData(ServerPlayerEntity player) {
        // Sync configs
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
        
        // Sync sigils using centralized method
        SigilSyncPackets.syncToClient(player);
    }
}
