
package occult.smp.config;

import net.minecraft.nbt.NbtCompound;

public class GuiConfig {
    private float scale;
    private int xPosition;
    private int yPosition;
    
    public static final float DEFAULT_SCALE = 1.0f;
    public static final int DEFAULT_X = 10;
    public static final int DEFAULT_Y = 10;
    
    public GuiConfig() {
        this.scale = DEFAULT_SCALE;
        this.xPosition = DEFAULT_X;
        this.yPosition = DEFAULT_Y;
    }
    
    public float getScale() {
        return scale;
    }
    
    public void setScale(float scale) {
        this.scale = Math.max(0.5f, Math.min(2.0f, scale)); // Clamp between 0.5 and 2.0
    }
    
    public int getXPosition() {
        return xPosition;
    }
    
    public void setXPosition(int x) {
        this.xPosition = x;
    }
    
    public int getYPosition() {
        return yPosition;
    }
    
    public void setYPosition(int y) {
        this.yPosition = y;
    }
    
    public void writeNbt(NbtCompound nbt) {
        nbt.putFloat("scale", scale);
        nbt.putInt("xPosition", xPosition);
        nbt.putInt("yPosition", yPosition);
    }
    
    public void readNbt(NbtCompound nbt) {
        this.scale = nbt.contains("scale") ? nbt.getFloat("scale") : DEFAULT_SCALE;
        this.xPosition = nbt.contains("xPosition") ? nbt.getInt("xPosition") : DEFAULT_X;
        this.yPosition = nbt.contains("yPosition") ? nbt.getInt("yPosition") : DEFAULT_Y;
    }
    
    public GuiConfig copy() {
        GuiConfig copy = new GuiConfig();
        copy.scale = this.scale;
        copy.xPosition = this.xPosition;
        copy.yPosition = this.yPosition;
        return copy;
    }
}
