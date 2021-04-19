package md.takkapi.mapImages;

import md.takkapi.mapImages.commands.ImageCommand;
import md.takkapi.mapImages.models.ImageManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomMaps extends JavaPlugin {

    @Override
    public void onEnable() {
        ImageManager manager = ImageManager.getInstance();
        manager.init();
        this.getCommand("map").setExecutor(new ImageCommand());
    }

    @Override
    public void onDisable() {

    }

}
