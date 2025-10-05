
package occult.smp.Network.Handler;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payload.SigilActivatePayload;
import occult.smp.Sigil.AbilitySlot.AbilityContext;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;

public class SigilActivateHandler implements ServerPlayNetworking.PlayPayloadHandler<SigilActivatePayload> {
    
    @Override
    public void receive(SigilActivatePayload payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        
        context.server().execute(() -> {
            SigilState state = SigilState.get(player.getWorld());
            SigilType sigilType = payload.isPrimary() 
                ? state.getPrimarySigil(player) 
                : state.getSecondarySigil(player);
            
            if (sigilType == SigilType.NONE) {
                return;
            }
            
            // Check cooldown
            long currentTime = System.currentTimeMillis();
            long cooldown = payload.isPrimary() 
                ? state.getPrimaryCooldown(player.getUuid())
                : state.getSecondaryCooldown(player.getUuid());
            
            if (cooldown > currentTime) {
                return; // Still on cooldown
            }
            
            // Execute ability
            AbilitySlot slot = payload.isPrimary() ? AbilitySlot.PRIMARY : AbilitySlot.SECONDARY;
            var ability = AbilityRegistry.getAbility(sigilType, slot);
            
            if (ability != null) {
                AbilityContext abilityContext = new AbilityContext(player, sigilType, slot);
                ability.execute(abilityContext);
                
                // Set cooldown
                long newCooldown = currentTime + ability.getCooldown();
                if (payload.isPrimary()) {
                    state.setPrimaryCooldown(player.getUuid(), newCooldown);
                } else {
                    state.setSecondaryCooldown(player.getUuid(), newCooldown);
                }
            }
        });
    }
}
