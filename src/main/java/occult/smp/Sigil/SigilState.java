
package occult.smp.Sigil;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import occult.smp.OccultSmp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SigilState extends PersistentState {
    private final Map<UUID, SigilType> primarySigils = new HashMap<>();
    private final Map<UUID, SigilType> secondarySigils = new HashMap<>();
    
    // Client-side instance for rendering
    private static SigilState clientInstance = null;

    public SigilState() {
        super();
    }

    /**
     * Get server-side state
     */
    public static SigilState get(World world) {
        MinecraftServer server = world.getServer();
        if (server == null) {
            OccultSmp.LOGGER.error("Cannot get SigilState - server is null");
            return new SigilState();
        }
        
        return server.getOverworld().getPersistentStateManager()
            .getOrCreate(
                new Type<>(
                    SigilState::new,
                    SigilState::createFromNbt,
                    null
                ),
                "occult_sigils"
            );
    }

    /**
     * Get client-side state (for rendering only)
     */
    public static SigilState getClientInstance() {
        if (clientInstance == null) {
            clientInstance = new SigilState();
        }
        return clientInstance;
    }

    /**
     * Create from NBT data
     */
    public static SigilState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        SigilState state = new SigilState();
        
        // Load primary sigils
        NbtCompound primaryNbt = nbt.getCompound("PrimarySigils");
        for (String key : primaryNbt.getKeys()) {
            UUID uuid = UUID.fromString(key);
            SigilType type = SigilType.valueOf(primaryNbt.getString(key));
            state.primarySigils.put(uuid, type);
        }
        
        // Load secondary sigils
        NbtCompound secondaryNbt = nbt.getCompound("SecondarySigils");
        for (String key : secondaryNbt.getKeys()) {
            UUID uuid = UUID.fromString(key);
            SigilType type = SigilType.valueOf(secondaryNbt.getString(key));
            state.secondarySigils.put(uuid, type);
        }
        
        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        // Save primary sigils
        NbtCompound primaryNbt = new NbtCompound();
        for (Map.Entry<UUID, SigilType> entry : primarySigils.entrySet()) {
            primaryNbt.putString(entry.getKey().toString(), entry.getValue().name());
        }
        nbt.put("PrimarySigils", primaryNbt);
        
        // Save secondary sigils
        NbtCompound secondaryNbt = new NbtCompound();
        for (Map.Entry<UUID, SigilType> entry : secondarySigils.entrySet()) {
            secondaryNbt.putString(entry.getKey().toString(), entry.getValue().name());
        }
        nbt.put("SecondarySigils", secondaryNbt);
        
        return nbt;
    }

    // Primary Sigil Methods
    public SigilType getPrimarySigil(UUID playerUuid) {
        return primarySigils.getOrDefault(playerUuid, SigilType.NONE);
    }

    public void setPrimarySigil(UUID playerUuid, SigilType type) {
        if (type == null || type == SigilType.NONE) {
            primarySigils.remove(playerUuid);
        } else {
            primarySigils.put(playerUuid, type);
        }
        markDirty();
    }

    public void clearPrimarySigil(UUID playerUuid) {
        primarySigils.remove(playerUuid);
        markDirty();
    }

    // Secondary Sigil Methods
    public SigilType getSecondarySigil(UUID playerUuid) {
        return secondarySigils.getOrDefault(playerUuid, SigilType.NONE);
    }

    public void setSecondarySigil(UUID playerUuid, SigilType type) {
        if (type == null || type == SigilType.NONE) {
            secondarySigils.remove(playerUuid);
        } else {
            secondarySigils.put(playerUuid, type);
        }
        markDirty();
    }

    public void clearSecondarySigil(UUID playerUuid) {
        secondarySigils.remove(playerUuid);
        markDirty();
    }

    // Utility Methods
    public void clearAllSigils(UUID playerUuid) {
        primarySigils.remove(playerUuid);
        secondarySigils.remove(playerUuid);
        markDirty();
    }

    public boolean hasPrimarySigil(UUID playerUuid) {
        SigilType type = primarySigils.get(playerUuid);
        return type != null && type != SigilType.NONE;
    }

    public boolean hasSecondarySigil(UUID playerUuid) {
        SigilType type = secondarySigils.get(playerUuid);
        return type != null && type != SigilType.NONE;
    }
}
