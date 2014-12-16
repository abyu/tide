package com.skk.evented;

public abstract class Event {

    private EventData eventData;

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
