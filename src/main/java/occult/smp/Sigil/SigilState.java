
package occult.smp.Sigil;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import occult.smp.Network.SyncSigilCooldownPayload;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;

import java.util.*;

public class SigilState extends PersistentState {
    private static final PersistentState.Type<SigilState> TYPE =
            new PersistentState.Type<>(
                    SigilState::new,
                    SigilState::fromNbt,
                    (DataFixTypes) null
            );

    // Store a list of sigils per player (max 2)
    private final Map<UUID, List<SigilType>> activeSigils = new HashMap<>();
    
    // Store cooldowns per player per slot
    private final Map<UUID, Map<AbilitySlot, Long>> cooldowns = new HashMap<>();

    public static SigilState get(World world) {
        PersistentStateManager manager = world.getServer().getOverworld().getPersistentStateManager();
        return manager.getOrCreate(TYPE, "occult_sigils");
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        // Save active sigils
        NbtCompound sigilsNbt = new NbtCompound();
        for (Map.Entry<UUID, List<SigilType>> entry : activeSigils.entrySet()) {
            NbtList sigilList = new NbtList();
            for (SigilType sigil : entry.getValue()) {
                NbtCompound sigilNbt = new NbtCompound();
                sigilNbt.putString("type", sigil.name());
                sigilList.add(sigilNbt);
            }
            sigilsNbt.put(entry.getKey().toString(), sigilList);
        }
        nbt.put("sigils", sigilsNbt);

        // Save cooldowns
        NbtCompound cooldownsNbt = new NbtCompound();
        for (Map.Entry<UUID, Map<AbilitySlot, Long>> playerEntry : cooldowns.entrySet()) {
            NbtCompound playerCooldowns = new NbtCompound();
            for (Map.Entry<AbilitySlot, Long> cooldownEntry : playerEntry.getValue().entrySet()) {
                playerCooldowns.putLong(cooldownEntry.getKey().name(), cooldownEntry.getValue());
            }
            cooldownsNbt.put(playerEntry.getKey().toString(), playerCooldowns);
        }
        nbt.put("cooldowns", cooldownsNbt);

        return nbt;
    }

    public static SigilState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        SigilState state = new SigilState();

        // Load active sigils
        NbtCompound sigilsNbt = nbt.getCompound("sigils");
        for (String key : sigilsNbt.getKeys()) {
            UUID playerId = UUID.fromString(key);
            NbtList sigilList = sigilsNbt.getList(key, NbtElement.COMPOUND_TYPE);
            List<SigilType> sigils = new ArrayList<>();
            for (int i = 0; i < sigilList.size(); i++) {
                NbtCompound sigilNbt = sigilList.getCompound(i);
                String typeName = sigilNbt.getString("type");
                try {
                    sigils.add(SigilType.valueOf(typeName));
                } catch (IllegalArgumentException e) {
                    System.err.println("Unknown sigil type: " + typeName);
                }
            }
            state.activeSigils.put(playerId, sigils);
        }

        // Load cooldowns
        NbtCompound cooldownsNbt = nbt.getCompound("cooldowns");
        for (String key : cooldownsNbt.getKeys()) {
            UUID playerId = UUID.fromString(key);
            NbtCompound playerCooldowns = cooldownsNbt.getCompound(key);
            Map<AbilitySlot, Long> cooldownMap = new HashMap<>();
            for (String slotName : playerCooldowns.getKeys()) {
                try {
                    AbilitySlot slot = AbilitySlot.valueOf(slotName);
                    long cooldown = playerCooldowns.getLong(slotName);
                    cooldownMap.put(slot, cooldown);
                } catch (IllegalArgumentException e) {
                    System.err.println("Unknown ability slot: " + slotName);
                }
            }
            state.cooldowns.put(playerId, cooldownMap);
        }

        return state;
    }

    // Add a sigil to a player, returns the removed sigil if any
    public SigilType addSigil(UUID playerId, SigilType sigil) {
        List<SigilType> sigils = activeSigils.computeIfAbsent(playerId, k -> new ArrayList<>());
        
        System.out.println("[SigilState Debug] Before add - Player: " + playerId + ", Sigils: " + sigils + ", Adding: " + sigil);
        
        // Check if player already has this sigil
        if (sigils.contains(sigil)) {
            return SigilType.NONE;
        }
        
        SigilType removed = SigilType.NONE;
        
        // Remove oldest if at max capacity
        if (sigils.size() >= 2) {
            removed = sigils.remove(0);
            System.out.println("[SigilState Debug] Removed oldest sigil: " + removed);
        }
        
        sigils.add(sigil);
        markDirty();
        
        System.out.println("[SigilState Debug] After add - Player: " + playerId + ", Sigils: " + sigils);
        
        return removed;
    }

    // Remove a sigil from a player
    public boolean removeSigil(UUID playerId, SigilType sigil) {
        List<SigilType> sigils = activeSigils.get(playerId);
        if (sigils != null && sigils.remove(sigil)) {
            if (sigils.isEmpty()) {
                activeSigils.remove(playerId);
            }
            markDirty();
            return true;
        }
        return false;
    }

    // Get all sigils for a player
    public List<SigilType> getSigils(UUID playerId) {
        return new ArrayList<>(activeSigils.getOrDefault(playerId, new ArrayList<>()));
    }

    // Get primary (most recent) sigil
    public SigilType getPrimarySigil(UUID playerId) {
        List<SigilType> sigils = activeSigils.get(playerId);
        if (sigils == null || sigils.isEmpty()) return SigilType.NONE;
        return sigils.get(sigils.size() - 1);
    }

    // Get secondary (second most recent) sigil
    public SigilType getSecondarySigil(UUID playerId) {
        List<SigilType> sigils = activeSigils.get(playerId);
        if (sigils == null || sigils.size() < 2) return SigilType.NONE;
        return sigils.get(sigils.size() - 2);
    }

    // Clear all sigils for a player
    public void clearAll(UUID playerId) {
        activeSigils.remove(playerId);
        markDirty();
    }

    // Check if player has a specific sigil
    public boolean hasSigil(UUID playerId, SigilType sigil) {
        List<SigilType> sigils = activeSigils.get(playerId);
        return sigils != null && sigils.contains(sigil);
    }

    // Get cooldown for a player and slot
    public long getCooldown(UUID playerId, AbilitySlot slot) {
        return cooldowns.getOrDefault(playerId, Map.of()).getOrDefault(slot, 0L);
    }

    // Set cooldown for a player and slot
    public void setCooldown(World world, UUID playerId, AbilitySlot slot, long ticks) {
        cooldowns.computeIfAbsent(playerId, k -> new HashMap<>()).put(slot, ticks);
        markDirty();

        ServerPlayerEntity player = world.getServer().getPlayerManager().getPlayer(playerId);
        if (player != null) {
            ServerPlayNetworking.send(player, new SyncSigilCooldownPayload(
                    getPrimarySigil(playerId),
                    slot,
                    (int) ticks
            ));
        }
    }

    // Tick cooldowns
    public void tickCooldowns() {
        cooldowns.forEach((uuid, map) -> map.replaceAll((slot, time) -> Math.max(0, time - 1)));
        markDirty();
    }
}
