package occult.smp.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.KeybindSettingsData;
import occult.smp.KeybindSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityKeybindMixin implements KeybindSettingsData {
    @Unique private KeybindSettings occult_smp$keybindSettings = KeybindSettings.DEFAULT;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void occult_smp$writeKeybind(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("occult_keybind_enabled", occult_smp$keybindSettings.enabled());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void occult_smp$readKeybind(NbtCompound nbt, CallbackInfo ci) {
        boolean enabled = nbt.getBoolean("occult_keybind_enabled");
        occult_smp$keybindSettings = new KeybindSettings(enabled);
    }

    @Override public void occult_smp$setKeybindSettings(KeybindSettings settings) {
        this.occult_smp$keybindSettings = settings;
    }

    @Override public KeybindSettings occult_smp$getKeybindSettings() {
        return this.occult_smp$keybindSettings;
    }
}
