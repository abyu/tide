package com.skk.evented.events;

import com.skk.evented.Event;

/**
 * Created by kishorek on 12/7/14.
 */
public class EventOne extends Event {
    @Override
    public String getId() {
        return "EventOne";
    }

    public EventOne() {
        super();
    }
}
