package occult.smp;

import occult.smp.Sigil.SigilType;

public final class ClientSigilState {
    private static SigilType active = SigilType.NONE;

    private ClientSigilState() {}

    public static SigilType getActive() { return active; }
    public static void setActive(SigilType sigil) { active = sigil; }
}
