package fr.bayantoine.plugintest.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player)sender;

            if(cmd.getName().equalsIgnoreCase("test")) {
                player.sendMessage("§bCommande §4test §5exécutée");
                return true;
            }

            if(cmd.getName().equalsIgnoreCase("alert")) {
                if(args.length == 0) {
                    player.sendMessage("§8La commande est : §f/alert <message>");
                    return true;
                } else if(args.length >= 1) {
                    StringBuilder bc = new StringBuilder();
                    for(String str : args) {
                        bc.append(str + " ");
                    }
                    Bukkit.broadcastMessage("[" + player.getName() + "] §4" + bc.toString());
                    return true;
                }
            }
            
        }

        return false;
    }

}
