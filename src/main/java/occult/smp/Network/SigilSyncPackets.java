
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;

public class SigilSyncPackets {
    public static void sendBothSigilsSync(ServerPlayerEntity player, SigilType primarySigil, SigilType secondarySigil) {
        ServerPlayNetworking.send(player, new SyncBothSigilsPayload(
            primarySigil,
            secondarySigil
        ));
    }
    
    public static void sendBothSigils(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        SigilType primarySigil = state.getPrimarySigil(player.getUuid());
        SigilType secondarySigil = state.getSecondarySigil(player.getUuid());
        sendBothSigilsSync(player, primarySigil, secondarySigil);
    }
}
