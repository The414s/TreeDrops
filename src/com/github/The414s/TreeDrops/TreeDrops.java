package com.github.The414s.TreeDrops;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.The414s.util.T4Formatter;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class TreeDrops extends JavaPlugin {

	private BlockListen blocklistener = new BlockListen(this);
	protected Logger log = Logger.getLogger("Minecraft");
	private Logger debug = Logger.getLogger("TreeDrops");
	static String mainDirectory = "plugins/TreeDrops/";
	protected PermissionHandler pm;

	public void onEnable() {
		debug.setUseParentHandlers(false);

		File file = new File("plugins/TreeDrops/plugin.log");
		new File(mainDirectory).mkdir();
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.warning("[TreeDrops] Error #7" + e.getMessage());
			}
		}
		try {
			for (Handler ele : debug.getHandlers()) {
				ele.close();
				debug.removeHandler(ele);
			}
			FileHandler fh = new FileHandler("plugins/TreeDrops/plugin.log");
			fh.setFormatter(new T4Formatter());
			debug.addHandler(fh);
		} catch (SecurityException e1) {
			log.warning("[TreeDrops] Error #5");
			debug.warning(" Error #5");
		} catch (IOException e1) {
			log.warning("[TreeDrops] Error #6");
			debug.warning(" Error #6");
		}
		setupPermissions();
		new ConfigurationManager(this).load();
		PluginManager p = this.getServer().getPluginManager();
		p.registerEvent(Event.Type.BLOCK_BREAK, blocklistener,
				Event.Priority.Highest, this);
		p.registerEvent(Event.Type.LEAVES_DECAY, blocklistener,
				Event.Priority.Highest, this);
		log(Level.INFO, " [" + getVersion() + "] is enabled!");
	}

	public void onDisable() {
		
		debug.info("Plugin Disabed");
		for (Handler ele : debug.getHandlers()) {
			ele.close();
			debug.removeHandler(ele);
		}
		log(Level.INFO, "[" + getVersion() + "] is disabled!");
	}

	// logger
	public void log(Level level, String msg) {
		log.log(level, "[TreeDrops] " + msg);
	}

	// plugin version
	public String getVersion() {
		return this.getDescription().getVersion();
	}

	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {

		String commandName = command.getName();
		if (commandName.equalsIgnoreCase("treedrops")) {
			boolean console = false;
			if (!(sender instanceof Player)) {
				console = true;
				debug.info("Sender: Console");
			} else {
				Player pl = (Player) sender;
				debug.info("Sender: " + pl.getName());
			}
			if (console || sender.isOp()
					|| pm.has((Player) sender, "TreeDrops.admin.reload")) { // TreeDrops.admin.reload
				this.getServer().getPluginManager().disablePlugin(this);
				this.getServer().getPluginManager().enablePlugin(this);
				sender.sendMessage(ChatColor.RED
						+ "[TreeDrops] Configuration settings reloaded.");
			} else {
				sender.sendMessage(ChatColor.RED
						+ "[TreeDrops] You do not have permissions to use that command.");
			}

		}

		return true;
	}

	private void setupPermissions() {
		Plugin test = this.getServer().getPluginManager()
				.getPlugin("Permissions");

		if (this.pm == null) {
			if (test != null) {
				this.pm = ((Permissions) test).getHandler();
			} else {
				log.severe("[TreeDrops]Permissions not found disabling");
				this.getPluginLoader().disablePlugin(this);
			}
		}
	}
}