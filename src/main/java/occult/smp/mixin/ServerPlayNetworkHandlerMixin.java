
package occult.smp.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import occult.smp.Network.ModNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;
    
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(MinecraftServer server, ClientConnection connection, ServerPlayerEntity player, 
                       ConnectedClientData clientData, CallbackInfo ci) {
        // Sync player data when they connect
        ModNetworking.syncPlayerData(player);
    }
}
