package org.skk.evented.dimpl;

import org.skk.evented.EventData;
import org.skk.evented.EventHandler;
import org.skk.evented.HandleEvent;
import org.skk.evented.events.EventOne;
import org.skk.evented.events.EventThree;
import org.skk.evented.events.EventTwo;

public class TestHandler implements EventHandler {

    @HandleEvent(eventType = EventTwo.class)
    public void handleEvent(EventData data)  {
    }

    @HandleEvent(eventType = EventOne.class)
    public void handleEventOne(EventData data) throws MethodInvoked {
//Mockito does not support spying on annotated methods(the annotations are lost on the spy object), hence an exception is used assert that the method was called.
        throw new MethodInvoked(data);
    }

    @HandleEvent(eventType = EventThree.class)
    public void noParam() throws MethodInvoked {

    }

}
