
package occult.smp.Sigil;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import occult.smp.Network.SigilSyncPackets;

public class SigilItem extends Item {
    private final SigilType type;

    public SigilItem(Settings settings, SigilType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity serverPlayer) {
            SigilState state = SigilState.get(world);
            state.setPrimarySigil(serverPlayer, type);
            SigilSyncPackets.sendBothSigils(serverPlayer);
        }
        return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
    }

    public SigilType getType() {
        return type;
    }
}
