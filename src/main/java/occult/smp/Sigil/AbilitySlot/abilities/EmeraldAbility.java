
package occult.smp.Sigil.AbilitySlot.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.AbilitySlot.Ability;

public class EmeraldAbility implements Ability {
    private static final int COOLDOWN = 600; // 30 seconds

    @Override
    public void activate(ServerPlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 1200, 0)); // 60 seconds
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, 1200, 0));
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
}
