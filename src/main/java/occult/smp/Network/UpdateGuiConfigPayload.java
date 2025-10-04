
package occult.smp.Network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public record UpdateGuiConfigPayload(
    float scale,
    int xPosition,
    int yPosition
) implements CustomPayload {
    
    public static final CustomPayload.Id<UpdateGuiConfigPayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "update_gui_config"));
    
    public static final PacketCodec<RegistryByteBuf, UpdateGuiConfigPayload> CODEC = PacketCodec.tuple(
        PacketCodecs.FLOAT, UpdateGuiConfigPayload::scale,
        PacketCodecs.INTEGER, UpdateGuiConfigPayload::xPosition,
        PacketCodecs.INTEGER, UpdateGuiConfigPayload::yPosition,
        UpdateGuiConfigPayload::new
    );
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
