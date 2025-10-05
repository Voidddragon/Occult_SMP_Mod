
package occult.smp.Sigil.AbilitySlot;

import net.minecraft.server.network.ServerPlayerEntity;

public record AbilityContext(ServerPlayerEntity player, AbilitySlot slot) {
}
