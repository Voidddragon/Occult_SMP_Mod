
package occult.smp.Sigil.AbilitySlot.abilities;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import occult.smp.Sigil.AbilitySlot.Ability;

public class LeapAbility implements Ability {
    private static final int COOLDOWN = 100; // 5 seconds (100 ticks)

    @Override
    public void activate(ServerPlayerEntity player) {
        Vec3d lookVec = player.getRotationVec(1.0F);
        Vec3d velocity = new Vec3d(lookVec.x * 2.0, 1.5, lookVec.z * 2.0);
        player.setVelocity(velocity);
        player.velocityModified = true;
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }
}
