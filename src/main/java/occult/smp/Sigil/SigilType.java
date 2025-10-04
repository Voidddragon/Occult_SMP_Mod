
package occult.smp.Sigil;

import net.minecraft.util.StringIdentifiable;

public enum SigilType implements StringIdentifiable {
    NONE("none"),
    FIRE("fire"),
    WATER("water"),
    EARTH("earth"),
    AIR("air");

    private final String name;

    SigilType(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }

    public static SigilType fromString(String name) {
        for (SigilType type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return NONE;
    }
}
