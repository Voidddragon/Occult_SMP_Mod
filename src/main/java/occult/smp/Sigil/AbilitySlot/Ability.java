
package occult.smp.Sigil.AbilitySlot;

public interface Ability {
    
    /**
     * Execute the ability
     * @param context The context containing player, sigil type, and slot information
     */
    void execute(AbilityContext context);
    
    /**
     * Get the cooldown in milliseconds
     * @return Cooldown duration in milliseconds
     */
    long getCooldown();
    
    /**
     * Get the ability name
     * @return The display name of the ability
     */
    String getName();
    
    /**
     * Get the ability description
     * @return The description of what the ability does
     */
    String getDescription();
}
