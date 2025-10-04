
package occult.smp.Sigil.AbilitySlot;

import occult.smp.OccultSmp;
import occult.smp.Sigil.SigilType;
import occult.smp.Sigil.abilities.*;

public class AbilityInitializer {
    public static void registerAbilities() {
        OccultSmp.LOGGER.info("Registering Sigil Abilities");
        
        // Register one ability per sigil type
        AbilityRegistry.register(SigilType.LEAP, new LeapAbility());
        AbilityRegistry.register(SigilType.EMERALD, new EmeraldAbility());
        AbilityRegistry.register(SigilType.ICE, new IceAbility());
        AbilityRegistry.register(SigilType.OCEAN, new OceanAbility());
        AbilityRegistry.register(SigilType.STRENGTH, new StrengthAbility());
        AbilityRegistry.register(SigilType.FIRE, new FireAbility());
        AbilityRegistry.register(SigilType.HASTE, new HasteAbility());
        AbilityRegistry.register(SigilType.END, new EndAbility());
        AbilityRegistry.register(SigilType.DRAGON, new DragonAbility());
        
        OccultSmp.LOGGER.info("Successfully registered {} abilities", 9);
    }
}
