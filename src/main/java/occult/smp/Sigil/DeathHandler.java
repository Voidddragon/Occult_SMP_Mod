package occult.smp.Sigil;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import occult.smp.item.ModItems;

public final class DeathHandler {
    private DeathHandler() {}

    public static void register() {
        // AFTER_DEATH: (LivingEntity entity, DamageSource damageSource)
        ServerLivingEntityEvents.AFTER_DEATH.register((LivingEntity entity, DamageSource source) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;

            // Optional: respect keepInventory
            if (player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;

            SigilType active = Sigils.getActive(player);
            if (active == SigilType.NONE) return;

            var item = ModItems.fromType(active);
            if (item != null) {
                player.dropItem(item.getDefaultStack(), true, false);
            }

            Sigils.clearActive(player);
        });
    }
}
