package fr.bayantoine.plugintest.listeners.interact;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if(event.getClickedBlock() != null && action == Action.RIGHT_CLICK_BLOCK) {

            // Gestion panneaux
            BlockState bs = event.getClickedBlock().getState();
            if(bs instanceof Sign) {
                Sign sign = (Sign) bs;
                if(sign.getLine(0).equals("[Time]")) {
                    if(sign.getLine(1).equals("day")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set day");
                        player.sendMessage("JOUR");
                    } else if(sign.getLine(1).equals("night")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set night");
                        player.sendMessage("NUIT");
                    }
                }
            }

            // Gestion chaudron
            Block block = event.getClickedBlock();
            if(block.getType() == Material.WATER_CAULDRON) {
                if(event.getHand() == EquipmentSlot.HAND) {
                    if(player.getInventory().getItemInMainHand().getType() == Material.WHEAT) {
                        //final BlockData cauldron = (BlockData) block.getState().getData();
                        //final Levelled cauldron = (Levelled) block.getState();
                        
                        //player.sendMessage("- "+cauldron.getLevel());
                        player.sendMessage("OK");

                        // Récupère les items dans la main
                        final ItemStack handItems = player.getInventory().getItemInMainHand();
                        // On retire 1 item
                        handItems.setAmount(handItems.getAmount() - 1);
                        // On met à jour l'inventaire
                        player.getInventory().setItemInMainHand(handItems);
                        player.updateInventory();
                    }
                }
            }

        }

        // Stop s'il n'y a pas d'item mais qu'il y a une action
        if(item == null && action != null) return;

        switch (item.getType()) {
            case WOODEN_SWORD:
                if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    createInventory(player);
                }
                break;
            case BREAD:
                if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    player.sendMessage("CLIC");
                }
                break;
        
            default:
                break;
        }

    }

    private void createInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, (9 * 1), "Menu");
        inventory.setItem(4, new ItemStack(Material.IRON_PICKAXE, 1));

        player.openInventory(inventory);

    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        InventoryView invView = event.getView();

        if(event.getCurrentItem() == null && event.getAction() != null) return;

        if(invView.getTitle() == "Menu") {

            event.setCancelled(true);
            player.closeInventory();
            
            switch (event.getCurrentItem().getType()) {
                case IRON_PICKAXE:
                    player.sendMessage("Pioche !!!");
                    double x = 9;
                    double y = 78;
                    double z = 194;
                    Location location = new Location(Bukkit.getWorld("world"), x, y, z);
                    player.teleport(location);
                    break;
            
                default:
                    break;
            }
        }

        
    }
    
}
