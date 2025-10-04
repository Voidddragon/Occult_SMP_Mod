
package occult.smp.util;

import occult.smp.config.GuiConfig;
import occult.smp.config.KeybindConfig;

public interface PlayerConfigData {
    KeybindConfig occult$getKeybindConfig();
    void occult$setKeybindConfig(KeybindConfig config);
    
    GuiConfig occult$getGuiConfig();
    void occult$setGuiConfig(GuiConfig config);
}
