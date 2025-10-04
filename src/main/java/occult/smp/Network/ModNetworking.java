
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.*;
import occult.smp.Sigil.AbilitySlot.Ability;
import occult.smp.Sigil.AbilitySlot.AbilityContext;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;
import occult.smp.config.GuiConfig;
import occult.smp.config.KeybindConfig;
import occult.smp.config.PlayerConfigData;

public class ModNetworking {
    
    /**
     * Register all payloads first, then register handlers
     */
    public static void registerReceivers() {
        // First, register all payload types
        registerPayloadTypes();
        
        // Then, register all handlers
        registerServerHandlers();
    }
    
    /**
     * Register all custom payload types
     */
    private static void registerPayloadTypes() {
        // Register C2S (Client to Server) payloads
        PayloadTypeRegistry.playC2S().register(TriggerAbilityPayload.ID, TriggerAbilityPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ReturnSigilsPayload.ID, ReturnSigilsPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(UpdateKeybindConfigPayload.ID, UpdateKeybindConfigPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(UpdateGuiConfigPayload.ID, UpdateGuiConfigPayload.CODEC);
        
        // Register S2C (Server to Client) payloads
        PayloadTypeRegistry.playS2C().register(SyncBothSigilsPayload.ID, SyncBothSigilsPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncSigilCooldownPayload.ID, SyncSigilCooldownPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncKeybindConfigPayload.ID, SyncKeybindConfigPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncGuiConfigPayload.ID, SyncGuiConfigPayload.CODEC);
    }
    
    /**
     * Register all server-side packet handlers
     */
    private static void registerServerHandlers() {
        // Return sigils handler
        ReturnSigilsHandler.register();
        
        // Ability trigger handler
        ServerPlayNetworking.registerGlobalReceiver(TriggerAbilityPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = context.player();
                SigilState state = SigilState.get(player.getWorld());
                
                // Get the sigil for the requested slot
                SigilType sigil = payload.slot() == AbilitySlot.PRIMARY 
                    ? state.getPrimarySigil(player.getUuid())
                    : state.getSecondarySigil(player.getUuid());
                
                if (sigil == SigilType.NONE) return;
                
                Ability ability = AbilityRegistry.get(sigil, payload.slot());
                if (ability == null) return;
                
                AbilityState abilityState = AbilityState.get(player.getWorld());
                if (abilityState.tryConsume(player, payload.slot(), ability.cooldownTicks())) {
                    ability.activate(new AbilityContext(
                        player.getWorld(),
                        player,
                        player.getActiveHand(),
                        payload.slot(),
                        sigil
                    ));
                }
            });
        });
        
        // Update keybind config handler
        ServerPlayNetworking.registerGlobalReceiver(UpdateKeybindConfigPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = context.player();
                PlayerConfigData configData = (PlayerConfigData) player;
                
                KeybindConfig config = configData.occult$getKeybindConfig();
                config.setDropSigilsKey(payload.dropSigilsKey());
                config.setPrimaryAbilityKey(payload.primaryAbilityKey());
                config.setSecondaryAbilityKey(payload.secondaryAbilityKey());
                
                // Sync back to client to confirm
                ServerPlayNetworking.send(player, new SyncKeybindConfigPayload(
                    config.getDropSigilsKey(),
                    config.getPrimaryAbilityKey(),
                    config.getSecondaryAbilityKey()
                ));
            });
        });
        
        // Update GUI config handler
        ServerPlayNetworking.registerGlobalReceiver(UpdateGuiConfigPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = context.player();
                PlayerConfigData configData = (PlayerConfigData) player;
                
                GuiConfig config = configData.occult$getGuiConfig();
                config.setScale(payload.scale());
                config.setXPosition(payload.xPosition());
                config.setYPosition(payload.yPosition());
                
                // Sync back to client to confirm
                ServerPlayNetworking.send(player, new SyncGuiConfigPayload(
                    config.getScale(),
                    config.getXPosition(),
                    config.getYPosition()
                ));
            });
        });
    }
    
    public static void registerClientHandlers() {
        SyncBothSigilsHandler.register();
        SyncKeybindConfigHandler.register();
        SyncGuiConfigHandler.register();
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
