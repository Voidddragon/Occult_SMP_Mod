package occult.smp;

import net.fabricmc.api.ModInitializer;


import occult.smp.Network.AbilityPacketHandler;

import occult.smp.Network.ModNetworking;
import occult.smp.Network.OccultServerEvents;
import occult.smp.Network.SettingsSyncPackets;
import occult.smp.Sigil.Abilities;
import occult.smp.Sigil.DeathHandler;

import occult.smp.item.ModItemGroups;
import occult.smp.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OccultSmp implements ModInitializer {
	public static final String MOD_ID = "occult_smp";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
        ModItemGroups.registerItemGroups();
        DeathHandler.register();
        Abilities.registerAll();
        AbilityPacketHandler.register();
        OccultServerEvents.register();
        ModNetworking.registerPayloads();


    }

    }