package fr.bayantoine.plugintest;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import fr.bayantoine.plugintest.commands.CommandTest;
import fr.bayantoine.plugintest.listeners.ListenersManager;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        System.out.println("PluginTest activé");

        //Commande de test
        getCommand("test").setExecutor(new CommandTest());
        //Commande d'alerte'
        getCommand("alert").setExecutor(new CommandTest());

        new ListenersManager(this).registerListeners();

        // test ajout recette
        NamespacedKey key = new NamespacedKey(this, this.getDescription().getName());
        ShapedRecipe sh = new ShapedRecipe(key, new ItemStack(Material.SADDLE, 1));
        sh.shape(new String[] {"S S", "ISI"});
        sh.setIngredient('S', Material.LEATHER);
        sh.setIngredient('I', Material.IRON_INGOT);
        getServer().addRecipe(sh);
    }

    @Override
    public void onDisable() {
        System.out.println("PluginTest désactivé");
    }
    
}