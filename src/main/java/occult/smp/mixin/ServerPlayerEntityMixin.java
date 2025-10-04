
package occult.smp.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.config.GuiConfig;
import occult.smp.config.KeybindConfig;
import occult.smp.util.PlayerConfigData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements PlayerConfigData {
    @Unique
    private KeybindConfig occult$keybindConfig = new KeybindConfig();
    
    @Unique
    private GuiConfig occult$guiConfig = new GuiConfig();
    
    @Override
    public KeybindConfig occult$getKeybindConfig() {
        return occult$keybindConfig;
    }
    
    @Override
    public void occult$setKeybindConfig(KeybindConfig config) {
        this.occult$keybindConfig = config;
    }
    
    @Override
    public GuiConfig occult$getGuiConfig() {
        return occult$guiConfig;
    }
    
    @Override
    public void occult$setGuiConfig(GuiConfig config) {
        this.occult$guiConfig = config;
    }
    
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void occult$writeCustomData(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound occultData = new NbtCompound();
        
        // Save keybind config
        NbtCompound keybindNbt = new NbtCompound();
        keybindNbt.putInt("dropSigils", occult$keybindConfig.getDropSigilsKey());
        keybindNbt.putInt("primaryAbility", occult$keybindConfig.getPrimaryAbilityKey());
        keybindNbt.putInt("secondaryAbility", occult$keybindConfig.getSecondaryAbilityKey());
        occultData.put("keybinds", keybindNbt);
        
        // Save GUI config
        NbtCompound guiNbt = new NbtCompound();
        guiNbt.putFloat("scale", occult$guiConfig.getScale());
        guiNbt.putInt("xPos", occult$guiConfig.getXPosition());
        guiNbt.putInt("yPos", occult$guiConfig.getYPosition());
        occultData.put("gui", guiNbt);
        
        nbt.put("occult_smp", occultData);
    }
    
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void occult$readCustomData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("occult_smp")) {
            NbtCompound occultData = nbt.getCompound("occult_smp");
            
            // Load keybind config
            if (occultData.contains("keybinds")) {
                NbtCompound keybindNbt = occultData.getCompound("keybinds");
                occult$keybindConfig.setDropSigilsKey(keybindNbt.getInt("dropSigils"));
                occult$keybindConfig.setPrimaryAbilityKey(keybindNbt.getInt("primaryAbility"));
                occult$keybindConfig.setSecondaryAbilityKey(keybindNbt.getInt("secondaryAbility"));
            }
            
            // Load GUI config
            if (occultData.contains("gui")) {
                NbtCompound guiNbt = occultData.getCompound("gui");
                occult$guiConfig.setScale(guiNbt.getFloat("scale"));
                occult$guiConfig.setXPosition(guiNbt.getInt("xPos"));
                occult$guiConfig.setYPosition(guiNbt.getInt("yPos"));
            }
        }
    }
}
