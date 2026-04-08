package fr.aimcvent.template.handler.player;

import fr.aimcvent.bukkit.api.BukkitService;
import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.event.player.PlayerLoadEvent;
import fr.aimcvent.template.sidebar.SidebarKey;

@Inject
public class LoadHandler implements KernelListener {
    private final BukkitService bukkitService;

    public LoadHandler(BukkitService bukkitService) {
        this.bukkitService = bukkitService;
    }

    @KernelEventHandler
    private void on(PlayerLoadEvent event) {
        this.bukkitService.sidebars().update(SidebarKey.PLAYERS);
    }
}
