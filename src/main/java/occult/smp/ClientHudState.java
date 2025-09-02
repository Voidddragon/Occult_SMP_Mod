package occult.smp;



public final class ClientHudState {
    private static HudVisibility hud = HudVisibility.DEFAULT;
    private static KeybindSettings keybinds = KeybindSettings.DEFAULT;

    public static void setHud(HudVisibility h) { hud = h; }
    public static void setKeybinds(KeybindSettings k) { keybinds = k; }

    public static HudVisibility getHud() { return hud; }
    public static KeybindSettings getKeybinds() { return keybinds; }
}
