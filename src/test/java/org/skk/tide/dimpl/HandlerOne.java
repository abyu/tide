package org.skk.tide.dimpl;

import org.skk.tide.EventData;
import org.skk.tide.EventHandler;
import org.skk.tide.HandleEvent;
import org.skk.tide.events.EventOne;
import org.skk.tide.events.EventTwo;

public class HandlerOne implements EventHandler {

    @HandleEvent(eventType = EventOne.class)
    public void handleEventOne() throws MethodInvoked {
        throw new MethodInvoked();
    }

    @HandleEvent(eventType = EventTwo.class)
    public void handleEventTwo(EventData eventData) throws MethodInvoked{
        throw new MethodInvoked(eventData);
    }
}
