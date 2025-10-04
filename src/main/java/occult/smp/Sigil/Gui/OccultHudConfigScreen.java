
package occult.smp.Sigil.Gui;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class OccultHudConfigScreen {
    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.of("Occult HUD Settings"))
                .setSavingRunnable(OccultHudConfig::save);

        ConfigEntryBuilder eb = builder.entryBuilder();

        builder.getOrCreateCategory(Text.of("HUD"))
                .addEntry(eb.startFloatField(Text.of("HUD Scale"), OccultHudConfig.hudScale)
                        .setDefaultValue(0.5f)
                        .setMin(0.25f)
                        .setMax(2.0f)
                        .setTooltip(Text.of("Adjust the size of the HUD slots"))
                        .setSaveConsumer(newValue -> {
                            OccultHudConfig.hudScale = newValue;
                            OccultHudConfig.save();
                        })
                        .build()
                )
                .addEntry(eb.startIntField(Text.of("Vertical Offset"), OccultHudConfig.hudOffsetY)
                        .setDefaultValue(-50)
                        .setMin(-200)
                        .setMax(0)
                        .setTooltip(Text.of("Move the HUD up from the bottom (negative = higher)"))
                        .setSaveConsumer(newValue -> {
                            OccultHudConfig.hudOffsetY = newValue;
                            OccultHudConfig.save();
                        })
                        .build()
                )
                .addEntry(eb.startIntField(Text.of("Horizontal Offset"), OccultHudConfig.hudOffsetX)
                        .setDefaultValue(0)
                        .setMin(-500)
                        .setMax(500)
                        .setTooltip(Text.of("Move the HUD left or right from center"))
                        .setSaveConsumer(newValue -> {
                            OccultHudConfig.hudOffsetX = newValue;
                            OccultHudConfig.save();
                        })
                        .build()
                );

        return builder.build();
    }
}
