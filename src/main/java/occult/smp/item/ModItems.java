package occult.smp.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;
import occult.smp.Sigil.SigilItem;
import occult.smp.Sigil.SigilType;

public class ModItems {

    // Register each as a fireproof SigilItem tied to a SigilType
    public static final Item LEAP_SIGIL =
            registerItem("leap_sigil", new SigilItem(new Item.Settings().fireproof().maxCount(1), SigilType.LEAP));
    public static final Item EMERALD_SIGIL =
            registerItem("emerald_sigil", new SigilItem(new Item.Settings().fireproof().maxCount(1), SigilType.EMERALD));
    public static final Item ICE_SIGIL =
            registerItem("ice_sigil", new SigilItem(new Item.Settings().fireproof().maxCount(1), SigilType.ICE));
    public static final Item OCEAN_SIGIL =
            registerItem("ocean_sigil", new SigilItem(new Item.Settings().fireproof().maxCount(1), SigilType.OCEAN));

    // Newly added sigil items (like other non-leap sigils)
    public static final Item STRENGTH_SIGIL =
            registerItem("strength_sigil", new SigilItem(new Item.Settings().fireproof().maxCount(1), SigilType.STRENGTH));
    public static final Item FIRE_SIGIL =
            registerItem("fire_sigil", new SigilItem(new Item.Settings().fireproof().maxCount(1), SigilType.FIRE));
    public static final Item HASTE_SIGIL =
            registerItem("haste_sigil", new SigilItem(new Item.Settings().fireproof().maxCount(1), SigilType.HASTE));
    public static final Item END_SIGIL =
            registerItem("end_sigil", new SigilItem(new Item.Settings().fireproof().maxCount(1), SigilType.END));
    public static final Item DRAGON_SIGIL =
            registerItem("dragon_sigil", new SigilItem(new Item.Settings().fireproof().maxCount(1), SigilType.DRAGON));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(OccultSmp.MOD_ID,name), item);
    }

    // Map SigilType -> Item for refunds
    public static Item fromType(SigilType type) {
        return switch (type) {
            case LEAP -> LEAP_SIGIL;
            case EMERALD -> EMERALD_SIGIL;
            case ICE -> ICE_SIGIL;
            case OCEAN -> OCEAN_SIGIL;
            case STRENGTH -> STRENGTH_SIGIL;
            case FIRE -> FIRE_SIGIL;
            case HASTE -> HASTE_SIGIL;
            case END -> END_SIGIL;
            case DRAGON -> DRAGON_SIGIL;
            case NONE -> null;
        };
    }

    public static void registerModItems() {
        OccultSmp.LOGGER.info("Registering Mod Items for " + OccultSmp.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(LEAP_SIGIL);
            entries.add(EMERALD_SIGIL);
            entries.add(ICE_SIGIL);
            entries.add(OCEAN_SIGIL);
            entries.add(STRENGTH_SIGIL);
            entries.add(FIRE_SIGIL);
            entries.add(HASTE_SIGIL);
            entries.add(END_SIGIL);
            entries.add(DRAGON_SIGIL);
        });
    }
}
