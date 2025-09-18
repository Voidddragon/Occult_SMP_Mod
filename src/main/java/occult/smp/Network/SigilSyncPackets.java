package occult.smp.Network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.ClientSigilState;
import occult.smp.Sigil.SigilType;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;

public class SigilSyncPackets {

    /** Called client-side to register packet receivers */
    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(
                SyncSigilS2CPayload.ID,
                (payload, context) -> {
                    // Update client state with all three fields
                    ClientSigilState.setActive(payload.sigil());
                    ClientSigilState.setCooldown(payload.sigil(), payload.slot(), payload.ticks());

                    System.out.println("[Occult Debug] Synced sigil from server: " + payload.sigil().name()
                            + " | Slot: " + payload.slot()
                            + " | Ticks: " + payload.ticks());
                }
        );
    }

    /**
     * Called server-side to send the active sigil + slot + ticks to a player.
     *
     * @param player  The player to send to
     * @param sigil   The sigil type
     * @param primary
     * @param slot    The ability slot
     * @param ticks   Remaining cooldown ticks
     */
    public static void sendSigilSync(ServerPlayerEntity player, SigilType sigil, AbilitySlot primary, AbilitySlot slot, int ticks) {
        ServerPlayNetworking.send(player, new SyncSigilS2CPayload(sigil, slot, ticks));
    }
}
