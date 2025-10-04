
package occult.smp.Sigil.Gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OccultHudConfig {
    public static float hudScale = 0.5f;
    public static int hudOffsetY = -50; // Offset from bottom (negative moves up)
    public static int hudOffsetX = 0; // Offset from center (0 = centered)

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/occult_hud.json");

    // Snapshot class to hold current values
    private static class Snapshot {
        float hudScale;
        int hudOffsetY;
        int hudOffsetX;
    }

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            save(); // Create default config
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Snapshot loaded = GSON.fromJson(reader, Snapshot.class);
            hudScale = loaded.hudScale;
            hudOffsetY = loaded.hudOffsetY;
            hudOffsetX = loaded.hudOffsetX;
        } catch (IOException e) {
            System.err.println("[Occult HUD] Failed to load config: " + e.getMessage());
        }
    }

    public static void save() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                Snapshot snapshot = new Snapshot();
                snapshot.hudScale = hudScale;
                snapshot.hudOffsetY = hudOffsetY;
                snapshot.hudOffsetX = hudOffsetX;
                GSON.toJson(snapshot, writer);
            }
        } catch (IOException e) {
            System.err.println("[Occult HUD] Failed to save config: " + e.getMessage());
        }
    }
}
