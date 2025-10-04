
package occult.smp.Sigil;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import occult.smp.OccultSmp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SigilState extends PersistentState {
    private final Map<UUID, SigilType> primarySigils = new HashMap<>();
    private final Map<UUID, SigilType> secondarySigils = new HashMap<>();
    private final Map<UUID, Long> primaryCooldowns = new HashMap<>();
    private final Map<UUID, Long> secondaryCooldowns = new HashMap<>();

    private static final String PRIMARY_SIGILS_KEY = "PrimarySigils";
    private static final String SECONDARY_SIGILS_KEY = "SecondarySigils";
    private static final String PRIMARY_COOLDOWNS_KEY = "PrimaryCooldowns";
    private static final String SECONDARY_COOLDOWNS_KEY = "SecondaryCooldowns";

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
                    SigilState::fromNbt,
                    null
                ),
                OccultSmp.MOD_ID + "_sigils"
            );
    }

    public static SigilState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        SigilState state = new SigilState();
        
        // Load primary sigils
        NbtCompound primaryNbt = nbt.getCompound(PRIMARY_SIGILS_KEY);
        for (String key : primaryNbt.getKeys()) {
            UUID uuid = UUID.fromString(key);
            SigilType type = SigilType.fromString(primaryNbt.getString(key));
            state.primarySigils.put(uuid, type);
        }
        
        // Load secondary sigils
        NbtCompound secondaryNbt = nbt.getCompound(SECONDARY_SIGILS_KEY);
        for (String key : secondaryNbt.getKeys()) {
            UUID uuid = UUID.fromString(key);
            SigilType type = SigilType.fromString(secondaryNbt.getString(key));
            state.secondarySigils.put(uuid, type);
        }
        
        // Load primary cooldowns
        NbtCompound primaryCooldownsNbt = nbt.getCompound(PRIMARY_COOLDOWNS_KEY);
        for (String key : primaryCooldownsNbt.getKeys()) {
            UUID uuid = UUID.fromString(key);
            long cooldown = primaryCooldownsNbt.getLong(key);
            state.primaryCooldowns.put(uuid, cooldown);
        }
        
        // Load secondary cooldowns
        NbtCompound secondaryCooldownsNbt = nbt.getCompound(SECONDARY_COOLDOWNS_KEY);
        for (String key : secondaryCooldownsNbt.getKeys()) {
            UUID uuid = UUID.fromString(key);
            long cooldown = secondaryCooldownsNbt.getLong(key);
            state.secondaryCooldowns.put(uuid, cooldown);
        }
        
        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        // Save primary sigils
        NbtCompound primaryNbt = new NbtCompound();
        primarySigils.forEach((uuid, type) -> 
            primaryNbt.putString(uuid.toString(), type.asString())
        );
        nbt.put(PRIMARY_SIGILS_KEY, primaryNbt);
        
        // Save secondary sigils
        NbtCompound secondaryNbt = new NbtCompound();
        secondarySigils.forEach((uuid, type) -> 
            secondaryNbt.putString(uuid.toString(), type.asString())
        );
        nbt.put(SECONDARY_SIGILS_KEY, secondaryNbt);
        
        // Save primary cooldowns
        NbtCompound primaryCooldownsNbt = new NbtCompound();
        primaryCooldowns.forEach((uuid, cooldown) -> 
            primaryCooldownsNbt.putLong(uuid.toString(), cooldown)
        );
        nbt.put(PRIMARY_COOLDOWNS_KEY, primaryCooldownsNbt);
        
        // Save secondary cooldowns
        NbtCompound secondaryCooldownsNbt = new NbtCompound();
        secondaryCooldowns.forEach((uuid, cooldown) -> 
            secondaryCooldownsNbt.putLong(uuid.toString(), cooldown)
        );
        nbt.put(SECONDARY_COOLDOWNS_KEY, secondaryCooldownsNbt);
        
        return nbt;
    }

    // Primary Sigil Methods
    public void setPrimarySigil(ServerPlayerEntity player, SigilType type) {
        primarySigils.put(player.getUuid(), type);
        markDirty();
    }

    public SigilType getPrimarySigil(ServerPlayerEntity player) {
        return primarySigils.getOrDefault(player.getUuid(), SigilType.NONE);
    }

    // Secondary Sigil Methods
    public void setSecondarySigil(ServerPlayerEntity player, SigilType type) {
        secondarySigils.put(player.getUuid(), type);
        markDirty();
    }

    public SigilType getSecondarySigil(ServerPlayerEntity player) {
        return secondarySigils.getOrDefault(player.getUuid(), SigilType.NONE);
    }

    // Cooldown Methods
    public void setPrimaryCooldown(UUID playerId, long cooldown) {
        primaryCooldowns.put(playerId, cooldown);
        markDirty();
    }

    public long getPrimaryCooldown(UUID playerId) {
        return primaryCooldowns.getOrDefault(playerId, 0L);
    }

    public void setSecondaryCooldown(UUID playerId, long cooldown) {
        secondaryCooldowns.put(playerId, cooldown);
        markDirty();
    }

    public long getSecondaryCooldown(UUID playerId) {
        return secondaryCooldowns.getOrDefault(playerId, 0L);
    }

    public void tickCooldowns() {
        long currentTime = System.currentTimeMillis();
        
        primaryCooldowns.entrySet().removeIf(entry -> entry.getValue() <= currentTime);
        secondaryCooldowns.entrySet().removeIf(entry -> entry.getValue() <= currentTime);
        
        if (!primaryCooldowns.isEmpty() || !secondaryCooldowns.isEmpty()) {
            markDirty();
        }
    }

    public void clearPlayer(UUID playerId) {
        primarySigils.remove(playerId);
        secondarySigils.remove(playerId);
        primaryCooldowns.remove(playerId);
        secondaryCooldowns.remove(playerId);
        markDirty();
    }
}
