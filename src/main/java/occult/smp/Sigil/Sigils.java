
package occult.smp.Sigil;

import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.SigilSyncPackets;

public final class Sigils {
    private Sigils() {}

    public static void setPrimarySigil(ServerPlayerEntity player, SigilType type) {
        SigilState state = SigilState.get(player.getWorld());
        state.setPrimarySigil(player, type);
        SigilSyncPackets.sendBothSigils(player);
    }

    public static void setSecondarySigil(ServerPlayerEntity player, SigilType type) {
        SigilState state = SigilState.get(player.getWorld());
        state.setSecondarySigil(player, type);
        SigilSyncPackets.sendBothSigils(player);
    }

    public static SigilType getPrimarySigil(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        return state.getPrimarySigil(player);
    }

    public static SigilType getSecondarySigil(ServerPlayerEntity player) {
        SigilState state = SigilState.get(player.getWorld());
        return state.getSecondarySigil(player);
    }
}
