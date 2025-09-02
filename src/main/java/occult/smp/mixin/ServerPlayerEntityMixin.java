package occult.smp.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.SigilData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements SigilData {
    @Unique
    private String occult_smp$equippedSigil = "NONE";

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void occult_smp$writeSigil(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("occult_sigil", occult_smp$equippedSigil);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void occult_smp$readSigil(NbtCompound nbt, CallbackInfo ci) {
        occult_smp$equippedSigil = nbt.getString("occult_sigil");
    }

    @Override
    public void occult_smp$setEquippedSigil(String sigilName) {
        this.occult_smp$equippedSigil = sigilName;
    }

    @Override
    public String occult_smp$getEquippedSigil() {
        return this.occult_smp$equippedSigil;
    }
}
