
package occult.smp.Sigil.AbilitySlot.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.AbilitySlot.Ability;

public class FireAbility implements Ability {
    private static final int COOLDOWN = 500; // 25 seconds

    @Override
    public void activate(ServerPlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 1200, 0)); // 60 seconds
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0)); // 30 seconds
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
}
