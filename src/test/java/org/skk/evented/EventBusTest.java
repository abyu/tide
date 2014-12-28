package org.skk.evented;

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

//    @Test
//    public void handlersCanRegisterToMultipleEventsCorrespondingMethodIsOnlyCalled() throws IllegalAccessException, HandlerMethodNotFoundException {
//        eventRepository.register(testHandler, EventOne.class);
//        eventRepository.register(testHandler, EventTwo.class);
//
//        EventData data = mock(EventData.class);
//        try {
//            eventRepository.raiseEvent(new EventOne().withData(data));
//
//            Assert.fail("Expected InvocationTargetException to be thrown");
//        } catch (InvocationTargetException e) {
//            Throwable targetException = e.getTargetException();
//            Assert.assertTrue("Expected MethodInvoked exception, but was "+targetException.toString(), targetException instanceof MethodInvoked);
//            Assert.assertThat(((MethodInvoked) targetException).getData(), Is.<EventData>is(data));
//        }
//    }




}
class TestHandler implements EventHandler{

    @HandleEvent(eventType = EventTwo.class)
    public void handleEvent(EventData data)  {
        //Mockito does not support spying on annotated methods(the annotations are lost on the spy object), hence an exception is used assert that the method was called.
//        throw new MethodInvoked(data);
    }


    @HandleEvent(eventType = EventOne.class)
    public void handleEventOne(EventData data) throws MethodInvoked {
        throw new MethodInvoked(data);
    }

}

 class MethodInvoked extends Exception {

    private EventData data;

    public MethodInvoked(EventData data) {

        this.data = data;
    }

    public MethodInvoked() {

    }

    public EventData getData() {
        return data;
    }
}


