
package occult.smp.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.config.GuiConfig;
import occult.smp.config.KeybindConfig;
import occult.smp.config.PlayerConfigData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityConfigMixin implements PlayerConfigData {
    @Unique
    private KeybindConfig keybindConfig = new KeybindConfig();
    
    @Unique
    private GuiConfig guiConfig = new GuiConfig();
    
    @Override
    public KeybindConfig occult$getKeybindConfig() {
        return keybindConfig;
    }
    
    @Override
    public void occult$setKeybindConfig(KeybindConfig config) {
        this.keybindConfig = config;
    }
    
    @Override
    public GuiConfig occult$getGuiConfig() {
        return guiConfig;
    }
    
    @Override
    public void occult$setGuiConfig(GuiConfig config) {
        this.guiConfig = config;
    }
    
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeConfigData(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound keybindNbt = new NbtCompound();
        keybindConfig.writeNbt(keybindNbt);
        nbt.put("occult_keybinds", keybindNbt);
        
        NbtCompound guiNbt = new NbtCompound();
        guiConfig.writeNbt(guiNbt);
        nbt.put("occult_gui", guiNbt);
    }
    
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readConfigData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("occult_keybinds")) {
            keybindConfig.readNbt(nbt.getCompound("occult_keybinds"));
        }
        
        if (nbt.contains("occult_gui")) {
            guiConfig.readNbt(nbt.getCompound("occult_gui"));
        }
    }
}
