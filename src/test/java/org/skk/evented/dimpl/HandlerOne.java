package org.skk.evented.dimpl;

import org.skk.evented.EventData;
import org.skk.evented.EventHandler;
import org.skk.evented.HandleEvent;
import org.skk.evented.dimpl.MethodInvoked;
import org.skk.evented.events.EventOne;
import org.skk.evented.events.EventTwo;

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
