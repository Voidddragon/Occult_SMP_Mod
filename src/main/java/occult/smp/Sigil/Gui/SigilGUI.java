
package occult.smp.Sigil.Gui;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import occult.smp.ClientHudState;
import occult.smp.OccultSmp;
import occult.smp.Sigil.SigilType;

public class SigilGUI implements HudRenderCallback {
    private static final int SIGIL_SIZE = 32;
    private static final int SPACING = 4;
    
    // Texture identifiers for each sigil
    private static final Identifier LEAP_TEXTURE = Identifier.of(OccultSmp.MOD_ID, "textures/item/leap_sigil.png");
    private static final Identifier EMERALD_TEXTURE = Identifier.of(OccultSmp.MOD_ID, "textures/item/emerald_sigil.png");
    private static final Identifier ICE_TEXTURE = Identifier.of(OccultSmp.MOD_ID, "textures/item/ice_sigil.png");
    private static final Identifier OCEAN_TEXTURE = Identifier.of(OccultSmp.MOD_ID, "textures/item/ocean_sigil.png");
    private static final Identifier STRENGTH_TEXTURE = Identifier.of(OccultSmp.MOD_ID, "textures/item/strength_sigil.png");
    private static final Identifier FIRE_TEXTURE = Identifier.of(OccultSmp.MOD_ID, "textures/item/fire_sigil.png");
    private static final Identifier HASTE_TEXTURE = Identifier.of(OccultSmp.MOD_ID, "textures/item/haste_sigil.png");
    private static final Identifier END_TEXTURE = Identifier.of(OccultSmp.MOD_ID, "textures/item/end_sigil.png");
    private static final Identifier DRAGON_TEXTURE = Identifier.of(OccultSmp.MOD_ID, "textures/item/dragon_sigil.png");

    public static void registerHudOverlay() {
        HudRenderCallback.EVENT.register(new SigilGUI());
        System.out.println("[Occult Debug] SigilGUI registered");
        
        // Load HUD config
        OccultHudConfig.load();
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) {
            return;
        }

        SigilType primary = ClientHudState.getPrimarySigil();
        SigilType secondary = ClientHudState.getSecondarySigil();

        // Debug: Log what we're trying to render (less frequently to avoid spam)
        if (client.world != null && client.world.getTime() % 100 == 0) { // Log every 5 seconds
            System.out.println("[SigilGUI Debug] Rendering - Primary: " + primary + ", Secondary: " + secondary);
        }

        // Apply HUD config scale and offset
        float scale = OccultHudConfig.hudScale;
        int offsetY = OccultHudConfig.hudOffsetY;
        int offsetX = OccultHudConfig.hudOffsetX;
        
        drawContext.getMatrices().push();
        drawContext.getMatrices().scale(scale, scale, 1.0f);
        
        // Get screen dimensions
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        
        // Calculate total width of both sigils
        int totalWidth = (SIGIL_SIZE * 2) + SPACING;
        
        // Position at bottom center of screen
        // X: Center horizontally, adjusted for scale
        int x = (int)(((screenWidth / 2) - (totalWidth / 2) + offsetX) / scale);
        // Y: Position from bottom, adjusted for scale
        int y = (int)((screenHeight - SIGIL_SIZE + offsetY) / scale);

        // Render secondary sigil (left slot)
        if (secondary != SigilType.NONE) {
            renderSigilSlot(drawContext, x, y, secondary);
        }

        // Render primary sigil (right slot)
        if (primary != SigilType.NONE) {
            renderSigilSlot(drawContext, x + SIGIL_SIZE + SPACING, y, primary);
        }
        
        drawContext.getMatrices().pop();
    }

    private void renderSigilSlot(DrawContext context, int x, int y, SigilType sigil) {
        // Get the texture for this sigil
        Identifier texture = getSigilTexture(sigil);
        
        if (texture != null) {
            // Draw the sigil texture
            context.drawTexture(texture, x, y, 0, 0, SIGIL_SIZE, SIGIL_SIZE, SIGIL_SIZE, SIGIL_SIZE);
        }
    }

    private Identifier getSigilTexture(SigilType sigil) {
        return switch (sigil) {
            case LEAP -> LEAP_TEXTURE;
            case EMERALD -> EMERALD_TEXTURE;
            case ICE -> ICE_TEXTURE;
            case OCEAN -> OCEAN_TEXTURE;
            case STRENGTH -> STRENGTH_TEXTURE;
            case FIRE -> FIRE_TEXTURE;
            case HASTE -> HASTE_TEXTURE;
            case END -> END_TEXTURE;
            case DRAGON -> DRAGON_TEXTURE;
            default -> null;
        };
    }
}
