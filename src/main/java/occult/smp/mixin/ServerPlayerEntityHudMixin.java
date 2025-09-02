package occult.smp.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.HudVisibilityData;
import occult.smp.HudVisibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityHudMixin implements HudVisibilityData {
    @Unique private HudVisibility occult_smp$hudVisibility = HudVisibility.DEFAULT;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void occult_smp$writeHud(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("occult_hud_visible", occult_smp$hudVisibility.visible());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void occult_smp$readHud(NbtCompound nbt, CallbackInfo ci) {
        boolean visible = nbt.getBoolean("occult_hud_visible");
        occult_smp$hudVisibility = new HudVisibility(visible);
    }

    @Override public void occult_smp$setHudVisibility(HudVisibility visibility) {
        this.occult_smp$hudVisibility = visibility;
    }

    @Override public HudVisibility occult_smp$getHudVisibility() {
        return this.occult_smp$hudVisibility;
    }
}
