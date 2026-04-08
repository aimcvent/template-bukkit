package fr.aimcvent.template.hotbar;

import fr.aimcvent.bukkit.api.BukkitService;
import fr.aimcvent.bukkit.api.hotbar.Item;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.team.Team;
import fr.aimcvent.template.utils.State;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TeamItem implements Item {
    private final TemplateService templateService;
    private final BukkitService bukkitService;
    private final Player player;

    public TeamItem(TemplateService templateService, BukkitService bukkitService, Player player) {
        this.templateService = templateService;
        this.bukkitService = bukkitService;
        this.player = player;
    }

    @Override
    public ItemStack icon() {
        final Team team = this.player.team().of();
        return this.bukkitService.items()
            .of(team != null ? team.icon().getType() : Material.WOOL)
            .durability(team != null ? team.icon().getDurability() : 0)
            .name(
                this.bukkitService.translations()
                    .of(this.templateService, "inventory.team.title")
                    .translate(this.player.aimcvent().translation())
            )
            .build();
    }

    @Override
    public void interact(org.bukkit.entity.Player player) {
        if (this.templateService.informations().state().equals(State.LOBBY)) {
            player.performCommand("team open");
        }
    }
}
