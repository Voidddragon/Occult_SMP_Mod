package occult.smp;

public record KeybindSettings(boolean enabled) {
    public static final KeybindSettings DEFAULT = new KeybindSettings(true);
}
