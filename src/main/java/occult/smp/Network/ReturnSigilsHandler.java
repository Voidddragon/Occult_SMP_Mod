
package occult.smp.Network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.SigilState;
import occult.smp.Sigil.SigilType;
import occult.smp.Sigil.Sigils;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;
import occult.smp.item.ModItems;

import java.util.List;

public class ReturnSigilsHandler {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ReturnSigilsPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = context.player();
                SigilState state = SigilState.get(player.getWorld());
                
                // Get all equipped sigils
                List<SigilType> sigils = state.getSigils(player.getUuid());
                
                // Call onUnequip for all abilities
                for (SigilType sigil : sigils) {
                    var primary = AbilityRegistry.get(sigil, AbilitySlot.PRIMARY);
                    var secondary = AbilityRegistry.get(sigil, AbilitySlot.SECONDARY);
                    if (primary != null) primary.onUnequip(player);
                    if (secondary != null) secondary.onUnequip(player);
                }
                
                // Return all sigils to inventory
                for (SigilType sigil : sigils) {
                    if (sigil != SigilType.NONE) {
                        var item = ModItems.fromType(sigil);
                        if (item != null) {
                            ItemStack stack = new ItemStack(item);
                            if (!player.getInventory().insertStack(stack)) {
                                player.dropItem(stack, false);
                            }
                        }
                    }
                }
                
                // Clear all sigils
                Sigils.clearActive(player);
                
                System.out.println("[Occult Debug] " + player.getName().getString() + " returned all sigils");
            });
        });
    }
}
