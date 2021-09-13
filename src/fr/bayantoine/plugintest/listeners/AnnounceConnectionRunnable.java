package fr.bayantoine.plugintest.listeners;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnnounceConnectionRunnable extends BukkitRunnable {

    public int time = 5;
    public Player player;

    public AnnounceConnectionRunnable(Player player) {
        this.player = player;
    }

    @Override
    public void run() {

        if(time == 0) {
            this.cancel();
            return;
        }

        player.sendMessage(String.valueOf(time));
        time--;
    }

}
