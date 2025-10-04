
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payload.AbilityActivatePayload;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;
import occult.smp.Sigil.AbilitySlot.Ability;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;

public class AbilityActivateHandler implements ServerPlayNetworking.PlayPayloadHandler<AbilityActivatePayload> {
    @Override
    public void receive(AbilityActivatePayload payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        
        context.server().execute(() -> {
            SigilState state = SigilState.get(player.getWorld());
            SigilType sigilType;
            
            // Determine which sigil to use based on slot
            if (payload.isPrimary()) {
                sigilType = state.getPrimarySigil(player.getUuid());
            } else {
                sigilType = state.getSecondarySigil(player.getUuid());
            }
            
            // Validate sigil exists
            if (sigilType == null || sigilType == SigilType.NONE) {
                System.out.println("[Occult Debug] Player " + player.getName().getString() + 
                    " tried to activate " + (payload.isPrimary() ? "primary" : "secondary") + 
                    " ability but no sigil equipped");
                return;
            }
            
            // Get and execute ability
            Ability ability = AbilityRegistry.getAbility(sigilType);
            if (ability != null) {
                System.out.println("[Occult Debug] Activating " + sigilType + " ability for " + 
                    player.getName().getString());
                ability.activate(player);
            } else {
                System.out.println("[Occult Debug] No ability registered for sigil type: " + sigilType);
            }
        });
    }
}
