
package occult.smp.Sigil;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import occult.smp.Sigil.AbilitySlot.Ability;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;

/**
 * Definitions for all sigil abilities
 */
public final class Abilities {
    private Abilities() {}
    
    // Leap Ability
    public static final Ability LEAP = new Ability() {
        @Override
        public void activate(ServerPlayerEntity player) {
            Vec3d lookVec = player.getRotationVec(1.0F);
            player.setVelocity(lookVec.x * 2, lookVec.y * 2 + 0.5, lookVec.z * 2);
            player.velocityModified = true;
            
            player.getWorld().playSound(
                null,
                player.getBlockPos(),
                SoundEvents.ENTITY_ENDER_DRAGON_FLAP,
                SoundCategory.PLAYERS,
                1.0F,
                1.0F
            );
        }
        
        @Override
        public int getCooldown() {
            return 100; // 5 seconds
        }
    };
    
    // Strength Ability
    public static final Ability STRENGTH = new Ability() {
        @Override
        public void activate(ServerPlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.STRENGTH,
                200, // 10 seconds
                1    // Level 2
            ));
            
            player.getWorld().playSound(
                null,
                player.getBlockPos(),
                SoundEvents.ENTITY_PLAYER_LEVELUP,
                SoundCategory.PLAYERS,
                1.0F,
                1.0F
            );
        }
        
        @Override
        public int getCooldown() {
            return 400; // 20 seconds
        }
    };
    
    // Fire Ability
    public static final Ability FIRE = new Ability() {
        @Override
        public void activate(ServerPlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.FIRE_RESISTANCE,
                600, // 30 seconds
                0
            ));
            
            player.getWorld().playSound(
                null,
                player.getBlockPos(),
                SoundEvents.ITEM_FIRECHARGE_USE,
                SoundCategory.PLAYERS,
                1.0F,
                1.0F
            );
        }
        
        @Override
        public int getCooldown() {
            return 600; // 30 seconds
        }
    };
    
    // Haste Ability
    public static final Ability HASTE = new Ability() {
        @Override
        public void activate(ServerPlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.HASTE,
                300, // 15 seconds
                2    // Level 3
            ));
            
            player.getWorld().playSound(
                null,
                player.getBlockPos(),
                SoundEvents.BLOCK_BEACON_ACTIVATE,
                SoundCategory.PLAYERS,
                1.0F,
                1.5F
            );
        }
        
        @Override
        public int getCooldown() {
            return 300; // 15 seconds
        }
    };
    
    // Ice Ability
    public static final Ability ICE = new Ability() {
        @Override
        public void activate(ServerPlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SLOWNESS,
                100, // 5 seconds
                0
            ));
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.RESISTANCE,
                100, // 5 seconds
                2    // Level 3
            ));
            
            player.getWorld().playSound(
                null,
                player.getBlockPos(),
                SoundEvents.BLOCK_GLASS_BREAK,
                SoundCategory.PLAYERS,
                1.0F,
                0.5F
            );
        }
        
        @Override
        public int getCooldown() {
            return 200; // 10 seconds
        }
    };
    
    /**
     * Register all abilities
     */
    public static void registerAll() {
        AbilityRegistry.register(SigilType.LEAP, LEAP);
        AbilityRegistry.register(SigilType.STRENGTH, STRENGTH);
        AbilityRegistry.register(SigilType.FIRE, FIRE);
        AbilityRegistry.register(SigilType.HASTE, HASTE);
        AbilityRegistry.register(SigilType.ICE, ICE);
    }
}
