package fr.aimcvent.template.listener.player;

import fr.aimcvent.bukkit.api.BukkitService;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.kernel.api.translation.Translation;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.utils.State;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;

@Inject
public class JoinListener implements Listener {
    private final TemplateService templateService;
    private final BukkitService bukkitService;

    public JoinListener(TemplateService templateService, BukkitService bukkitService) {
        this.templateService = templateService;
        this.bukkitService = bukkitService;
    }

    @EventHandler
    private void on(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        final Player player = this.templateService.players().of(event.getPlayer());
        final Translation translation = player.aimcvent().translation();

        this.updatePlayerList(event.getPlayer(), translation);

        if (this.templateService.informations().state().equals(State.LOBBY)) {
            this.templateService.players().reset(event.getPlayer(), GameMode.ADVENTURE);
            this.templateService.locations().spawn().teleport(event.getPlayer());
        }

        this.bukkitService.translations()
            .of(this.templateService, "event.player.join")
            .add("player", player.displayName())
            .broadcast(this.templateService.players().translations());
    }

    private void updatePlayerList(org.bukkit.entity.Player bukkitPlayer, Translation translation) {
        try {
            final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            this.setFieldToPacket(
                packet,
                "a",
                this.bukkitService.translations()
                    .of(this.templateService, "player_list.header")
                    .translate(translation)
            );
            this.setFieldToPacket(
                packet,
                "b",
                this.bukkitService.translations()
                    .of(this.templateService, "player_list.footer")
                    .translate(translation)
            );
            ((CraftPlayer) bukkitPlayer).getHandle().playerConnection.sendPacket(packet);
        } catch (Exception exception) {
            this.templateService.logger().error(exception.getMessage(), exception);
        }
    }

    private void setFieldToPacket(Packet<?> packet, String fieldName, String value) throws Exception {
        final Field field = packet.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(packet, new ChatComponentText(value));
        field.setAccessible(false);
    }
}
