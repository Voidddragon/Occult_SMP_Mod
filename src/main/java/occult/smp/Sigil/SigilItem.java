
package occult.smp.Sigil;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SigilItem extends Item {
    private final SigilType sigilType;
    
    public SigilItem(Settings settings, SigilType sigilType) {
        super(settings);
        this.sigilType = sigilType;
    }
    
    public SigilType getSigilType() {
        return sigilType;
    }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer) {
            ItemStack stack = player.getStackInHand(hand);
            SigilState state = SigilState.get(world);
            
            SigilType currentPrimary = state.getPrimarySigil(player.getUuid());
            SigilType currentSecondary = state.getSecondarySigil(player.getUuid());
            
            // Shift + Right Click = Secondary Slot
            if (player.isSneaking()) {
                if (currentSecondary == SigilType.NONE) {
                    state.setSecondarySigil(player.getUuid(), sigilType);
                    stack.decrement(1);
                    SigilSyncPackets.syncToClient(serverPlayer);
                    return TypedActionResult.success(stack);
                }
            } else {
                // Normal Right Click = Primary Slot
                if (currentPrimary == SigilType.NONE) {
                    state.setPrimarySigil(player.getUuid(), sigilType);
                    stack.decrement(1);
                    SigilSyncPackets.syncToClient(serverPlayer);
                    return TypedActionResult.success(stack);
                }
            }
            
            // Both slots full
            return TypedActionResult.fail(stack);
        }
        
        return TypedActionResult.success(player.getStackInHand(hand));
    }
}
