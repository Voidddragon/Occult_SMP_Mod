
package occult.smp;

import occult.smp.Sigil.SigilType;

public class ClientHudState {
    private static SigilType primarySigil = SigilType.NONE;
    private static SigilType secondarySigil = SigilType.NONE;
    private static HudVisibility hud = HudVisibility.DEFAULT;
    private static KeybindSettings keybinds = KeybindSettings.DEFAULT;

    public static SigilType getPrimarySigil() {
        return primarySigil;
    }
    
    public static void setPrimarySigil(SigilType sigil) {
        primarySigil = sigil;
    }
    
    public static SigilType getSecondarySigil() {
        return secondarySigil;
    }
    
    public static void setSecondarySigil(SigilType sigil) {
        secondarySigil = sigil;
    }
    
    public static void setBothSigils(SigilType primary, SigilType secondary) {
        primarySigil = primary;
        secondarySigil = secondary;
    }
    
    public static void setHud(HudVisibility h) { hud = h; }
    public static void setKeybinds(KeybindSettings k) { keybinds = k; }

    public static HudVisibility getHud() { return hud; }
    public static KeybindSettings getKeybinds() { return keybinds; }
}
