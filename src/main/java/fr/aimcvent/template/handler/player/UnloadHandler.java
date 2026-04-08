package fr.aimcvent.template.handler.player;

import fr.aimcvent.bukkit.api.BukkitService;
import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.player.api.PlayerService;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.event.player.PlayerUnloadEvent;
import fr.aimcvent.template.sidebar.SidebarKey;

@Inject
public class UnloadHandler implements KernelListener {
    private final TemplateService templateService;
    private final BukkitService bukkitService;
    private final PlayerService playerService;

    public UnloadHandler(TemplateService templateService, BukkitService bukkitService, PlayerService playerService) {
        this.templateService = templateService;
        this.bukkitService = bukkitService;
        this.playerService = playerService;
    }

    @KernelEventHandler
    private void on(PlayerUnloadEvent event) {
        this.templateService.teams().leave(event.player());
        event.player()
            .bukkit()
            .ifPresent(player -> this.bukkitService.sidebars().destroy(player));
        this.playerService.unload(event.player().aimcvent(), false);
        this.bukkitService.sidebars().update(SidebarKey.PLAYERS);
    }
}
