package occult.smp.Sigil.AbilitySlot;

import net.minecraft.server.network.ServerPlayerEntity;

public interface Ability {
    boolean activate(AbilityContext ctx);
    int cooldownTicks();
    public String icon();

    default void onEquip(ServerPlayerEntity player) {}
    default void onUnequip(ServerPlayerEntity player) {}
    default void tick(ServerPlayerEntity player) {}
}
