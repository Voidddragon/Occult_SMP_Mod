
package occult.smp.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.SigilSyncPackets;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;
import occult.smp.item.ModItems;

public class SigilUtils {
    
    /**
     * Return both equipped sigils to player's inventory and clear slots
     */
    public static void returnSigilsToPlayer(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        
        // Get current sigils
        SigilType primary = state.getPrimarySigil(player.getUuid());
        SigilType secondary = state.getSecondarySigil(player.getUuid());
        
        // Return primary sigil
        if (primary != null && primary != SigilType.NONE) {
            Item sigilItem = getSigilItem(primary);
            if (sigilItem != null) {
                player.getInventory().insertStack(new ItemStack(sigilItem));
                OccultDebug.logSigilReturn(player.getName().getString(), primary, true);
            }
            state.clearPrimarySigil(player.getUuid());
        }
        
        // Return secondary sigil
        if (secondary != null && secondary != SigilType.NONE) {
            Item sigilItem = getSigilItem(secondary);
            if (sigilItem != null) {
                player.getInventory().insertStack(new ItemStack(sigilItem));
                OccultDebug.logSigilReturn(player.getName().getString(), secondary, false);
            }
            state.clearSecondarySigil(player.getUuid());
        }
        
        // Sync to client
        SigilSyncPackets.syncToClient(player);
    }
    
    /**
     * Get the item corresponding to a sigil type
     */
    private static Item getSigilItem(SigilType type) {
        return switch (type) {
            case LEAP -> ModItems.LEAP_SIGIL;
            case EMERALD -> ModItems.EMERALD_SIGIL;
            case ICE -> ModItems.ICE_SIGIL;
            case OCEAN -> ModItems.OCEAN_SIGIL;
            case STRENGTH -> ModItems.STRENGTH_SIGIL;
            case FIRE -> ModItems.FIRE_SIGIL;
            case HASTE -> ModItems.HASTE_SIGIL;
            case END -> ModItems.END_SIGIL;
            case DRAGON -> ModItems.DRAGON_SIGIL;
            default -> null;
        };
    }
    
    /**
     * Check if player can equip a sigil in the specified slot
     */
    public static boolean canEquipSigil(ServerPlayerEntity player, SigilType type, boolean isPrimary) {
        if (type == null || type == SigilType.NONE) {
            return false;
        }
        
        SigilState state = SigilState.get(player.getWorld());
        
        // Check if sigil is already equipped in the other slot
        if (isPrimary) {
            SigilType secondary = state.getSecondarySigil(player.getUuid());
            return secondary != type;
        } else {
            SigilType primary = state.getPrimarySigil(player.getUuid());
            return primary != type;
        }
    }
    
    /**
     * Equip a sigil to the specified slot
     */
    public static boolean equipSigil(ServerPlayerEntity player, SigilType type, boolean isPrimary) {
        if (!canEquipSigil(player, type, isPrimary)) {
            return false;
        }
        
        SigilState state = SigilState.get(player.getWorld());
        
        // Return existing sigil if any
        SigilType existing = isPrimary 
            ? state.getPrimarySigil(player.getUuid())
            : state.getSecondarySigil(player.getUuid());
            
        if (existing != null && existing != SigilType.NONE) {
            Item sigilItem = getSigilItem(existing);
            if (sigilItem != null) {
                player.getInventory().insertStack(new ItemStack(sigilItem));
            }
        }
        
        // Equip new sigil
        if (isPrimary) {
            state.setPrimarySigil(player.getUuid(), type);
        } else {
            state.setSecondarySigil(player.getUuid(), type);
        }
        
        OccultDebug.logSigilEquip(player.getName().getString(), type, isPrimary);
        SigilSyncPackets.syncToClient(player);
        
        return true;
    }
}
