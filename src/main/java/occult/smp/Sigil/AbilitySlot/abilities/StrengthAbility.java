
package occult.smp.Sigil.AbilitySlot.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.AbilitySlot.Ability;

public class StrengthAbility implements Ability {
    private static final int COOLDOWN = 400; // 20 seconds

    @Override
    public void activate(ServerPlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 1)); // 30 seconds, level 2
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 600, 0));
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
}
