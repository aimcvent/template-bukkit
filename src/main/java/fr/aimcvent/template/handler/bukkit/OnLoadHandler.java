package fr.aimcvent.template.handler.bukkit;

import fr.aimcvent.bukkit.api.event.BukkitOnLoadEvent;
import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;

@Inject
public class OnLoadHandler implements KernelListener {
    private final TemplateService templateService;

    public OnLoadHandler(TemplateService templateService) {
        this.templateService = templateService;
    }

    @KernelEventHandler
    private void on(BukkitOnLoadEvent event) {
        event.service().translations()
            .addParameter("prefix", event.service().translations().of(this.templateService, "prefix"))
            .addParameter("version", this.templateService.version());
    }
}
