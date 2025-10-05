
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.Payload.AbilityActivatePayload;
import occult.smp.Sigil.Abilities.AbilityManager;
import occult.smp.Sigil.Abilities.Ability;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;
import occult.smp.util.OccultDebug;

public class AbilityActivateHandler implements ServerPlayNetworking.PlayPayloadHandler<AbilityActivatePayload> {
    @Override
    public void receive(AbilityActivatePayload payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        
        context.server().execute(() -> {
            SigilState state = SigilState.get(player.getWorld());
            
            SigilType sigilType = payload.isPrimary() 
                ? state.getPrimarySigil(player.getUuid())
                : state.getSecondarySigil(player.getUuid());
            
            if (sigilType == null || sigilType == SigilType.NONE) {
                OccultDebug.logNoSigil(player.getName().getString(), payload.isPrimary());
                return;
            }
            
            Ability ability = AbilityManager.getAbility(sigilType);
            if (ability != null) {
                OccultDebug.logAbilityActivate(player.getName().getString(), sigilType, payload.isPrimary());
                ability.activate(player);
            } else {
                OccultDebug.logNoAbility(player.getName().getString(), sigilType);
            }
        });
    }
}
