
package occult.smp.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TextUtil {
    
    public static MutableText colored(String text, Formatting color) {
        return Text.literal(text).formatted(color);
    }
    
    public static MutableText translatable(String key, Object... args) {
        return Text.translatable(key, args);
    }
    
    public static MutableText error(String message) {
        return colored(message, Formatting.RED);
    }
    
    public static MutableText success(String message) {
        return colored(message, Formatting.GREEN);
    }
    
    public static MutableText warning(String message) {
        return colored(message, Formatting.YELLOW);
    }
    
    public static MutableText info(String message) {
        return colored(message, Formatting.AQUA);
    }
}
