package occult.smp.Sigil.AbilitySlot;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import occult.smp.Sigil.SigilType;

public record AbilityContext(
        World world,
        ServerPlayerEntity player,
        Hand hand,
        AbilitySlot slot,
        SigilType sigil
) {}
