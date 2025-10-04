
package occult.smp.Network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

public record SyncKeybindConfigPayload(
    int dropSigilsKey,
    int primaryAbilityKey,
    int secondaryAbilityKey
) implements CustomPayload {
    
    public static final CustomPayload.Id<SyncKeybindConfigPayload> ID = 
        new CustomPayload.Id<>(Identifier.of(OccultSmp.MOD_ID, "sync_keybind_config"));
    
    public static final PacketCodec<RegistryByteBuf, SyncKeybindConfigPayload> CODEC = PacketCodec.tuple(
        PacketCodecs.INTEGER, SyncKeybindConfigPayload::dropSigilsKey,
        PacketCodecs.INTEGER, SyncKeybindConfigPayload::primaryAbilityKey,
        PacketCodecs.INTEGER, SyncKeybindConfigPayload::secondaryAbilityKey,
        SyncKeybindConfigPayload::new
    );
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
