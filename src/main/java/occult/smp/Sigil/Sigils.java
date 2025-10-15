
package occult.smp.Sigil;

import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages active sigils for players
 */
public class Sigils {
    private static final Map<UUID, PlayerSigils> ACTIVE_SIGILS = new HashMap<>();
    
    public static class PlayerSigils {
        private SigilType primary = SigilType.NONE;
        private SigilType secondary = SigilType.NONE;
        private int primaryCooldown = 0;
        private int secondaryCooldown = 0;
        
        public SigilType getPrimary() { return primary; }
        public void setPrimary(SigilType type) { this.primary = type; }
        
        public SigilType getSecondary() { return secondary; }
        public void setSecondary(SigilType type) { this.secondary = type; }
        
        public int getPrimaryCooldown() { return primaryCooldown; }
        public void setPrimaryCooldown(int cooldown) { this.primaryCooldown = cooldown; }
        
        public int getSecondaryCooldown() { return secondaryCooldown; }
        public void setSecondaryCooldown(int cooldown) { this.secondaryCooldown = cooldown; }
        
        public void clear() {
            primary = SigilType.NONE;
            secondary = SigilType.NONE;
            primaryCooldown = 0;
            secondaryCooldown = 0;
        }
    }
    
    public static PlayerSigils get(ServerPlayerEntity player) {
        return ACTIVE_SIGILS.computeIfAbsent(player.getUuid(), k -> new PlayerSigils());
    }
    
    public static void clearActive(ServerPlayerEntity player) {
        PlayerSigils sigils = ACTIVE_SIGILS.get(player.getUuid());
        if (sigils != null) {
            sigils.clear();
        }
    }
    
    public static void setActive(ServerPlayerEntity player, AbilitySlot slot, SigilType type) {
        PlayerSigils sigils = get(player);
        if (slot == AbilitySlot.PRIMARY) {
            sigils.setPrimary(type);
        } else {
            sigils.setSecondary(type);
        }
    }
    
    public static SigilType getActive(ServerPlayerEntity player, AbilitySlot slot) {
        PlayerSigils sigils = get(player);
        return slot == AbilitySlot.PRIMARY ? sigils.getPrimary() : sigils.getSecondary();
    }
    
    public static void setCooldown(ServerPlayerEntity player, AbilitySlot slot, int cooldown) {
        PlayerSigils sigils = get(player);
        if (slot == AbilitySlot.PRIMARY) {
            sigils.setPrimaryCooldown(cooldown);
        } else {
            sigils.setSecondaryCooldown(cooldown);
        }
    }
    
    public static int getCooldown(ServerPlayerEntity player, AbilitySlot slot) {
        PlayerSigils sigils = get(player);
        return slot == AbilitySlot.PRIMARY ? sigils.getPrimaryCooldown() : sigils.getSecondaryCooldown();
    }
}
