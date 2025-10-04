
package occult.smp.Sigil.AbilitySlot;

import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.SigilType;

public record AbilityContext(
    ServerPlayerEntity player,
    SigilType sigilType,
    AbilitySlot slot
) {
    
    public boolean isPrimary() {
        return slot == AbilitySlot.PRIMARY;
    }
    
    public boolean isSecondary() {
        return slot == AbilitySlot.SECONDARY;
    }
}
