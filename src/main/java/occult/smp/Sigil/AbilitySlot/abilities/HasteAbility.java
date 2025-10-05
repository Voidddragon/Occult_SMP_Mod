
package occult.smp.Sigil.AbilitySlot.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.AbilitySlot.Ability;

public class HasteAbility implements Ability {
    private static final int COOLDOWN = 300; // 15 seconds

    @Override
    public void activate(ServerPlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 600, 1)); // 30 seconds, level 2
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1)); // 30 seconds, level 2
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
}
