
package occult.smp.Network.Handler;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payload.AbilityActivatePayload;
import occult.smp.Sigil.AbilitySlot.Ability;
import occult.smp.Sigil.AbilitySlot.AbilityContext;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;

public class AbilityActivateHandler implements ServerPlayNetworking.PlayPayloadHandler<AbilityActivatePayload> {
    
    @Override
    public void receive(AbilityActivatePayload payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        
        context.server().execute(() -> {
            SigilState state = SigilState.get(player.getWorld());
            
            // Determine which slot based on index
            AbilitySlot slot = payload.slotIndex() == 0 ? AbilitySlot.PRIMARY : AbilitySlot.SECONDARY;
            SigilType sigilType = slot == AbilitySlot.PRIMARY 
                ? state.getPrimarySigil(player) 
                : state.getSecondarySigil(player);
            
            if (sigilType == SigilType.NONE) {
                return;
            }
            
            // Get ability
            Ability ability = AbilityRegistry.getAbility(sigilType, slot);
            if (ability == null) {
                return;
            }
            
            // Check cooldown
            String cooldownKey = sigilType.asString() + "_" + slot.name();
            long currentTime = System.currentTimeMillis();
            long cooldownEnd = slot == AbilitySlot.PRIMARY 
                ? state.getPrimaryCooldown(player.getUuid())
                : state.getSecondaryCooldown(player.getUuid());
            
            if (cooldownEnd > currentTime) {
                return; // Still on cooldown
            }
            
            // Execute ability
            AbilityContext abilityContext = new AbilityContext(player, sigilType, slot);
            ability.execute(abilityContext);
            
            // Set cooldown
            long newCooldown = currentTime + ability.getCooldown();
            if (slot == AbilitySlot.PRIMARY) {
                state.setPrimaryCooldown(player.getUuid(), newCooldown);
            } else {
                state.setSecondaryCooldown(player.getUuid(), newCooldown);
            }
        });
    }
}
