package org.skk.evented;

import org.skk.evented.dimpl.TestHandler;
import org.skk.evented.intnl.EventHandlerWrappers;
import org.skk.evented.events.EventOne;
import org.skk.evented.events.EventTwo;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;

public class EventBusTest {

    @Mock
    private EventHandler eventHandler1;

    @Mock
    private EventHandler eventHandler2;

    private TestHandler testHandler = new TestHandler();

    private EventBus eventBus;

    @Before
    public void setUp(){
        initMocks(this);
        eventBus = new EventBus();
    }

    @Test
    public void aHandlerForAnEventIsRegistered() throws InvocationTargetException, IllegalAccessException {

        eventBus.register(eventHandler1, EventOne.class);

        EventHandlerWrappers handlers = eventBus.getHandlers(EventOne.class);

        Assert.assertThat(handlers.size(), Is.is(1));
    }

    @Test
    public void multipleHandlersForAnEventAreRegistered(){
        eventBus.register(eventHandler1, EventOne.class);
        eventBus.register(eventHandler2, EventOne.class);

        EventHandlerWrappers handlers = eventBus.getHandlers(EventOne.class);

        Assert.assertThat(handlers.size(), Is.is(2));
    }

    @Test
    public void onlyOneInstanceOfAHandlerIsRegisteredForAnEvent(){
        eventBus.register(eventHandler1, EventOne.class);
        eventBus.register(eventHandler1, EventOne.class);

        EventHandlerWrappers handlers = eventBus.getHandlers(EventOne.class);

        Assert.assertThat(handlers.size(), Is.is(1));
    }

    @Test
    public void emptyHandlerListIsReturnedWhenNoneRegistered(){

        EventHandlerWrappers handlers = eventBus.getHandlers(EventOne.class);

        Assert.assertTrue(handlers.isEmpty());
    }

    @Test
    public void methodsAnnotatedWithHandleEventInHandlersAreInvokedOnRaiseAnEvent() throws IllegalAccessException, HandlerMethodNotFoundException, InvocationTargetException {
        eventBus.register(testHandler, EventTwo.class);

        EventData data = mock(EventData.class);

        Event event = new EventTwo().withData(data);
        eventBus.raiseEvent(event);

        spy(testHandler).handleEvent(data);
    }
}


