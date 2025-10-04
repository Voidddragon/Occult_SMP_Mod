
package occult.smp.config;

import net.minecraft.nbt.NbtCompound;

public class KeybindConfig {
    private int dropSigilsKey;
    private int primaryAbilityKey;
    private int secondaryAbilityKey;
    
    // Default GLFW key codes
    public static final int DEFAULT_DROP_SIGILS = 82; // R key
    public static final int DEFAULT_PRIMARY_ABILITY = 90; // Z key
    public static final int DEFAULT_SECONDARY_ABILITY = 88; // X key
    
    public KeybindConfig() {
        this.dropSigilsKey = DEFAULT_DROP_SIGILS;
        this.primaryAbilityKey = DEFAULT_PRIMARY_ABILITY;
        this.secondaryAbilityKey = DEFAULT_SECONDARY_ABILITY;
    }
    
    public int getDropSigilsKey() {
        return dropSigilsKey;
    }
    
    public void setDropSigilsKey(int key) {
        this.dropSigilsKey = key;
    }
    
    public int getPrimaryAbilityKey() {
        return primaryAbilityKey;
    }
    
    public void setPrimaryAbilityKey(int key) {
        this.primaryAbilityKey = key;
    }
    
    public int getSecondaryAbilityKey() {
        return secondaryAbilityKey;
    }
    
    public void setSecondaryAbilityKey(int key) {
        this.secondaryAbilityKey = key;
    }
    
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("dropSigilsKey", dropSigilsKey);
        nbt.putInt("primaryAbilityKey", primaryAbilityKey);
        nbt.putInt("secondaryAbilityKey", secondaryAbilityKey);
    }
    
    public void readNbt(NbtCompound nbt) {
        this.dropSigilsKey = nbt.contains("dropSigilsKey") ? nbt.getInt("dropSigilsKey") : DEFAULT_DROP_SIGILS;
        this.primaryAbilityKey = nbt.contains("primaryAbilityKey") ? nbt.getInt("primaryAbilityKey") : DEFAULT_PRIMARY_ABILITY;
        this.secondaryAbilityKey = nbt.contains("secondaryAbilityKey") ? nbt.getInt("secondaryAbilityKey") : DEFAULT_SECONDARY_ABILITY;
    }
    
    public KeybindConfig copy() {
        KeybindConfig copy = new KeybindConfig();
        copy.dropSigilsKey = this.dropSigilsKey;
        copy.primaryAbilityKey = this.primaryAbilityKey;
        copy.secondaryAbilityKey = this.secondaryAbilityKey;
        return copy;
    }
}
