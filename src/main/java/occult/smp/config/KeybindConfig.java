
package occult.smp.config;

public class KeybindConfig {
    private int dropSigilsKey;
    private int primaryAbilityKey;
    private int secondaryAbilityKey;
    
    public KeybindConfig() {
        // Default keybinds (GLFW key codes)
        this.dropSigilsKey = 82; // R key
        this.primaryAbilityKey = 90; // Z key
        this.secondaryAbilityKey = 88; // X key
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
}
