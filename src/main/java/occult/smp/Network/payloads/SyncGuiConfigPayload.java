
package occult.smp.Network.payloads;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

/**
 * Server -> Client: Sync GUI configuration
 */
public record SyncGuiConfigPayload(
    float scale,
    int xPosition,
    int yPosition
) implements OccultPayload {
    
    public static final Id<SyncGuiConfigPayload> ID = 
        new Id<>(Identifier.of(OccultSmp.MOD_ID, "sync_gui_config"));
    
    public static final PacketCodec<RegistryByteBuf, SyncGuiConfigPayload> CODEC = 
        PacketCodec.of(SyncGuiConfigPayload::write, SyncGuiConfigPayload::read);
    
    private static SyncGuiConfigPayload read(RegistryByteBuf buf) {
        return new SyncGuiConfigPayload(
            buf.readFloat(),
            buf.readInt(),
            buf.readInt()
        );
    }
    
    private static void write(RegistryByteBuf buf, SyncGuiConfigPayload payload) {
        buf.writeFloat(payload.scale);
        buf.writeInt(payload.xPosition);
        buf.writeInt(payload.yPosition);
    }
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
