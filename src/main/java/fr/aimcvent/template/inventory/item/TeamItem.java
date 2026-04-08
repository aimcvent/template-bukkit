package fr.aimcvent.template.inventory.item;

import fr.aimcvent.bukkit.api.BukkitService;
import fr.aimcvent.bukkit.api.inventory.Inventory;
import fr.aimcvent.bukkit.api.translation.Translator;
import fr.aimcvent.kernel.api.translation.Translation;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.team.Team;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public class TeamItem extends AbstractSoundItem {
    private final TemplateService templateService;
    private final BukkitService bukkitService;
    private final Player player;
    private final Team team;

    public TeamItem(TemplateService templateService, BukkitService bukkitService, Player player, Team team) {
        this.templateService = templateService;
        this.bukkitService = bukkitService;
        this.player = player;
        this.team = team;
    }

    @Override
    public ItemStack icon() {
        final Translator translator = this.bukkitService.translations()
            .of(this.templateService, "inventory.team.lore");
        final Translation translation = this.player.aimcvent().translation();

        return this.bukkitService.items()
            .of(this.team.icon().getType(), 1, this.team.icon().getDurability())
            .name(this.team.name())
            .lore(
                this.team.players().stream()
                    .map(p -> translator.add("player", p.name()).translate(translation))
                    .collect(Collectors.toList())
            )
            .build();
    }

    @Override
    public boolean onClick(Inventory inventory, ClickType clickType) {
        final String path;
        if (clickType.isLeftClick()) {
            if (this.templateService.teams().join(this.team, this.player)) {
                inventory.player().closeInventory();
                this.bukkitService.hotbars().update(inventory.player());
                return true;
            }
            path = "inventory.team.join.error";
        } else {
            path = this.team.equals(this.player.team().of())
                    && this.templateService.teams().leave(this.player)
                ? null
                : "inventory.team.leave.error";
            inventory.update();
        }

        if (path != null) {
            this.bukkitService.translations()
                .of(this.templateService, path)
                .add("team", this.team.displayName())
                .send(inventory.player(), this.player.aimcvent().translation());
        }

        this.bukkitService.hotbars().update(inventory.player());
        return path == null;
    }
}
