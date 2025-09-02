package occult.smp.Sigil.Gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OccultHudConfig {
    public static float hudScale = 0.5f;
    public static int hudOffsetY = -80;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/occult_hud.json");

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            save(); // create default config
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            OccultHudConfig loaded = GSON.fromJson(reader, OccultHudConfig.class);
            hudScale = loaded.hudScale;
            hudOffsetY = loaded.hudOffsetY;
        } catch (IOException e) {
            System.err.println("[Occult HUD] Failed to load config: " + e.getMessage());
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(new OccultHudConfig(), writer);
        } catch (IOException e) {
            System.err.println("[Occult HUD] Failed to save config: " + e.getMessage());
        }
    }
}
