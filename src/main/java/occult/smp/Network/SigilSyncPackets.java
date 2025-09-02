package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.ClientSigilState;
import occult.smp.Sigil.SigilType;

public final class SigilSyncPackets {
    private SigilSyncPackets() {}

    // ✅ Called on client init
    public static void registerClient() {
        PayloadTypeRegistry.playS2C().register(SyncSigilS2CPayload.ID, SyncSigilS2CPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(SyncSigilS2CPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientSigilState.setActive(payload.sigil());
                System.out.println("[Occult Debug] Synced sigil from server: " + payload.sigil().name());
            });
        });
    }

    // ✅ Called on server when sigil changes or player joins
    public static void sendTo(ServerPlayerEntity player, SigilType sigil) {
        ServerPlayNetworking.send(player, new SyncSigilS2CPayload(sigil));
    }
}
