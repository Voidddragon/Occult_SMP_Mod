
package occult.smp.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;
import occult.smp.item.ModItems;

public class SigilUtils {
    
    /**
     * Return both equipped sigils to player's inventory and clear slots
     */
    public static void returnSigilsToPlayer(ServerPlayerEntity player) {
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
        
        OccultDebug.logSigilReturn(player.getName().getString(), primary, secondary);
        
        // Clear sigils
        state.clearSigils(player.getUuid());
    }
}
