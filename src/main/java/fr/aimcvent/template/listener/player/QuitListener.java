package fr.aimcvent.template.listener.player;

import fr.aimcvent.bukkit.api.BukkitService;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.player.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Inject
public class QuitListener implements Listener {
    private final TemplateService templateService;
    private final BukkitService bukkitService;

    public QuitListener(TemplateService templateService, BukkitService bukkitService) {
        this.templateService = templateService;
        this.bukkitService = bukkitService;
    }

    @EventHandler
    private void on(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        final Player player = this.templateService.players().of(event.getPlayer());
        this.templateService.players().remove(player);

        this.bukkitService.translations()
            .of(this.templateService, "event.player.quit")
            .add("player", player.displayName())
            .broadcast(this.templateService.players().translations());
    }
}
