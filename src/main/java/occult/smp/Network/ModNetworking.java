package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.*;
import occult.smp.Sigil.AbilitySlot.Ability;
import occult.smp.Sigil.AbilitySlot.AbilityContext;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;

public class ModNetworking {
    private static boolean payloadsRegistered = false;
    private static boolean serverReceiversRegistered = false;

    /** Register ALL payload types here — runs once on mod init */
    public static synchronized void registerPayloads() {
        if (payloadsRegistered) return;
        payloadsRegistered = true;

        // Client → Server
        PayloadTypeRegistry.playC2S().register(
                AbilityUseC2SPayload.ID,
                AbilityUseC2SPayload.CODEC
        );

        // Server → Client
        PayloadTypeRegistry.playS2C().register(
                SyncSigilCooldownPayload.ID,
                SyncSigilCooldownPayload.CODEC
        );

        PayloadTypeRegistry.playS2C().register(
                SyncSettingsS2CPayload.ID,
                SyncSettingsS2CPayload.CODEC
        );

        PayloadTypeRegistry.playS2C().register(
                SyncSigilS2CPayload.ID,
                SyncSigilS2CPayload.CODEC
        );
    }

    /** Register server-side packet handlers here */
    public static synchronized void registerServerReceivers() {
        if (serverReceiversRegistered) return;
        serverReceiversRegistered = true;

        // Handle ability use from client
        ServerPlayNetworking.registerGlobalReceiver(AbilityUseC2SPayload.ID,
                (payload, context) -> {
                    ServerPlayerEntity player = context.player();
                    int slotIndex = payload.slotIndex();

                    context.server().execute(() -> {
                        AbilitySlot slot = slotIndex == 0
                                ? AbilitySlot.PRIMARY
                                : AbilitySlot.SECONDARY;

                        // Get the active sigil and ability
                        SigilType sigil = Sigils.getActive(player);
                        Ability ability = AbilityRegistry.get(sigil, slot);
                        if (ability == null) return;

                        // Check cooldown and consume if ready
                        AbilityState abilityState = AbilityState.get(player.getWorld());
                        if (abilityState.tryConsume(player, slot, ability.cooldownTicks())) {
                            // ✅ Ability actually activates
                            ability.activate(new AbilityContext(
                                    player.getWorld(),
                                    player,
                                    player.getActiveHand(),
                                    slot,
                                    sigil
                            ));

                            // ✅ Sync cooldown to client HUD
                            SigilState sigilState = SigilState.get(player.getWorld());
                            sigilState.setCooldown(player.getWorld(), player.getUuid(), slot, ability.cooldownTicks());
                        }
                        // ❌ If tryConsume() fails, do nothing — no cooldown reset
                    });
                }
        );
    }
}
