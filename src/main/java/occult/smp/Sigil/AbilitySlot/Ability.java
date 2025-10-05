
package occult.smp.Sigil.AbilitySlot;

import net.minecraft.server.network.ServerPlayerEntity;

public interface Ability {
    void activate(ServerPlayerEntity player);
    int getCooldown();
}
