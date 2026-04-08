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
public class FooterSidebarLine implements SidebarLine {

    private final TemplateService templateService;
    private final BukkitService bukkitService;

    private FooterSidebarLine(TemplateService templateService, BukkitService bukkitService) {
        this.templateService = templateService;
        this.bukkitService = bukkitService;
    }

    @Override
    public String key() {
        return SidebarKey.FOOTER;
    }

    @Override
    public Optional<String> apply(UUID playerId, int index) {
        final org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(playerId);
        if (bukkitPlayer == null) {
            return Optional.empty();
        }
        final Player player = this.templateService.players().of(bukkitPlayer);
        return Optional.of(
            this.bukkitService.translations()
                .of(this.templateService, "sidebar.footer")
                .translate(player.aimcvent().translation())
        );
    }
}
