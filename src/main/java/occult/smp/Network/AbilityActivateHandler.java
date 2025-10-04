
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
            
            // Get the appropriate sigil based on slot
            SigilType sigilType = payload.isPrimary() 
                ? state.getPrimarySigil(player.getUuid())
                : state.getSecondarySigil(player.getUuid());
            
            // Validate sigil exists
            if (sigilType == null || sigilType == SigilType.NONE) {
                return;
            }
            
            // Get and execute ability
            Ability ability = AbilityRegistry.getAbility(sigilType);
            if (ability != null) {
                ability.activate(player);
            }
        });
    }
}
