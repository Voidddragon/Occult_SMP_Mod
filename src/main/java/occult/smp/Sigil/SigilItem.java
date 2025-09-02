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
import occult.smp.Sigil.AbilitySlot.Ability;
import occult.smp.Sigil.AbilitySlot.AbilityContext;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;
import occult.smp.item.ModItems;

public class SigilItem extends Item {
    private final SigilType type;

    public SigilItem(Settings settings, SigilType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (world.isClient) return TypedActionResult.success(stack, true);

        ServerPlayerEntity sp = (ServerPlayerEntity) player;
        SigilType previous = Sigils.getActive(sp);
        SigilType next = this.type;

        // ✅ If already active, do NOT trigger ability and prevent hand swing
        if (previous == next) {
            System.out.println("[Occult Debug] " + sp.getName().getString() + " attempted to equip sigil: " + next.name() + " (already active)");
            return TypedActionResult.pass(stack); // prevents hand swing
        }

        if (!player.getAbilities().creativeMode) stack.decrement(1);

        var prevPrimary = AbilityRegistry.get(previous, AbilitySlot.PRIMARY);
        var prevSecondary = AbilityRegistry.get(previous, AbilitySlot.SECONDARY);
        if (prevPrimary != null) prevPrimary.onUnequip(sp);
        if (prevSecondary != null) prevSecondary.onUnequip(sp);

        Sigils.setActive(sp, next);
        System.out.println("[Occult Debug] " + sp.getName().getString() + " equipped sigil: " + next.name());

        var nextPrimary = AbilityRegistry.get(next, AbilitySlot.PRIMARY);
        var nextSecondary = AbilityRegistry.get(next, AbilitySlot.SECONDARY);
        if (nextPrimary != null) nextPrimary.onEquip(sp);
        if (nextSecondary != null) nextSecondary.onEquip(sp);

        if (previous != SigilType.NONE) {
            var prevItem = ModItems.fromType(previous);
            if (prevItem != null) {
                ItemStack refund = new ItemStack(prevItem);
                if (!player.getInventory().insertStack(refund)) player.dropItem(refund, false);
            }
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.8f, 1.2f);
        player.incrementStat(Stats.USED.getOrCreateStat(this));

        // ❌ Removed automatic ability activation on equip
        // AbilitySlot abilitySlot = player.isSneaking() ? AbilitySlot.SECONDARY : AbilitySlot.PRIMARY;
        // triggerAbility(world, sp, hand, abilitySlot, next);

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
