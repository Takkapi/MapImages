package md.takkapi.mapImages.commands;

import md.takkapi.mapImages.models.ImageManager;
import md.takkapi.mapImages.models.LogoRenderer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

public class ImageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //MapView <--- map meta MapView -> ItemStack.MAP
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "This is an only player command!");
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage(ChatColor.DARK_RED + "Invalid usage, /getmap <url>");
            return true;
        }

        boolean spawnNewMap = false;
        ItemStack mainHand  = player.getInventory().getItemInMainHand();

        // Deny map creation if player is not holding a map and doesn't have relevant permission
        if(mainHand.getType() != Material.MAP) {
            if(!player.hasPermission("mapimages.getmap.thinair")) {
                player.sendMessage(ChatColor.DARK_RED + "You must be holding a map");
                return true;
            } else {
                spawnNewMap = true;
            }
        } else {
            // Otherwise multiple stacked empty maps would be overwritten to 1 filled map
            if(mainHand.getAmount() > 1) {
                player.sendMessage(ChatColor.DARK_RED + "You must be holding exactly one map");
                return true;
            }
        }

        MapView view = Bukkit.createMap(player.getWorld());
        view.getRenderers().clear();

        LogoRenderer renderer = new LogoRenderer();
        if (!renderer.load(args[0])) {
            player.sendMessage(ChatColor.DARK_RED + "Image load failure. Check URL validity");
            return true;
        }
        view.addRenderer(renderer);

        ItemStack map = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) map.getItemMeta();

        meta.setMapView(view);
        map.setItemMeta(meta);

        player.getInventory().addItem(map);
        player.sendMessage(ChatColor.GREEN + "Map has been created");

        ImageManager manager = ImageManager.getInstance();
        manager.saveImage(view.getId(), args[0]);
        return true;
    }
}
