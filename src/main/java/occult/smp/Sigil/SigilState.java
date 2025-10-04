
package occult.smp.Sigil;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


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

        return nbt;
    }

    public static SigilState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        SigilState state = new SigilState();

        if (nbt.contains("PrimarySigils")) {
            NbtCompound primaryNbt = nbt.getCompound("PrimarySigils");
            for (String key : primaryNbt.getKeys()) {
                UUID uuid = UUID.fromString(key);
                SigilType type = SigilType.valueOf(primaryNbt.getString(key));
                state.primarySigils.put(uuid, type);
            }
        }

        if (nbt.contains("SecondarySigils")) {
            NbtCompound secondaryNbt = nbt.getCompound("SecondarySigils");
            for (String key : secondaryNbt.getKeys()) {
                UUID uuid = UUID.fromString(key);
                SigilType type = SigilType.valueOf(secondaryNbt.getString(key));
                state.secondarySigils.put(uuid, type);
            }
        }

        return state;
    }

    public static SigilState get(World world) {
        MinecraftServer server = world.getServer();
        if (server == null) {
            throw new IllegalStateException("Cannot get SigilState on client side");
        }

        return server.getOverworld()
                .getPersistentStateManager()
                .getOrCreate(
                        new Type<>(SigilState::new, SigilState::createFromNbt, null),
                        "occult_sigils"
                );
    }

    // Primary Sigil Methods
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

    // Secondary Sigil Methods
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

    // Helper Methods (replaces Sigils.java functionality)
    public void clearSigils(UUID playerUuid) {
        primarySigils.remove(playerUuid);
        secondarySigils.remove(playerUuid);
        markDirty();
    }

    public boolean hasSigil(UUID playerUuid, SigilType type) {
        return getPrimarySigil(playerUuid) == type || getSecondarySigil(playerUuid) == type;
    }

    public boolean hasAnySigil(UUID playerUuid) {
        return getPrimarySigil(playerUuid) != SigilType.NONE ||
                getSecondarySigil(playerUuid) != SigilType.NONE;
    }
}