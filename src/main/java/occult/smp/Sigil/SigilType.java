
package occult.smp.Sigil;

public enum SigilType {
    NONE,
    LEAP,
    EMERALD,
    ICE,
    OCEAN,
    STRENGTH,
    FIRE,
    HASTE,
    END,
    DRAGON;
    
    public static SigilType fromString(String name) {
        if (name == null || name.isEmpty()) {
            return NONE;
        }
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return NONE;
        }
    }
}
