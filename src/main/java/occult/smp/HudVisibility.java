package occult.smp;

public record HudVisibility(boolean visible) {
    public static final HudVisibility DEFAULT = new HudVisibility(true);
}
