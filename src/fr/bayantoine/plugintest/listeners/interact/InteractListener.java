package fr.bayantoine.plugintest.listeners.interact;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
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
import org.bukkit.inventory.meta.ItemMeta;

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

                if(sign.getLine(0).equals("[Shop]")) {
                    if(sign.getLine(2) != null) {
                        try {
                            int cost = Integer.parseInt(sign.getLine(2));
                            if(player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName() && player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                                if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Carte de paiement")) {
                                    List<String> lore = player.getInventory().getItemInMainHand().getItemMeta().getLore();
                                    if(lore.size() >= 2) {
                                        if(Integer.parseInt(lore.get(1)) >= cost) {
                                            int money = Integer.parseInt(lore.get(1));
                                            // Récupère les items dans la main
                                            final ItemStack card = player.getInventory().getItemInMainHand();
                                            ItemMeta customCard = card.getItemMeta();
                                            // Modification du lore
                                            customCard.setLore(Arrays.asList(lore.get(0), "" + (money - cost)));
                                            card.setItemMeta(customCard);
                                            // On met à jour l'inventaire
                                            player.getInventory().setItemInMainHand(card);
                                            player.updateInventory();
                                            player.sendMessage("Achat effectué");
                                            player.getInventory().addItem(new ItemStack(Material.BREAD, 1));
                                        } else {
                                            player.sendMessage("Vous n'avez pas assez d'argent");
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            
                        }
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
