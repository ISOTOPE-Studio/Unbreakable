package cc.isotopestudio.Unbreakable;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cc.isotopestudio.Unbreakable.Unbreakable.plugin;

/**
 * Created by Mars on 6/5/2016.
 * Copyright ISOTOPE Studio
 */
public class ArmorListener implements Listener {

    private static boolean isUnbreakable(ItemStack item) {
        try {
            return item.getItemMeta().getLore().contains("¡ìa·À±¬");
        } catch (Exception ignored) {
        }
        return false;
    }

    private static ItemStack handleUnbreakable(ItemStack item) {
        item.setDurability((short) 0);
        return item;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        List<ItemStack> items = event.getDrops();
        event.getEntity().getEquipment();
        for (ItemStack item : items) {
            if (isUnbreakable(item)) {
                item.setDurability((short) 0);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getItemInHand();
        if (isUnbreakable(item)) {
            item.setDurability((short) 0);
            event.getPlayer().setItemInHand(item);
        }
    }

    @EventHandler
    public void onItem(PlayerItemBreakEvent event) {
        ItemStack item = event.getBrokenItem();
        if (isUnbreakable(event.getBrokenItem())) {
            Material type = item.getType();
            item.setAmount(1);
            item.setDurability((short) 0);
            if (type == Material.DIAMOND_HELMET || type == Material.GOLD_HELMET ||
                    type == Material.CHAINMAIL_HELMET || type == Material.IRON_HELMET || type == Material.LEATHER_HELMET) {
                event.getPlayer().getEquipment().setHelmet(item);
                return;
            }
            if (type == Material.DIAMOND_CHESTPLATE || type == Material.GOLD_CHESTPLATE ||
                    type == Material.CHAINMAIL_CHESTPLATE || type == Material.IRON_CHESTPLATE || type == Material.LEATHER_CHESTPLATE) {
                event.getPlayer().getEquipment().setChestplate(item);
                return;
            }
            if (type == Material.DIAMOND_LEGGINGS || type == Material.GOLD_LEGGINGS ||
                    type == Material.CHAINMAIL_LEGGINGS || type == Material.IRON_LEGGINGS || type == Material.LEATHER_LEGGINGS) {
                event.getPlayer().getEquipment().setLeggings(item);
                return;
            }
            if (type == Material.DIAMOND_BOOTS || type == Material.GOLD_BOOTS ||
                    type == Material.CHAINMAIL_BOOTS || type == Material.IRON_BOOTS || type == Material.LEATHER_BOOTS) {
                event.getPlayer().getEquipment().setBoots(item);
                return;
            }
            event.getPlayer().setItemInHand(item);
        }
    }

    @EventHandler
    public void onPlayerDamaged(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            EntityEquipment equipment = player.getEquipment();
            final Map<EquipType, ItemStack> equipTypeItemStackMap = new HashMap<>();
            if (equipment.getHelmet() != null && isUnbreakable(equipment.getHelmet())) {
                equipTypeItemStackMap.put(EquipType.HELMET, equipment.getHelmet());
            }
            if (equipment.getChestplate() != null && isUnbreakable(equipment.getChestplate())) {
                equipTypeItemStackMap.put(EquipType.CHESTPLATE, equipment.getChestplate());
            }
            if (equipment.getLeggings() != null && isUnbreakable(equipment.getLeggings())) {
                equipTypeItemStackMap.put(EquipType.LEGGINGS, equipment.getLeggings());
            }
            if (equipment.getBoots() != null && isUnbreakable(equipment.getBoots())) {
                equipTypeItemStackMap.put(EquipType.BOOTS, equipment.getBoots());
            }
            if (equipTypeItemStackMap.size() > 0) {
                System.out.print(player.getName() + " " + player.getHealth() + ": " + event.getCause());

                for (ItemStack item : equipTypeItemStackMap.values()) {
                    System.out.print(item.getType() + " " + item.getDurability());
                }
                for (EquipType type : EquipType.values()) {
                    if (equipTypeItemStackMap.get(type) != null) {
                        switch (type) {
                            case HELMET: {
                                player.getEquipment().setHelmet(handleUnbreakable(equipTypeItemStackMap.get(type)));
                                break;
                            }
                            case CHESTPLATE: {
                                player.getEquipment().setChestplate(handleUnbreakable(equipTypeItemStackMap.get(type)));

                                break;
                            }
                            case LEGGINGS: {
                                player.getEquipment().setLeggings(handleUnbreakable(equipTypeItemStackMap.get(type)));
                                break;
                            }
                            case BOOTS: {
                                player.getEquipment().setBoots(handleUnbreakable(equipTypeItemStackMap.get(type)));
                                break;
                            }
                        }
                    }
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {

                    }
                }.runTaskLater(plugin, 1);
            }
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }
        final Player player = (Player) event.getEntity().getShooter();
        final ItemStack item = player.getItemInHand();
        if (isUnbreakable(item)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setItemInHand(handleUnbreakable(item));
                }
            }.runTaskLater(plugin, 1);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        final Player player = (Player) event.getDamager();
        final ItemStack item = player.getItemInHand();
        if (isUnbreakable(item)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setItemInHand(handleUnbreakable(item));
                }
            }.runTaskLater(plugin, 1);
        }
    }

    private enum EquipType {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS;
    }

}
