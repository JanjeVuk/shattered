package net.etum.shattered;

import net.etum.shattered.listeners.Commands;
import net.etum.shattered.listeners.Events;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    private Main instance;



    @Override
    public void onEnable() {
        instance = this;

        new Commands(this);
        new Events(this);
     }


    @Override
    public void onDisable() {

    }

    public Main getInstance() {
        return instance;
    }
}
