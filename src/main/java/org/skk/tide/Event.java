package org.skk.tide;

import org.skk.tide.intnl.EmptyData;

/**
 * Base type for all custom events, inherit from this class to create a new event.
 *
 * EventBus understands only events that {@code extends Event}
 */
public abstract class Event {

    private EventData eventData;

    protected Event() {
        eventData = new EmptyData();
    }

    /**
     * When raising an event, if the handler requires any data, pass them using this method.
     *
     * The data must of type {@code implements} {@link org.skk.tide.EventData}.
     *
     * @param data The data the handler requires
     * @return the same event object.
     */
    public Event withData(EventData data) {
        eventData = data;

        return this;
    }


    /**
     * getter for event data.
     * @return data that was set using withData, if not {@link org.skk.tide.intnl.EmptyData}
     */
    public EventData getEventData() {
        return eventData;
    }
}
