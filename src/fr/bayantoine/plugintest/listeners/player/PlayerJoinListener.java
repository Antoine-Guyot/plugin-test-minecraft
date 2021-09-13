package fr.bayantoine.plugintest.listeners.player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.bayantoine.plugintest.Main;
import fr.bayantoine.plugintest.listeners.AnnounceConnectionRunnable;

public class PlayerJoinListener implements Listener {

    private Main main;

    public PlayerJoinListener(Main plugin) {
        this.main = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Récupération du joueur
        Player player = event.getPlayer();

        // Modifie le nombre de points de vie du joueur
        player.setHealthScale(40);

        // Vide l'inventaire du joueur
        //player.getInventory().clear();
        
        // Epée custom
        /*ItemStack customSword = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta customM = customSword.getItemMeta();
        customM.setDisplayName("§6Épée d'entraînement");
        customM.setLore(Arrays.asList("§7Cette épée a été conçue pour l'entraînement au combat.", "§7Qualité : §8faible"));
        customSword.setItemMeta(customM);
        player.getInventory().setItemInMainHand(customSword);*/

        // Donne du pain
        //player.getInventory().addItem(new ItemStack(Material.BREAD, 12));

        //player.updateInventory();

        //event.setJoinMessage("Attention ! " + player.getName() + " est arrivé !");

        new AnnounceConnectionRunnable(player).runTaskTimer(main, 0L, 20L);

        Bukkit.getScheduler().runTaskLater(main,
        new Runnable() {
            @Override
            public void run() {
                List<String> listMsg = main.getConfig().getStringList("messages.playerJoin");
                Random rand = new Random();
                player.sendMessage(listMsg.get(rand.nextInt(listMsg.size())).replace("&", "§"));
                player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1f, 1f);
            }
        }, (20 * 5));
    }
    
}
