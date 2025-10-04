
package occult.smp.Network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public record UpdateKeybindConfigPayload(
    int dropSigilsKey,
    int primaryAbilityKey,
    int secondaryAbilityKey
) implements CustomPayload {
    
    public static final CustomPayload.Id<UpdateKeybindConfigPayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "update_keybind_config"));
    
    public static final PacketCodec<RegistryByteBuf, UpdateKeybindConfigPayload> CODEC = PacketCodec.tuple(
        PacketCodecs.INTEGER, UpdateKeybindConfigPayload::dropSigilsKey,
        PacketCodecs.INTEGER, UpdateKeybindConfigPayload::primaryAbilityKey,
        PacketCodecs.INTEGER, UpdateKeybindConfigPayload::secondaryAbilityKey,
        UpdateKeybindConfigPayload::new
    );
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
