package occult.smp.Sigil;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.datafixer.DataFixTypes;
import occult.smp.Sigil.AbilitySlot.AbilitySlot;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AbilityState extends PersistentState {
    private static final String KEY = "occult_ability_state";

    // If your mappings require a Type factory (MC 1.20.5+), use this:
    private static final PersistentState.Type<AbilityState> TYPE =
            new PersistentState.Type<>(AbilityState::new,AbilityState::fromNbt, (DataFixTypes) null);

    private final Map<UUID, EnumMap<AbilitySlot, Integer>> nextUse = new HashMap<>();

    public static AbilityState get(World world) {
        PersistentStateManager psm = world.getServer().getOverworld().getPersistentStateManager();
        // For 1.20.5+, use TYPE; for older mappings, use the 3-arg lambdas.
        return psm.getOrCreate(TYPE, KEY);
        // If your environment needs the lambda form instead, comment the line above and use:
        // return psm.getOrCreate(AbilityState::fromNbt, AbilityState::new, KEY);
    }

    public boolean tryConsume(ServerPlayerEntity player, AbilitySlot slot, int cooldownTicks) {
        int now = (int) player.getWorld().getTime();
        EnumMap<AbilitySlot, Integer> map =
                nextUse.computeIfAbsent(player.getUuid(), id -> new EnumMap<>(AbilitySlot.class));
        int readyAt = map.getOrDefault(slot, 0);
        if (now < readyAt) return false;
        map.put(slot, now + cooldownTicks);
        markDirty();
        return true;
    }

    public int ticksRemaining(ServerPlayerEntity player, AbilitySlot slot) {
        int now = (int) player.getWorld().getTime();
        EnumMap<AbilitySlot, Integer> map = nextUse.get(player.getUuid());
        if (map == null) return 0;
        return Math.max(0, map.getOrDefault(slot, 0) - now);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        NbtList list = new NbtList();
        for (Map.Entry<UUID, EnumMap<AbilitySlot, Integer>> e : nextUse.entrySet()) {
            UUID id = e.getKey();
            for (Map.Entry<AbilitySlot, Integer> s : e.getValue().entrySet()) {
                NbtCompound t = new NbtCompound();
                t.putUuid("uuid", id);
                t.putString("slot", s.getKey().name());
                t.putInt("ready", s.getValue());
                list.add(t);
            }
        }
        nbt.put("cooldowns", list);
        return nbt;
    }

    public static AbilityState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        AbilityState state = new AbilityState();
        NbtList list = nbt.getList("cooldowns", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            NbtCompound t = list.getCompound(i);
            UUID id = t.getUuid("uuid");
            AbilitySlot slot = AbilitySlot.valueOf(t.getString("slot"));
            int ready = t.getInt("ready");
            EnumMap<AbilitySlot, Integer> map =
                    state.nextUse.computeIfAbsent(id, x -> new EnumMap<>(AbilitySlot.class));
            map.put(slot, ready);
        }
        return state;
    }
}
