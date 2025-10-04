package occult.smp.Sigil;

public enum SigilType {
    NONE("none"),
    FIRE("fire"),
    WATER("water"),
    EARTH("earth"),
    AIR("air");
    
    private final String name;
    
    SigilType(String name) {
        this.name = name;
    }
    
    public String asString() {
        return name;
    }
    
    public static SigilType fromString(String name) {
        for (SigilType type : values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return NONE;
    }
    
    public boolean isNone() {
        return this == NONE;
    }
}