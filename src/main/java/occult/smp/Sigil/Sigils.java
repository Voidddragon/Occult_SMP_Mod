package occult.smp.Sigil;

import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.SigilSyncPackets;
import occult.smp.SigilData;

public final class Sigils {
    private static final String NBT_KEY = "occult_sigil";

    private Sigils() {}

    public static SigilType getActive(ServerPlayerEntity player) {
        String stored = ((SigilData) player).occult_smp$getEquippedSigil();
        SigilType sigil = SigilType.fromName(stored);
        SigilState.get(player.getWorld()).set(player.getUuid(), sigil);
        return sigil;
    }

    public static void setActive(ServerPlayerEntity player, SigilType type) {
        ((SigilData) player).occult_smp$setEquippedSigil(type.name());
        SigilState.get(player.getWorld()).set(player.getUuid(), type);
        SigilSyncPackets.sendTo(player, type);
    }

    public static void clearActive(ServerPlayerEntity player) {
        ((SigilData) player).occult_smp$setEquippedSigil("NONE");
        SigilState.get(player.getWorld()).clear(player.getUuid());
    }
}
