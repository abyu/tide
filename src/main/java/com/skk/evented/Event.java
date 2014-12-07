package com.skk.evented;

import java.lang.reflect.InvocationTargetException;

public abstract class Event {

    private EventData eventData;

    public abstract String getId();

    protected Event(){
        eventData = new EmptyData();
    }



    public Event withData(EventData data){
        eventData = data;

        return this;
    }

    public EventData getEventData() {
        return eventData;
    }
}
