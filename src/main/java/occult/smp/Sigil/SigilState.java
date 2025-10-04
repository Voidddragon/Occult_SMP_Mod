
package occult.smp.Sigil;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SigilState extends PersistentState {
    private final Map<UUID, SigilType> primarySigils = new HashMap<>();
    private final Map<UUID, SigilType> secondarySigils = new HashMap<>();
    private final Map<UUID, Long> primaryCooldowns = new HashMap<>();
    private final Map<UUID, Long> secondaryCooldowns = new HashMap<>();

    public SigilState() {
        super();
    }

    public static SigilState get(World world) {
        MinecraftServer server = world.getServer();
        if (server == null) {
            throw new IllegalStateException("Cannot get SigilState on client side");
        }
        
        return server.getOverworld()
            .getPersistentStateManager()
            .getOrCreate(
                new Type<>(
                    SigilState::new,
                    SigilState::createFromNbt,
                    null
                ),
                "occult_sigils"
            );
    }

    public static SigilState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        SigilState state = new SigilState();
        
        NbtCompound primaryNbt = nbt.getCompound("PrimarySigils");
        for (String key : primaryNbt.getKeys()) {
            UUID uuid = UUID.fromString(key);
            SigilType type = SigilType.fromString(primaryNbt.getString(key));
            state.primarySigils.put(uuid, type);
        }
        
        NbtCompound secondaryNbt = nbt.getCompound("SecondarySigils");
        for (String key : secondaryNbt.getKeys()) {
            UUID uuid = UUID.fromString(key);
            SigilType type = SigilType.fromString(secondaryNbt.getString(key));
            state.secondarySigils.put(uuid, type);
        }
        
        NbtCompound primaryCooldownsNbt = nbt.getCompound("PrimaryCooldowns");
        for (String key : primaryCooldownsNbt.getKeys()) {
            state.primaryCooldowns.put(UUID.fromString(key), primaryCooldownsNbt.getLong(key));
        }
        
        NbtCompound secondaryCooldownsNbt = nbt.getCompound("SecondaryCooldowns");
        for (String key : secondaryCooldownsNbt.getKeys()) {
            state.secondaryCooldowns.put(UUID.fromString(key), secondaryCooldownsNbt.getLong(key));
        }
        
        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound primaryNbt = new NbtCompound();
        primarySigils.forEach((uuid, type) -> 
            primaryNbt.putString(uuid.toString(), type.name())
        );
        nbt.put("PrimarySigils", primaryNbt);
        
        NbtCompound secondaryNbt = new NbtCompound();
        secondarySigils.forEach((uuid, type) -> 
            secondaryNbt.putString(uuid.toString(), type.name())
        );
        nbt.put("SecondarySigils", secondaryNbt);
        
        NbtCompound primaryCooldownsNbt = new NbtCompound();
        primaryCooldowns.forEach((uuid, cooldown) -> primaryCooldownsNbt.putLong(uuid.toString(), cooldown));
        nbt.put("PrimaryCooldowns", primaryCooldownsNbt);
        
        NbtCompound secondaryCooldownsNbt = new NbtCompound();
        secondaryCooldowns.forEach((uuid, cooldown) -> secondaryCooldownsNbt.putLong(uuid.toString(), cooldown));
        nbt.put("SecondaryCooldowns", secondaryCooldownsNbt);
        
        return nbt;
    }

    public SigilType getPrimarySigil(UUID playerUuid) {
        return primarySigils.getOrDefault(playerUuid, SigilType.NONE);
    }
    
    public void setPrimarySigil(UUID playerUuid, SigilType type) {
        if (type == SigilType.NONE) {
            primarySigils.remove(playerUuid);
        } else {
            primarySigils.put(playerUuid, type);
        }
        markDirty();
    }
    
    public SigilType getSecondarySigil(UUID playerUuid) {
        return secondarySigils.getOrDefault(playerUuid, SigilType.NONE);
    }
    
    public void setSecondarySigil(UUID playerUuid, SigilType type) {
        if (type == SigilType.NONE) {
            secondarySigils.remove(playerUuid);
        } else {
            secondarySigils.put(playerUuid, type);
        }
        markDirty();
    }

    public void setPrimaryCooldown(UUID playerId, long cooldown) {
        if (cooldown <= System.currentTimeMillis()) {
            primaryCooldowns.remove(playerId);
        } else {
            primaryCooldowns.put(playerId, cooldown);
        }
        markDirty();
    }

    public long getPrimaryCooldown(UUID playerId) {
        return primaryCooldowns.getOrDefault(playerId, 0L);
    }

    public void setSecondaryCooldown(UUID playerId, long cooldown) {
        if (cooldown <= System.currentTimeMillis()) {
            secondaryCooldowns.remove(playerId);
        } else {
            secondaryCooldowns.put(playerId, cooldown);
        }
        markDirty();
    }

    public long getSecondaryCooldown(UUID playerId) {
        return secondaryCooldowns.getOrDefault(playerId, 0L);
    }

    public void clearPlayer(UUID playerId) {
        primarySigils.remove(playerId);
        secondarySigils.remove(playerId);
        primaryCooldowns.remove(playerId);
        secondaryCooldowns.remove(playerId);
        markDirty();
    }
}
