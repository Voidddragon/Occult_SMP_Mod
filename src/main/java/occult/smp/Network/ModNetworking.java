package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModNetworking {
    public static void registerPayloads() {
        // Register for receiving on the client
        PayloadTypeRegistry.playS2C().register(SyncSigilCooldownPayload.ID, SyncSigilCooldownPayload.CODEC);

        // Register for receiving on the server (if needed)
        PayloadTypeRegistry.playC2S().register(SyncSigilCooldownPayload.ID, SyncSigilCooldownPayload.CODEC);
    }
}
