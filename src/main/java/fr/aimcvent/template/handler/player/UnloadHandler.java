package fr.aimcvent.template.handler.player;

import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.event.player.PlayerUnloadEvent;

@Inject
public class UnloadHandler implements KernelListener {
    private final TemplateService templateService;

    public UnloadHandler(TemplateService templateService) {
        this.templateService = templateService;
    }

    @KernelEventHandler
    private void on(PlayerUnloadEvent event) {
        this.templateService.teams().leave(event.player());
    }
}
