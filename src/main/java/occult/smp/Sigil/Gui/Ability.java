package occult.smp.Sigil.Gui;


import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.AbilitySlot.AbilityContext;

public interface Ability {
    boolean activate(AbilityContext ctx);
    int cooldownTicks();

    // HUD icon name: assets/occult-smp/textures/gui/<icon()>.png
    default String icon() { return null; }

    default void onEquip(ServerPlayerEntity player) {}
    default void onUnequip(ServerPlayerEntity player) {}
    default void tick(ServerPlayerEntity player) {}
}
