package fr.aimcvent.template.inventory;

import fr.aimcvent.bukkit.api.BukkitService;
import fr.aimcvent.bukkit.api.inventory.Inventory;
import fr.aimcvent.bukkit.api.inventory.ReloadInventory;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.inventory.item.TeamItem;
import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.team.Team;
import fr.aimcvent.template.utils.Permissions;

import java.util.List;

public class TeamReloadInventory implements ReloadInventory<Inventory> {
    private final TemplateService templateService;
    private final BukkitService bukkitService;

    public TeamReloadInventory(BukkitService bukkitService, TemplateService templateService) {
        this.bukkitService = bukkitService;
        this.templateService = templateService;
    }

    @Override
    public void reload(Inventory inventory) {
        final Player player = this.templateService.players().of(inventory.player());
        final List<Team> teams = this.templateService.teams().all();

        if (player.aimcvent().hasPermission(Permissions.INVENTORY_TEAM_JOIN_SPECTATOR)) {
            teams.add(this.templateService.teams().spectator());
        }

        final int size = teams.size();
        final int increment = size < 5 ? 2 : 1;
        int slot = size < 5 ? 5 - size : size < 7 ? 2 : size == 7 ? 1 : 0;

        for (Team team : teams) {
            inventory.add(slot, new TeamItem(this.templateService, this.bukkitService, player, team));
            slot += increment;
        }
    }
}
