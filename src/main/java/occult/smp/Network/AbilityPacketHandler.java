package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import occult.smp.Sigil.*;
import occult.smp.Sigil.AbilitySlot.Ability;
import occult.smp.Sigil.AbilitySlot.AbilityContext;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;

public final class AbilityPacketHandler {
    private AbilityPacketHandler() {}

    public static void register() {
        PayloadTypeRegistry.playC2S().register(AbilityUseC2SPayload.ID, AbilityUseC2SPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(AbilityUseC2SPayload.ID, (payload, context) -> {
            var server = context.player().getServer();
            if (server == null) return;
            server.execute(() -> {
                var sp = context.player();
                AbilitySlot slot = payload.slotIndex() == 1 ? AbilitySlot.SECONDARY : AbilitySlot.PRIMARY;
                SigilType sigil = Sigils.getActive(sp);
                Ability ability = AbilityRegistry.get(sigil, slot);
                if (ability == null) return;
                var world = sp.getWorld();
                var state = AbilityState.get(world);
                if (state.tryConsume(sp, slot, ability.cooldownTicks())) {
                    ability.activate(new AbilityContext(world, sp, sp.getActiveHand(), slot, sigil));
                }
            });
        });
    }
}
