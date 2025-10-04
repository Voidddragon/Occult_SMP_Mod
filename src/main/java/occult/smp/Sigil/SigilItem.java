
package occult.smp.Sigil;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import occult.smp.Network.ModNetworking;
import occult.smp.Sigil.AbilitySlot.Ability;
import occult.smp.Sigil.AbilitySlot.AbilityContext;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;
import occult.smp.item.ModItems;

import java.util.UUID;

public class SigilItem extends Item {
    private final SigilType type;

    public SigilItem(Settings settings, SigilType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        
        // Only execute on server side
        if (world.isClient) {
            return TypedActionResult.success(stack, true);
        }
    
        ServerPlayerEntity sp = (ServerPlayerEntity) player;
        UUID playerId = sp.getUuid();
        
        SigilState state = SigilState.get(world);
        
        // Debug: Print current sigils before checking
        System.out.println("[Occult Debug] Current sigils for " + sp.getName().getString() + ": " + state.getSigils(playerId));
        System.out.println("[Occult Debug] Attempting to equip: " + this.type.name());
        
        // ✅ Check if already equipped - if so, prevent hand swing and don't consume
        if (state.hasSigil(playerId, this.type)) {
            System.out.println("[Occult Debug] " + sp.getName().getString() + " attempted to equip sigil: " + this.type.name() + " (already active - blocking)");
            sp.sendMessage(net.minecraft.text.Text.literal("§cThis sigil is already equipped!"), true);
            return TypedActionResult.pass(stack);
        }
    
        // Add sigil FIRST (before consuming item)
        SigilType removed = state.addSigil(playerId, this.type);
        
        // Verify it was actually added
        if (!state.hasSigil(playerId, this.type)) {
            System.out.println("[Occult Debug] ERROR: Failed to add sigil!");
            sp.sendMessage(net.minecraft.text.Text.literal("§cFailed to equip sigil!"), true);
            return TypedActionResult.fail(stack);
        }
        
        // NOW consume the item (only after we know it was successfully added)
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
        
        System.out.println("[Occult Debug] " + sp.getName().getString() + " equipped sigil: " + this.type.name() + ", removed: " + removed);
    
        // Call onEquip for new sigil
        var newPrimary = AbilityRegistry.get(this.type, AbilitySlot.PRIMARY);
        var newSecondary = AbilityRegistry.get(this.type, AbilitySlot.SECONDARY);
        if (newPrimary != null) newPrimary.onEquip(sp);
        if (newSecondary != null) newSecondary.onEquip(sp);
    
        // If a sigil was removed, call onUnequip and drop it
        if (removed != null && removed != SigilType.NONE) {
            System.out.println("[Occult Debug] Processing removal of: " + removed);
            
            var oldPrimary = AbilityRegistry.get(removed, AbilitySlot.PRIMARY);
            var oldSecondary = AbilityRegistry.get(removed, AbilitySlot.SECONDARY);
            if (oldPrimary != null) oldPrimary.onUnequip(sp);
            if (oldSecondary != null) oldSecondary.onUnequip(sp);

            // Simply drop the removed sigil
            Item refundItem = ModItems.fromType(removed);
            if (refundItem != null) {
                ItemStack refundStack = new ItemStack(refundItem);
                player.dropItem(refundStack, false);
                System.out.println("[Occult Debug] Dropped refund: " + removed);
            }
        } else {
            System.out.println("[Occult Debug] No sigil was removed (removed = " + removed + ")");
        }

        // ⭐ SYNC TO CLIENT - This is the critical missing piece!
        ModNetworking.syncSigilsToClient(sp);
        System.out.println("[Occult Debug] Synced sigils to client for " + sp.getName().getString());

        // Play sound and update stats
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.8f, 1.2f);
        player.incrementStat(Stats.USED.getOrCreateStat(this));

        return TypedActionResult.success(stack, false);
    }

    private static void triggerAbility(World world, ServerPlayerEntity sp, Hand hand, AbilitySlot slot, SigilType sigil) {
        Ability ability = AbilityRegistry.get(sigil, slot);
        if (ability == null) return;

        var state = AbilityState.get(world);
        if (state.tryConsume(sp, slot, ability.cooldownTicks())) {
            ability.activate(new AbilityContext(world, sp, hand, slot, sigil));
        } else {
            sp.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.2f, 0.6f);
        }
    }
}