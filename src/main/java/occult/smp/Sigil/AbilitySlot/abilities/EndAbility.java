
package occult.smp.Sigil.AbilitySlot.abilities;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import occult.smp.Sigil.AbilitySlot.Ability;

public class EndAbility implements Ability {
    private static final int COOLDOWN = 800; // 40 seconds
    private static final double TELEPORT_DISTANCE = 10.0;

    @Override
    public void activate(ServerPlayerEntity player) {
        // Teleport player forward
        Vec3d lookVec = player.getRotationVec(1.0F);
        Vec3d targetPos = player.getPos().add(lookVec.multiply(TELEPORT_DISTANCE));
        
        ServerWorld world = player.getServerWorld();
        BlockPos blockPos = BlockPos.ofFloored(targetPos);
        
        // Find safe landing spot
        while (blockPos.getY() > world.getBottomY() && !world.getBlockState(blockPos).isFullCube(world, blockPos)) {
            blockPos = blockPos.down();
        }
        blockPos = blockPos.up();
        
        player.teleport(world, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 
                       player.getYaw(), player.getPitch());
        
        // Add brief levitation to prevent fall damage
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 60, 0)); // 3 seconds
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
}
