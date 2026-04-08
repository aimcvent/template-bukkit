package fr.aimcvent.template.sidebar;

import fr.aimcvent.bukkit.api.BukkitService;
import fr.aimcvent.bukkit.api.sidebar.SidebarLine;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.player.Player;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

@Inject
public class PlayersSidebarLine implements SidebarLine {

    private final TemplateService templateService;
    private final BukkitService bukkitService;

    private PlayersSidebarLine(TemplateService templateService, BukkitService bukkitService) {
        this.templateService = templateService;
        this.bukkitService = bukkitService;
    }

    @Override
    public String key() {
        return SidebarKey.PLAYERS;
    }

    @Override
    public Optional<String> apply(UUID playerId, int index) {
        final org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(playerId);
        if (bukkitPlayer == null) {
            return Optional.empty();
        }
        final Player player = this.templateService.players().of(bukkitPlayer);
        final int playerCount = this.templateService.players().count();
        return Optional.of(
            this.bukkitService.translations()
                .of(this.templateService, "sidebar.players")
                .add("players", String.valueOf(playerCount))
                .add("s", playerCount > 1 ? "s" : "")
                .translate(player.aimcvent().translation())
        );
    }
}
