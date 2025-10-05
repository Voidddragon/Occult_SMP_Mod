
package occult.smp.Network.payloads;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import occult.smp.OccultSmp;

/**
 * Server -> Client: Sync keybind configuration
 */
public record SyncKeybindConfigPayload(
    String dropSigilsKey,
    String primaryAbilityKey,
    String secondaryAbilityKey
) implements OccultPayload {
    
    public static final Id<SyncKeybindConfigPayload> ID = 
        new Id<>(Identifier.of(OccultSmp.MOD_ID, "sync_keybind_config"));
    
    public static final PacketCodec<RegistryByteBuf, SyncKeybindConfigPayload> CODEC = 
        PacketCodec.of(SyncKeybindConfigPayload::write, SyncKeybindConfigPayload::read);
    
    private static SyncKeybindConfigPayload read(RegistryByteBuf buf) {
        return new SyncKeybindConfigPayload(
            buf.readString(),
            buf.readString(),
            buf.readString()
        );
    }
    
    private static void write(RegistryByteBuf buf, SyncKeybindConfigPayload payload) {
        buf.writeString(payload.dropSigilsKey);
        buf.writeString(payload.primaryAbilityKey);
        buf.writeString(payload.secondaryAbilityKey);
    }
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
