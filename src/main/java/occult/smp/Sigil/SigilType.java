package occult.smp.Sigil;

public enum SigilType {
   LEAP,
    ICE,
    OCEAN,
    EMERALD,
    NONE;

    public static SigilType fromName(String name) {
        for (SigilType type : SigilType.values()) {
            if (type.name().equals(name)) return type;
        }
        return SigilType.NONE;
    }
}
