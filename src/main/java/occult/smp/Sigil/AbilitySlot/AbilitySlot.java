
package occult.smp.Sigil.AbilitySlot;

public enum AbilitySlot {
    PRIMARY,
    SECONDARY;
    
    public boolean isPrimary() {
        return this == PRIMARY;
    }
    
    public boolean isSecondary() {
        return this == SECONDARY;
    }
}
