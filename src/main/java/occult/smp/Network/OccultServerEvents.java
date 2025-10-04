package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;

public class OccultServerEvents {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            SigilState state = SigilState.get(player.getWorld());
            
            // Get both sigils
            SigilType primarySigil = state.getPrimarySigil(player.getUuid());
            SigilType secondarySigil = state.getSecondarySigil(player.getUuid());
            
            // Send sync packet with both sigils
            SigilSyncPackets.sendBothSigilsSync(player, primarySigil, secondarySigil);
            
            // Sync player configs
            ModNetworking.syncConfigsToPlayer(player);
        });
    }
}
