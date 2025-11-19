package md.takkapi.mapImages;

import md.takkapi.mapImages.commands.ImageCommand;
import md.takkapi.mapImages.models.ImageManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomMaps extends JavaPlugin {

    @Override
    public void onEnable() {
        int pluginId = 21609;
        Metrics metrics = new Metrics(this, pluginId);

        ImageManager manager = ImageManager.getInstance();
        manager.init();
        this.getCommand("getmap").setExecutor(new ImageCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
