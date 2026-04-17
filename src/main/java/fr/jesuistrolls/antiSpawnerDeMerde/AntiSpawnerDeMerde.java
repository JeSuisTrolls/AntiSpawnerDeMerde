package fr.jesuistrolls.antiSpawnerDeMerde;

import org.bukkit.plugin.java.JavaPlugin;

public final class AntiSpawnerDeMerde extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SpawnerOffhandListener(), this);
    }
}