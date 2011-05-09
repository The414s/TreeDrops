package com.github.The414s.TreeDrops;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.util.config.Configuration;

public class ConfigurationManager {
	public TreeDrops plugin;
	private Configuration config;
	private Logger debug = Logger.getLogger("TreeDrops");
	

	// Beans
	protected static boolean cocoaBeans = false;
	protected static boolean cocoaBeansCropDrops = false;
	protected static double cocoaBeansCropChance = 0.0;
	protected static boolean cocoaBeansTreeDrops = false;
	protected static double cocoaBeansTreeChance = 0.0;

	// Apples
	protected static boolean apples = false;
	protected static boolean applesTreeDrops = false;
	protected static double applesTreeChance = 0.0;

	// goldenApples
	protected static boolean goldenApples = false;
	protected static boolean goldenApplesTreeDrops = false;
	protected static double goldenApplesTreeChance = 0.0;

	public ConfigurationManager(TreeDrops instance) {
		plugin = instance;
	}

	public void load() {
		config = plugin.getConfiguration();
		if (!new File(plugin.mainDirectory, "config.yml").exists()) {
			defaultConfig();
		}
		loadConfig();
	}

	public void loadConfig() {
		debug.info("Loading Config");
		config.load();
		// beans
		cocoaBeans = config.getBoolean("CocoaBeans.Enabled", false);
		cocoaBeansCropDrops = config.getBoolean("CocoaBeans.Drop-from-crops.Enabled",
				false);
		cocoaBeansCropChance = config.getDouble(
				"CocoaBeans.Drop-from-crops.chance", 0.0);
		cocoaBeansTreeDrops = config.getBoolean("CocoaBeans.Drop-from-trees.Enabled",
				false);
		cocoaBeansTreeChance = config.getDouble(
				"CocoaBeans.Drop-from-trees.chance", 0.0);
		debug.info("cocoaBeans: " + cocoaBeans);
		debug.info("cocoaBeansCropDrops: " + cocoaBeansCropDrops);
		debug.info("cocoaBeansCropChance: " + cocoaBeansCropChance);
		debug.info("cocoaBeansTreeDrops: " + cocoaBeansTreeDrops);
		debug.info("cocoaBeansTreeChance: " + cocoaBeansTreeChance);
		// apples
		apples = config.getBoolean("Apples.Enabled", false);
		applesTreeDrops = config.getBoolean("Apples.Drop-from-trees.Enabled", false);
		applesTreeChance = config.getDouble("Apples.Drop-from-trees.chance",
				0.0);
		debug.info("apples: " + apples);
		debug.info("applesTreeDrops: " + applesTreeDrops);
		debug.info("applesTreeChance: " + applesTreeChance);

		// golden
		goldenApples = config.getBoolean("GoldenApples.Enabled", false);
		goldenApplesTreeDrops = config.getBoolean(
				"GoldenApples.Drop-from-trees.Enabled", false);
		goldenApplesTreeChance = config.getDouble(
				"GoldenApples.Drop-from-trees.chance", 0.0);
		debug.info("goldenApples: " + goldenApples);
		debug.info("goldenApplesTreeDrops: " + goldenApplesTreeDrops);
		debug.info("goldenApplesTreeChance: " + goldenApplesTreeChance);
		debug.info("Loaded");
	}

	public void defaultConfig() {
		// beans
		config.setProperty("CocoaBeans", true);
		config.setProperty("CocoaBeans.Enabled", true);
		config.setProperty("CocoaBeans.Drop-from-crops", true);
		config.setProperty("CocoaBeans.Drop-from-crops.Enabled", true);
		config.setProperty("CocoaBeans.Drop-from-crops.chance", 0.01);
		config.setProperty("CocoaBeans.Drop-from-trees", true);
		config.setProperty("CocoaBeans.Drop-from-trees.Enabled", true);
		config.setProperty("CocoaBeans.Drop-from-trees.chance", 0.05);

		// apples
		config.setProperty("Apples", true);
		config.setProperty("Apples.Enabled", true);
		config.setProperty("Apples.Drop-from-trees", true);
		config.setProperty("Apples.Drop-from-trees.Enabled", true);
		config.setProperty("Apples.Drop-from-trees.chance", 0.05);

		// golden
		config.setProperty("GoldenApples", true);
		config.setProperty("GoldenApples.Enabled", true);
		config.setProperty("GoldenApples.Drop-from-trees", true);
		config.setProperty("GoldenApples.Drop-from-trees.Enabled", true);
		config.setProperty("GoldenApples.Drop-from-trees.chance", 0.002);

		config.save();
		debug.info("Created new Config");
	}

}
