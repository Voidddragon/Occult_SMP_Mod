
package occult.smp.Sigil;

import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.SigilSyncPackets;

public final class Sigils {
    private Sigils() {} // Prevent instantiation
    
    /**
     * Get the player's primary sigil
     */
    public static SigilType getPrimarySigil(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        return state.getPrimarySigil(player.getUuid());
    }
    
    /**
     * Set the player's primary sigil
     */
    public static void setPrimarySigil(ServerPlayerEntity player, SigilType type) {
        SigilState state = SigilState.get(player.getWorld());
        state.setPrimarySigil(player.getUuid(), type);
        SigilSyncPackets.syncToClient(player);
    }
    
    /**
     * Get the player's secondary sigil
     */
    public static SigilType getSecondarySigil(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        return state.getSecondarySigil(player.getUuid());
    }
    
    /**
     * Set the player's secondary sigil
     */
    public static void setSecondarySigil(ServerPlayerEntity player, SigilType type) {
        SigilState state = SigilState.get(player.getWorld());
        state.setSecondarySigil(player.getUuid(), type);
        SigilSyncPackets.syncToClient(player);
    }
    
    /**
     * Clear both sigils for a player
     */
    public static void clearSigils(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        state.setPrimarySigil(player.getUuid(), SigilType.NONE);
        state.setSecondarySigil(player.getUuid(), SigilType.NONE);
        SigilSyncPackets.syncToClient(player);
    }
    
    /**
     * Check if player has a specific sigil equipped (either slot)
     */
    public static boolean hasSigil(ServerPlayerEntity player, SigilType type) {
        return getPrimarySigil(player) == type || getSecondarySigil(player) == type;
    }
    
    /**
     * Check if player has any sigil equipped
     */
    public static boolean hasAnySigil(ServerPlayerEntity player) {
        return getPrimarySigil(player) != SigilType.NONE || 
               getSecondarySigil(player) != SigilType.NONE;
    }
}
