
package occult.smp.Network.Payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public record SyncGuiConfigPayload(float scale, int xPosition, int yPosition) implements CustomPayload {
    public static final CustomPayload.Id<SyncGuiConfigPayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "sync_gui_config"));
    
    public static final PacketCodec<RegistryByteBuf, SyncGuiConfigPayload> CODEC = PacketCodec.tuple(
        PacketCodecs.FLOAT, SyncGuiConfigPayload::scale,
        PacketCodecs.INTEGER, SyncGuiConfigPayload::xPosition,
        PacketCodecs.INTEGER, SyncGuiConfigPayload::yPosition,
        SyncGuiConfigPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
