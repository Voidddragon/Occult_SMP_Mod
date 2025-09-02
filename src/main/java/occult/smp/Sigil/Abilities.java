package occult.smp.Sigil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import occult.smp.Sigil.AbilitySlot.Ability;
import occult.smp.Sigil.AbilitySlot.AbilityContext;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;

public final class Abilities {
        private Abilities() {}

    public static final Ability LEAP = new Ability() {
        @Override
        public boolean activate(AbilityContext ctx) {
            var p = ctx.player();
            var vec = p.getRotationVec(1.0f).multiply(1.0);
            p.addVelocity(vec.x, 0.6, vec.z);
            p.velocityModified = true;
            p.getWorld().playSound(null, p.getBlockPos(), SoundEvents.ENTITY_SLIME_JUMP, SoundCategory.PLAYERS, 0.8f, 1.1f);
            return true;
        }

        @Override
        public int cooldownTicks() {
            return 20 * 6;
        }

        @Override
        public String icon() {
            return "leap";
        }
    };



    public static void registerAll() {
            // Map each sigil to two abilities (examples)
            AbilityRegistry.set(SigilType.LEAP, LEAP,null);
            AbilityRegistry.set(SigilType.ICE, null, null);
            AbilityRegistry.set(SigilType.OCEAN, null, null);
            AbilityRegistry.set(SigilType.EMERALD, null, null);
            AbilityRegistry.set(SigilType.NONE, null, null);
        }
    }


