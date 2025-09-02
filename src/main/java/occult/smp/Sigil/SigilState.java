package occult.smp.Sigil;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

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

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        NbtList list = new NbtList();
        for (Map.Entry<UUID, SigilType> e : active.entrySet()) {
            NbtCompound t = new NbtCompound();
            t.putUuid("uuid", e.getKey());
            t.putString("sigil", e.getValue().name());
            list.add(t);
        }
        nbt.put("players", list);
        return nbt;
    }

    public static SigilState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        SigilState state = new SigilState();
        NbtList list = nbt.getList("players", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            NbtCompound t = list.getCompound(i);
            state.active.put(t.getUuid("uuid"), SigilType.valueOf(t.getString("sigil")));
        }
        return state;
    }
}

