
package occult.smp.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import occult.smp.OccultSmp;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerConfigData {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_DIR = new File("config/occult-smp/players");
    private static final Map<UUID, HudConfig> HUD_CONFIGS = new HashMap<>();
    
    static {
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }
    }
    
    public static void saveHudConfig(UUID playerId, int x, int y, float scale, boolean enabled) {
        HudConfig config = new HudConfig(x, y, scale, enabled);
        HUD_CONFIGS.put(playerId, config);
        
        File configFile = new File(CONFIG_DIR, playerId.toString() + ".json");
        try (FileWriter writer = new FileWriter(configFile)) {
            JsonObject json = new JsonObject();
            json.addProperty("x", x);
            json.addProperty("y", y);
            json.addProperty("scale", scale);
            json.addProperty("enabled", enabled);
            
            GSON.toJson(json, writer);
        } catch (IOException e) {
            OccultSmp.LOGGER.error("Failed to save HUD config for player " + playerId, e);
        }
    }
    
    public static HudConfig loadHudConfig(UUID playerId) {
        if (HUD_CONFIGS.containsKey(playerId)) {
            return HUD_CONFIGS.get(playerId);
        }
        
        File configFile = new File(CONFIG_DIR, playerId.toString() + ".json");
        if (!configFile.exists()) {
            return new HudConfig(10, 10, 1.0f, true);
        }
        
        try (FileReader reader = new FileReader(configFile)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            HudConfig config = new HudConfig(
                json.get("x").getAsInt(),
                json.get("y").getAsInt(),
                json.get("scale").getAsFloat(),
                json.get("enabled").getAsBoolean()
            );
            HUD_CONFIGS.put(playerId, config);
            return config;
        } catch (IOException e) {
            OccultSmp.LOGGER.error("Failed to load HUD config for player " + playerId, e);
            return new HudConfig(10, 10, 1.0f, true);
        }
    }
    
    public static class HudConfig {
        private final int x;
        private final int y;
        private final float scale;
        private final boolean enabled;
        
        public HudConfig(int x, int y, float scale, boolean enabled) {
            this.x = x;
            this.y = y;
            this.scale = scale;
            this.enabled = enabled;
        }
        
        public int getX() { return x; }
        public int getY() { return y; }
        public float getScale() { return scale; }
        public boolean isEnabled() { return enabled; }
    }
}
