
package occult.smp.Sigil.AbilitySlot.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.AbilitySlot.Ability;

public class OceanAbility implements Ability {
    private static final int COOLDOWN = 300; // 15 seconds

    @Override
    public void activate(ServerPlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 1200, 0)); // 60 seconds
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 1200, 0));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1200, 0));
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
}
