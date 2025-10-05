
package occult.smp.Sigil.AbilitySlot.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.AbilitySlot.Ability;

public class IceAbility implements Ability {
    private static final int COOLDOWN = 400; // 20 seconds

    @Override
    public void activate(ServerPlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 2)); // 30 seconds
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600, 1));
        player.setFireTicks(0); // Extinguish fire
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
}
