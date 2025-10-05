package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.handlers.client.*;
import occult.smp.Network.handlers.server.*;
import occult.smp.Network.payloads.*;
import occult.smp.OccultSmp;
import occult.smp.config.GuiConfig;
import occult.smp.config.KeybindConfig;
import occult.smp.util.PlayerConfigData;

/**
 * Central networking registry for all Occult SMP packets
 */
public class ModNetworking {

    /**
     * Register all payload types (called on both client and server)
     */
    public static void registerPayloads() {
        OccultSmp.LOGGER.info("Registering network payloads");

        // Server -> Client payloads
        PayloadTypeRegistry.playS2C().register(SyncBothSigilsPayload.ID, SyncBothSigilsPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(CooldownSyncPayload.ID, CooldownSyncPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncSettingsS2CPayload.ID, SyncSettingsS2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncGuiConfigPayload.ID, SyncGuiConfigPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncKeybindConfigPayload.ID, SyncKeybindConfigPayload.CODEC);

        // Client -> Server payloads
        PayloadTypeRegistry.playC2S().register(AbilityActivatePayload.ID, AbilityActivatePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ReturnSigilsPayload.ID, ReturnSigilsPayload.CODEC);
    }

    /**
     * Register server-side packet handlers
     */
    public static void registerServerReceivers() {
        OccultSmp.LOGGER.info("Registering server-side packet handlers");

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
     * Register client-side packet handlers
     */
    public static void registerClientReceivers() {
        OccultSmp.LOGGER.info("Registering client-side packet handlers");

        ClientPlayNetworking.registerGlobalReceiver(
                SyncBothSigilsPayload.ID,
                new SyncBothSigilsHandler()
        );

        ClientPlayNetworking.registerGlobalReceiver(
                CooldownSyncPayload.ID,
                new CooldownSyncHandler()
        );

        ClientPlayNetworking.registerGlobalReceiver(
                SyncGuiConfigPayload.ID,
                new SyncGuiConfigHandler()
        );

        ClientPlayNetworking.registerGlobalReceiver(
                SyncKeybindConfigPayload.ID,
                new SyncKeybindConfigHandler()
        );
    }

    /**
     * Sync all player data to client (called on join)
     */
    public static void syncPlayerData(ServerPlayerEntity player) {
        SigilSyncPackets.syncToClient(player);
        syncConfigToClient(player);
    }

    /**
     * Sync configuration to client
     */
    private static void syncConfigToClient(ServerPlayerEntity player) {
        PlayerConfigData configData = (PlayerConfigData) player;

        // Sync GUI config
        GuiConfig guiConfig = configData.occult$getGuiConfig();
        ServerPlayNetworking.send(player, new SyncGuiConfigPayload(
                guiConfig.getScale(),
                guiConfig.getXPosition(),
                guiConfig.getYPosition()
        ));

        // Sync keybind config
        KeybindConfig keybindConfig = configData.occult$getKeybindConfig();
        ServerPlayNetworking.send(player, new SyncKeybindConfigPayload(
                keybindConfig.getDropSigilsKey(),
                keybindConfig.getPrimaryAbilityKey(),
                keybindConfig.getSecondaryAbilityKey()
        ));
    }

    /**
     * Send cooldown update to client
     */
    public static void syncCooldowns(ServerPlayerEntity player, int primaryCooldown, int secondaryCooldown) {
        ServerPlayNetworking.send(player, new CooldownSyncPayload(primaryCooldown, secondaryCooldown));
    }
}