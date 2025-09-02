package occult.smp.Sigil.Gui;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigSave {
    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.of("Occult HUD Settings"))
                .setSavingRunnable(OccultHudConfig::save); // ✅ Save when screen closes

        ConfigEntryBuilder eb = builder.entryBuilder();

        builder.getOrCreateCategory(Text.of("HUD"))
                .addEntry(eb.startFloatField(Text.of("HUD Scale"), OccultHudConfig.hudScale)
                        .setDefaultValue(0.5f)
                        .setMin(0.25f)
                        .setMax(2.0f)
                        .setSaveConsumer(newValue -> {
                            OccultHudConfig.hudScale = newValue;
                            OccultHudConfig.save(); // ✅ Save immediately
                        })
                        .build()
                )
                .addEntry(eb.startIntField(Text.of("Vertical Offset"), OccultHudConfig.hudOffsetY)
                        .setDefaultValue(-80)
                        .setMin(-200)
                        .setMax(0)
                        .setSaveConsumer(newValue -> {
                            OccultHudConfig.hudOffsetY = newValue;
                            OccultHudConfig.save(); // ✅ Save immediately
                        })
                        .build()
                );

        return builder.build();
    }
}
