package calebcompass.calebcompass;

import calebcompass.calebcompass.betonquest.*;
import calebcompass.calebcompass.citizens.CitizensEvents;
import calebcompass.calebcompass.citizens.CitizensInstance;
import calebcompass.calebcompass.miscevents.ItemFocus;
import calebcompass.calebcompass.mythicmobs.MythicEvents;
import calebcompass.calebcompass.mythicmobs.MythicInstance;
import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.betonquest.betonquest.BetonQuest;

import java.util.logging.Level;

public final class CalebCompass extends JavaPlugin {

	private static CalebCompass instance;

	private static ConfigManager configManager;

	@Override
	public void onEnable() {
		instance = this;

		configManager = ConfigManager.getInstance();

		CompassMoveEvent moveEvent = new CompassMoveEvent();
		moveEvent.runTaskTimer(this, 0, 3);

		getServer().getPluginManager().registerEvents(new ItemFocus(), this);

		getServer().getPluginCommand("calebcompass").setExecutor(new CalebCompassCommand());

		if (Bukkit.getServer().getPluginManager().getPlugin("BetonQuest") != null) {
			BetonQuest bq = BetonQuest.getInstance();
			bq.registerEvents("compasstrack", TrackEvent.class);
			bq.registerEvents("clearcompass", CompassClear.class);
			bq.registerEvents("togglewaypoint", TogglePoint.class);
			bq.registerEvents("focuspoint", Focus.class);

			getServer().getPluginManager().registerEvents(new TargetChange(), this);
			log("Plugin hooked: BetonQuest");
		}

		if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
			MythicEvents mythEvents = new MythicEvents();
			mythEvents.runTaskTimer(this, 0, 3);
			log("Plugin hooked: MythicMobs");
		}

		if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
			CitizensEvents npcEvents = new CitizensEvents();
			npcEvents.runTaskTimer(this, 0, 3);
			log("Plugin hooked: Citizens");
		}
	}

	@Override
	public void onDisable() {
		CompassInstance.getInstance().saveData();
	}

	public static CalebCompass getInstance() {
		return instance;
	}

	public static ConfigManager getConfigManager() {
		return configManager;
	}

	public static void log(String log) {
		Bukkit.getLogger().log(Level.INFO, "[CalebCompass]: " + log);
	}

}
