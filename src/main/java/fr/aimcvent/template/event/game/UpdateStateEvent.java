package fr.aimcvent.template.event.game;

import fr.aimcvent.kernel.api.event.Event;
import fr.aimcvent.template.utils.State;

public class UpdateStateEvent implements Event {
    private final State state;

    public UpdateStateEvent(State state) {
        this.state = state;
    }

    public State state() {
        return this.state;
    }
}
