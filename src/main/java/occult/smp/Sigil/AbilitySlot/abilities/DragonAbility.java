
package occult.smp.Sigil.AbilitySlot.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import occult.smp.Sigil.AbilitySlot.Ability;

public class DragonAbility implements Ability {
    private static final int COOLDOWN = 1200; // 60 seconds (1 minute)

    @Override
    public void activate(ServerPlayerEntity player) {
        // Grant powerful effects
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 2)); // 30 seconds, level 3
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600, 2)); // 30 seconds, level 3
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 1)); // 30 seconds, level 2
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 600, 0)); // 30 seconds
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1)); // 30 seconds, level 2
        
        // Play dragon roar sound
        player.getWorld().playSound(
            null,
            player.getBlockPos(),
            SoundEvents.ENTITY_ENDER_DRAGON_GROWL,
            SoundCategory.PLAYERS,
            1.0F,
            1.0F
        );
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
}
