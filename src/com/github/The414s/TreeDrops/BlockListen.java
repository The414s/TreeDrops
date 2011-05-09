package com.github.The414s.TreeDrops;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.CropState;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;

public class BlockListen extends BlockListener {
	private TreeDrops plugin;
	private ItemStack cocoaBean = new ItemStack(Material.INK_SACK, 1,
			(short) (15 - DyeColor.BROWN.getData()));
	private ItemStack apple = new ItemStack(Material.APPLE, 1);
	private ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE, 1);

	private Random random = new Random();
	private Logger debug = Logger.getLogger("TreeDrops");
	public BlockListen(TreeDrops instance) {
		plugin = instance;
	}

	public void onBlockBreak(BlockBreakEvent event) {
		try {
			debug.info("###################################");
			debug.info("entering");
			Block block = event.getBlock();
			Player player = event.getPlayer();
			if ((event.getBlock().getType() != Material.LEAVES) && !(ConfigurationManager.cocoaBeansCropDrops && event.getBlock().getType() == Material.CROPS)) {
				debug.info("Not leaves/crops = false/not crops");
				return;
			}
			if(event.getBlock().getType() == Material.CROPS)
			{debug.info("its crops but is it ripe?");
				if(CropState.getByData(block.getData()) != CropState.RIPE)
				{
					debug.info("nope not ripe");
					return;
				}
				debug.info("Ripe!");
			}
			
			debug.info("TreeType: " + block.getType());
			debug.info("Player Name: " + player.getName());
			boolean hasApples = plugin.pm.has(player, "TreeDrops.Apples");
			boolean hasGApples = plugin.pm.has(player, "TreeDrops.GoldenApples");
			boolean hasBeans = plugin.pm.has(player, "TreeDrops.CocoaBeans");
			boolean isTree =  false;
			
			debug.info("is leaves?");
			if(block.getType() == Material.LEAVES)
			{
				debug.info("Yes!");
				isTree = true;
			}
			debug.info("Check permissions");
			debug.info("hasApples:" +hasApples);
			debug.info("hasGApples:" +hasGApples);
			debug.info("hasBeans:" +hasBeans);
			if (hasApples || hasGApples || hasBeans) {
				if (ConfigurationManager.apples && isTree) {
					double roll = random.nextDouble();
					debug.info("Apple Chance:"+ConfigurationManager.applesTreeChance);
					debug.info("Apple Roll:"+roll);
					
					if (ConfigurationManager.applesTreeDrops
							&& ConfigurationManager.applesTreeChance >= roll) {
						block.getWorld().dropItemNaturally(block.getLocation(), apple);
						debug.info("Dropped an apple");
					}
				}
				if (ConfigurationManager.cocoaBeans) {
					double roll = random.nextDouble();
					debug.info("BeanTree Chance:"+ConfigurationManager.cocoaBeansTreeChance);
					debug.info("BeanTree Roll:"+roll);
					if (isTree &&ConfigurationManager.cocoaBeansTreeDrops
							&& ConfigurationManager.cocoaBeansTreeChance >=roll) {
						block.getWorld().dropItemNaturally(block.getLocation(), cocoaBean);
						debug.info("Dropped bean from tree");
					}
					roll = random.nextDouble();
					debug.info("BeanCrop Chance:"+ConfigurationManager.cocoaBeansTreeChance);
					debug.info("BeanCrop Roll:"+roll);
					if (!isTree &&ConfigurationManager.cocoaBeansCropDrops
							&& ConfigurationManager.cocoaBeansCropChance >= roll) {
						
						block.getWorld().dropItemNaturally(block.getLocation(), cocoaBean);
						debug.info("dropped bean from crop");
					}
				}
				if (ConfigurationManager.goldenApples && isTree) {
					double roll = random.nextDouble();
					debug.info("GoldenApple Chance:"+ConfigurationManager.applesTreeChance);
					debug.info("GoldenApple Roll:"+roll);
					if (ConfigurationManager.goldenApplesTreeDrops
							&& ConfigurationManager.goldenApplesTreeChance >=roll) {
						block.getWorld().dropItemNaturally(block.getLocation(), goldenApple);
						debug.info("Dropped a golden apple");
					}
				}

			} else {
				debug.info("Doesnt have permission");
			}
			debug.info("###################################");
		} catch (Exception e) {
			plugin.log.info("[TreeDrops] Error #1");
			debug.severe("Error #1: "+e.toString());
		}
	}
}