package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.ClientHudState;
import occult.smp.HudVisibility;
import occult.smp.KeybindSettings;


public final class SettingsSyncPackets {
    private SettingsSyncPackets() {}

    public static void registerClient() {
        PayloadTypeRegistry.playS2C().register(SyncSettingsS2CPayload.ID, SyncSettingsS2CPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(SyncSettingsS2CPayload.ID, (payload, ctx) -> {
            ctx.client().execute(() -> {
                ClientHudState.setHud(payload.hud());
                ClientHudState.setKeybinds(payload.keybinds());
            });
        });
    }

    public static void registerServer() {
        PayloadTypeRegistry.playS2C().register(SyncSettingsS2CPayload.ID, SyncSettingsS2CPayload.CODEC);
    }

    public static void sendTo(ServerPlayerEntity player, HudVisibility hud, KeybindSettings keybinds) {
        ServerPlayNetworking.send(player, new SyncSettingsS2CPayload(hud, keybinds));
    }
}
