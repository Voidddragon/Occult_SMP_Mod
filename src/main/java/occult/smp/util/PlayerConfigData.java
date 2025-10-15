
package occult.smp.util;

import occult.smp.config.GuiConfig;
import occult.smp.config.KeybindConfig;

/**
 * Interface for player configuration data storage
 * Implemented via Mixin on ServerPlayerEntity
 */
public interface PlayerConfigData {
    GuiConfig occult$getGuiConfig();
    void occult$setGuiConfig(GuiConfig config);
    
    KeybindConfig occult$getKeybindConfig();
    void occult$setKeybindConfig(KeybindConfig config);
}
