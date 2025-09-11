package occult.smp.Sigil.Gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import occult.smp.Sigil.AbilitySlot.Ability;
import occult.smp.Sigil.AbilitySlot.AbilityRegistry;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;
import occult.smp.Sigil.SigilType;
import occult.smp.Sigil.ClientSigilState;


public final class SigilGUI implements HudRenderCallback {

    private static final int BASE_SLOT_SIZE = 40;
    private static final int PADDING = 6;
    private static final int BORDER = 2;
    private static final int BORDER_COLOR = 0xFF000000;
    private static final String ICON_PATH_FORMAT = "textures/gui/%s.png";
    private static final String MOD_ID = "occult_smp";

    public static void register() {
        HudRenderCallback.EVENT.register(new SigilGUI());
    }

    @Override
    public void onHudRender(DrawContext ctx, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options.hudHidden || mc.player == null || mc.world == null) return;

        SigilType sigil = ClientSigilState.getActive();
        if (sigil == null) sigil = SigilType.NONE;

        Ability primary = AbilityRegistry.get(sigil, AbilitySlot.PRIMARY);
        Ability secondary = AbilityRegistry.get(sigil, AbilitySlot.SECONDARY);

        int scaledSize = (int) (BASE_SLOT_SIZE * OccultHudConfig.hudScale);
        int totalWidth = scaledSize * 2 + PADDING;
        int sw = ctx.getScaledWindowWidth();
        int sh = ctx.getScaledWindowHeight();
        int x = (sw - totalWidth) / 2;
        int y = sh + OccultHudConfig.hudOffsetY;

        drawSlot(ctx, x, y, primary != null ? primary.icon() : null, scaledSize, sigil, AbilitySlot.PRIMARY);
        x += scaledSize + PADDING;
        drawSlot(ctx, x, y, secondary != null ? secondary.icon() : null, scaledSize, sigil, AbilitySlot.SECONDARY);
    }

    private void drawSlot(DrawContext ctx, int x, int y, String iconName, int size, SigilType sigilType, AbilitySlot slot) {
        fillBorder2px(ctx, x, y, size, size, BORDER_COLOR);

        if (iconName == null || iconName.isEmpty()) return;

        Identifier tex = getIconIdentifier(iconName);
        boolean exists = MinecraftClient.getInstance()
                .getResourceManager()
                .getResource(tex)
                .isPresent();
        if (!exists) {
            System.out.println("[DEBUG] Missing sigil icon texture: " + tex +
                    " | Expected file: assets/" + tex.getNamespace() + "/textures/" + tex.getPath());
            return;
        }

        int insetX = x + BORDER;
        int insetY = y + BORDER;
        int insetSize = size - 2 * BORDER;

        RenderSystem.enableBlend();
        ctx.drawTexture(tex, insetX, insetY, 0, 0, insetSize, insetSize, insetSize, insetSize);
        RenderSystem.disableBlend();

        // Per-slot cooldown overlay
        int cooldown = ClientSigilState.getCooldown(sigilType, slot);
        int maxCooldown = ClientSigilState.getMaxCooldown(sigilType, slot);
        if (cooldown > 0 && maxCooldown > 0) {
            float progress = cooldown / (float) maxCooldown;
            int overlayHeight = (int) (insetSize * progress);
            ctx.fill(insetX, insetY + (insetSize - overlayHeight),
                    insetX + insetSize, insetY + insetSize,
                    0x88000000); // semi-transparent black
        }
    }

    private static Identifier getIconIdentifier(String iconName) {
        return Identifier.of(MOD_ID, String.format(ICON_PATH_FORMAT, iconName));
    }

    private static void fillBorder2px(DrawContext ctx, int x, int y, int w, int h, int color) {
        int b = BORDER;
        ctx.fill(x, y, x + w, y + b, color);
        ctx.fill(x, y + h - b, x + w, y + h, color);
        ctx.fill(x, y + b, x + b, y + h - b, color);
        ctx.fill(x + w - b, y + b, x + w, y + h - b, color);
    }
}
