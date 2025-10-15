
package occult.smp.Sigil.Gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import occult.smp.ClientHudState;
import occult.smp.OccultSmp;
import occult.smp.Sigil.SigilType;

public class SigilGUI implements HudRenderCallback {
    private static final Identifier SIGIL_TEXTURE = Identifier.of(OccultSmp.MOD_ID, "textures/gui/sigil_hud.png");
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    
    private float scale = 1.0f;
    private int xPosition = 10;
    private int yPosition = 10;
    
    public static void register() {
        HudRenderCallback.EVENT.register(new SigilGUI());
    }
    
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) {
            return;
        }
        
        renderSigilHud(drawContext);
    }
    
    private void renderSigilHud(DrawContext context) {
        SigilType primary = ClientHudState.getPrimarySigil();
        SigilType secondary = ClientHudState.getSecondarySigil();
        
        if (primary == SigilType.NONE && secondary == SigilType.NONE) {
            return;
        }
        
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        
        int scaledX = (int) (xPosition * scale);
        int scaledY = (int) (yPosition * scale);
        int size = (int) (32 * scale);
        
        // Render primary sigil
        if (primary != SigilType.NONE) {
            renderSigil(context, primary, scaledX, scaledY, size, ClientHudState.getPrimaryCooldown());
        }
        
        // Render secondary sigil
        if (secondary != SigilType.NONE) {
            renderSigil(context, secondary, scaledX + size + 5, scaledY, size, ClientHudState.getSecondaryCooldown());
        }
        
        RenderSystem.disableBlend();
    }
    
    private void renderSigil(DrawContext context, SigilType sigil, int x, int y, int size, int cooldown) {
        // Render sigil icon
        context.drawTexture(SIGIL_TEXTURE, x, y, 0, 0, size, size, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        
        // Render cooldown overlay if active
        if (cooldown > 0) {
            float cooldownPercent = cooldown / 100.0f; // Assuming max 100 ticks
            int overlayHeight = (int) (size * cooldownPercent);
            context.fill(x, y, x + size, y + overlayHeight, 0x80000000);
        }
    }
    
    public void setScale(float scale) {
        this.scale = scale;
    }
    
    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }
    
    public float getScale() {
        return scale;
    }
    
    public int getXPosition() {
        return xPosition;
    }
    
    public int getYPosition() {
        return yPosition;
    }
}
