package occult.smp.Sigil;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import occult.smp.item.ModItems;

public final class DeathHandler {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(DeathHandler::onDeath);
    }

    private static void onDeath(LivingEntity entity, DamageSource source) {
        if (!(entity instanceof ServerPlayerEntity player)) return;
        if (!player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;

        SigilState state = SigilState.get(player.getWorld());
        
        // Get primary and secondary sigils
        SigilType primarySigil = state.getPrimarySigil(player.getUuid());
        SigilType secondarySigil = state.getSecondarySigil(player.getUuid());

        // Drop primary sigil if equipped
        if (primarySigil != SigilType.NONE) {
            var item = ModItems.fromType(primarySigil);
            if (item != null) {
                ItemStack stack = new ItemStack(item);
                player.dropItem(stack, false);
            }
        }
        
        // Drop secondary sigil if equipped
        if (secondarySigil != SigilType.NONE) {
            var item = ModItems.fromType(secondarySigil);
            if (item != null) {
                ItemStack stack = new ItemStack(item);
                player.dropItem(stack, false);
            }
        }

        // Clear all sigils
        Sigils.clearActive(player);
    }
}