package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;
import occult.smp.item.ModItems;

public class OccultServerEvents {
    
    public static void register() {
        // Sync sigils and configs when player joins
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            ModNetworking.syncConfigsToPlayer(player);
        });
        
        // Return sigils when player disconnects
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            SigilState state = SigilState.get(player.getWorld());
            
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
            
            // Clear sigils
            state.clearSigils(player.getUuid());
        });
    }
}
