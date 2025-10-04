
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payloads.ReturnSigilsPayload;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;
import occult.smp.item.ModItems;

public class ReturnSigilsHandler implements ServerPlayNetworking.PlayPayloadHandler<ReturnSigilsPayload> {
    @Override
    public void receive(ReturnSigilsPayload payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        
        context.server().execute(() -> {
            SigilState state = SigilState.get(player.getWorld());
            
            // Get equipped sigils
            SigilType primary = state.getPrimarySigil(player.getUuid());
            SigilType secondary = state.getSecondarySigil(player.getUuid());
            
            // Return primary sigil
            if (primary != SigilType.NONE) {
                Item primaryItem = ModItems.fromType(primary);
                if (primaryItem != null) {
                    player.getInventory().insertStack(new ItemStack(primaryItem));
                }
            }
            
            // Return secondary sigil
            if (secondary != SigilType.NONE) {
                Item secondaryItem = ModItems.fromType(secondary);
                if (secondaryItem != null) {
                    player.getInventory().insertStack(new ItemStack(secondaryItem));
                }
            }
            
            // Clear equipped sigils
            state.clearSigils(player.getUuid());
            
            // Sync to client
            ModNetworking.syncSigilsToClient(player);
            
            System.out.println("[Occult Debug] Returned sigils to " + player.getName().getString());
        });
    }
}
