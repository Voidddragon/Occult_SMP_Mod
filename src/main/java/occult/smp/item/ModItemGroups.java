package occult.smp.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public class ModItemGroups {
    public static final ItemGroup OCCULT_SMP_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(OccultSmp.MOD_ID, "occult_smp_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.LEAP_SIGIL))
                    .displayName(Text.translatable("itemgroup.occultsmp.occult_smp_items"))
                    .entries((displayContext, entries) ->{
                        entries.add(ModItems.LEAP_SIGIL);
                        entries.add(ModItems.EMERALD_SIGIL);
                        entries.add(ModItems.ICE_SIGIL);
                        entries.add(ModItems.OCEAN_SIGIL);
                    } )
                    .build());


    public static void registerItemGroups() {
        OccultSmp.LOGGER.info("Registering Item Groups for " + OccultSmp.MOD_ID);
    }
}