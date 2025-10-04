package occult.smp.Network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.HudVisibility;
import occult.smp.KeybindSettings;

public record SyncSettingsS2CPayload(HudVisibility hud, KeybindSettings keybinds) implements CustomPayload {
    public static final Id<SyncSettingsS2CPayload> ID =
            new Id<>(Identifier.of("occult_smp", "sync_settings"));

    public static final PacketCodec<RegistryByteBuf, SyncSettingsS2CPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.BOOL, p -> p.hud().visible(),
                    PacketCodecs.BOOL, p -> p.keybinds().enabled(),
                    (hudVisible, keybindEnabled) ->
                            new SyncSettingsS2CPayload(
                                    new HudVisibility(hudVisible),
                                    new KeybindSettings(keybindEnabled)
                            )
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
