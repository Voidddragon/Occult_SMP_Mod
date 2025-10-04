
package occult.smp.Sigil;

import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.SigilSyncPackets;

public final class Sigils {
    
    /**
     * Adds a sigil to a player
     */
    public static boolean add(ServerPlayerEntity player, SigilType type) {
        SigilState state = SigilState.get(player.getWorld());
        SigilType removed = state.addSigil(player.getUuid(), type);
        boolean added = (removed != null); // If it returns non-null, something happened
        if (added || removed != SigilType.NONE) {
            SigilSyncPackets.sendBothSigils(player);
        }
        return added;
    }

    /**
     * Removes a specific sigil
     */
    public static boolean remove(ServerPlayerEntity player, SigilType type) {
        SigilState state = SigilState.get(player.getWorld());
        boolean removed = state.removeSigil(player.getUuid(), type);
        if (removed) {
            SigilSyncPackets.sendBothSigils(player);
        }
        return removed;
    }

    /**
     * Clears all sigils
     */
    public static void clearActive(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        state.clearAll(player.getUuid());
        SigilSyncPackets.sendBothSigils(player);
    }
    
    /**
     * Gets the primary sigil for a player
     */
    public static SigilType getPrimary(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        return state.getPrimarySigil(player.getUuid());
    }
    
    /**
     * Gets the secondary sigil for a player
     */
    public static SigilType getSecondary(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        return state.getSecondarySigil(player.getUuid());
    }
    
    /**
     * Checks if a player has a specific sigil
     */
    public static boolean has(ServerPlayerEntity player, SigilType type) {
        SigilState state = SigilState.get(player.getWorld());
        return state.hasSigil(player.getUuid(), type);
    }
}
