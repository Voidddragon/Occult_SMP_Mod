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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SigilState extends PersistentState {
    private static final String KEY = "occult_sigil_state";

    private static final PersistentState.Type<SigilState> TYPE =
            new PersistentState.Type<>(
                    SigilState::new,
                    SigilState::fromNbt,
                    (DataFixTypes) null
            );

    private final Map<UUID, SigilType> active = new HashMap<>();
    private final Map<UUID, Map<AbilitySlot, Integer>> cooldowns = new HashMap<>();
    private final Map<UUID, Map<AbilitySlot, Integer>> maxCooldowns = new HashMap<>();

    public static SigilState get(World world) {
        PersistentStateManager psm = Objects.requireNonNull(world.getServer())
                .getOverworld().getPersistentStateManager();
        return psm.getOrCreate(TYPE, KEY);
    }

    public SigilType get(UUID uuid) {
        return active.getOrDefault(uuid, SigilType.NONE);
    }

    public void set(UUID uuid, SigilType type) {
        SigilType cur = active.get(uuid);
        if (cur != type) {
            active.put(uuid, type);
            markDirty();
        }
    }

    public void clear(UUID uuid) {
        if (active.remove(uuid) != null) markDirty();
    }

    public int getCooldown(UUID uuid, AbilitySlot slot) {
        return cooldowns.getOrDefault(uuid, Map.of()).getOrDefault(slot, 0);
    }

    public int getMaxCooldown(UUID uuid, AbilitySlot slot) {
        return maxCooldowns.getOrDefault(uuid, Map.of()).getOrDefault(slot, 0);
    }

    /**
     * Sets a cooldown for a specific player and slot, and syncs it to the client.
     */
    public void setCooldown(World world, UUID uuid, AbilitySlot slot, int ticks) {
        cooldowns.computeIfAbsent(uuid, k -> new HashMap<>()).put(slot, ticks);
        maxCooldowns.computeIfAbsent(uuid, k -> new HashMap<>()).put(slot, ticks);
        markDirty();

        ServerPlayerEntity player = world.getServer().getPlayerManager().getPlayer(uuid);
        if (player != null) {
            // ✅ New Fabric API send using CustomPayload
            ServerPlayNetworking.send(player, new SyncSigilCooldownPayload(
                    active.getOrDefault(uuid, SigilType.NONE),
                    slot,
                    ticks
            ));
        }
    }

    public void tickCooldowns() {
        cooldowns.forEach((uuid, map) -> map.replaceAll((slot, time) -> Math.max(0, time - 1)));
        markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        NbtList playersList = new NbtList();
        for (UUID uuid : active.keySet()) {
            NbtCompound playerTag = new NbtCompound();
            playerTag.putUuid("uuid", uuid);
            playerTag.putString("sigil", active.get(uuid).name());

            NbtList cdList = new NbtList();
            Map<AbilitySlot, Integer> cds = cooldowns.getOrDefault(uuid, Map.of());
            for (Map.Entry<AbilitySlot, Integer> cd : cds.entrySet()) {
                NbtCompound cdTag = new NbtCompound();
                cdTag.putString("slot", cd.getKey().name());
                cdTag.putInt("ticks", cd.getValue());
                cdList.add(cdTag);
            }
            playerTag.put("cooldowns", cdList);

            playersList.add(playerTag);
        }
        nbt.put("players", playersList);
        return nbt;
    }

    public static SigilState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        SigilState state = new SigilState();
        NbtList list = nbt.getList("players", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            NbtCompound t = list.getCompound(i);
            UUID uuid = t.getUuid("uuid");
            state.active.put(uuid, SigilType.valueOf(t.getString("sigil")));

            NbtList cdList = t.getList("cooldowns", NbtElement.COMPOUND_TYPE);
            Map<AbilitySlot, Integer> cds = new HashMap<>();
            for (int j = 0; j < cdList.size(); j++) {
                NbtCompound cdTag = cdList.getCompound(j);
                AbilitySlot slot = AbilitySlot.valueOf(cdTag.getString("slot"));
                cds.put(slot, cdTag.getInt("ticks"));
            }
            state.cooldowns.put(uuid, cds);
        }
        return state;
    }
}
