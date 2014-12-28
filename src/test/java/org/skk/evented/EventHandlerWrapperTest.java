package org.skk.evented;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.skk.evented.events.EventOne;
import org.skk.evented.events.EventTwo;

import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.mock;

public class EventHandlerWrapperTest  {

    @Test
    public void returnsTrueWhenTheWrapperContainsTheHandler(){
        TestHandler handler = new TestHandler();
        EventHandlerWrapper wrapper = new EventHandlerWrapper(handler);

        Assert.assertThat(wrapper.hasHandler(handler), Is.is(true));
    }

    @Test(expected = HandlerMethodNotFoundException.class)
    public void handlerMethodNotFoundExceptionIsThrownWhenRegisteredHandlerDoesNotHandleTheEvent() throws IllegalAccessException, HandlerMethodNotFoundException, InvocationTargetException {
        BadHandler badHandler = new BadHandler();
        EventHandlerWrapper wrapper = new EventHandlerWrapper(badHandler);

        wrapper.invokeHandlerMethod(new EventOne());
    }
    @Test
    public void methodAnnotatedWithHandleEventInsideHandlerIsInvoked() throws IllegalAccessException, HandlerMethodNotFoundException {

        EventHandlerWrapper wrapper = new EventHandlerWrapper(new HandlerOne());

        try {
            wrapper.invokeHandlerMethod(new EventOne());

            Assert.fail("Expected InvocationTargetException to be thrown");
        }
        catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            Assert.assertTrue("Expected MethodInvoked exception, but was " + targetException.toString(), targetException instanceof MethodInvoked);
        }

    }

    @Test
    public void handlerMethodIsInvokedWithEventDataWhenIsTakesAnArgument() throws HandlerMethodNotFoundException, IllegalAccessException {

        EventHandlerWrapper wrapper = new EventHandlerWrapper(new HandlerOne());
        EventData eventData = mock(EventData.class);

        try {
            wrapper.invokeHandlerMethod(new EventTwo().withData(eventData));

            Assert.fail("Expected InvocationTargetException to be thrown");
        }
        catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            Assert.assertTrue("Expected MethodInvoked exception, but was " + targetException.toString(), targetException instanceof MethodInvoked);
            Assert.assertThat(((MethodInvoked) targetException).getData(), Is.is(eventData));

        }
    }

}

class BadHandler implements EventHandler{

    public void notAHandlerMethod(){

    }
}

class HandlerOne implements  EventHandler{

    @HandleEvent(eventType = EventOne.class)
    public void handleEventOne() throws MethodInvoked {
        throw new MethodInvoked();
    }

    @HandleEvent(eventType = EventTwo.class)
    public void handleEventTwo(EventData eventData) throws MethodInvoked{
        throw new MethodInvoked(eventData);
    }
}


