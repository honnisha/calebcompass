package calebcompass.calebcompass.betonquest;

import calebcompass.calebcompass.util.CompassLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import calebcompass.calebcompass.CalebCompass;
import calebcompass.calebcompass.util.CompassInstance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.betonquest.betonquest.api.QuestCompassTargetChangeEvent;

public class TargetChange implements Listener {

    @EventHandler
    public void targetChange(QuestCompassTargetChangeEvent e) {
        if (e == null || e.getLocation() == null) return;

        String autoUpdate = CalebCompass.getConfigManager().getString("betonquest-compass-update");
        if (autoUpdate.equals("true")) {
            Player player = e.getPlayer();

            if (e.getLocation().getWorld() != player.getLocation().getWorld()) {
                player.sendMessage("§cЦель находится в другом мире!");
                return;
            }

            CompassInstance compass = CompassInstance.getInstance();
            if (compass == null) return;

            CompassLocation location = compass.getCompassLocation(player);
            if (location == null) {
                compass.addCompassLocation(player, player.getLocation(), e.getLocation());
                location = compass.getCompassLocation(player);
            }
            else {
                location.setOrigin(player.getLocation());
                location.setTarget(e.getLocation());
            }
            location.setTracking(true);
        }
    }
}