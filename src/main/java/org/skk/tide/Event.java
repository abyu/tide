package org.skk.tide;

import org.skk.tide.intnl.EmptyData;

public abstract class Event {

    private EventData eventData;

    protected Event() {
        eventData = new EmptyData();
    }

    public Event withData(EventData data) {
        eventData = data;

        return this;
    }

    public EventData getEventData() {
        return eventData;
    }
}