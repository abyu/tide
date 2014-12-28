package org.skk.evented.intnl;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.skk.evented.EventData;
import org.skk.evented.HandlerMethodNotFoundException;
import org.skk.evented.dimpl.BadHandler;
import org.skk.evented.dimpl.HandlerOne;
import org.skk.evented.dimpl.MethodInvoked;
import org.skk.evented.dimpl.TestHandler;
import org.skk.evented.intnl.EventHandlerWrapper;
import org.skk.evented.events.EventOne;
import org.skk.evented.events.EventTwo;

import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.mock;

public class EventHandlerWrapperTest  {

    @Test
    public void returnsTrueWhenTheWrapperContainsTheHandler(){
        TestHandler handler = new TestHandler();
        EventHandlerWrapper wrapper = new EventHandlerWrapper(handler);

        Assert.assertThat(wrapper.containsHandler(handler), Is.is(true));
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
    public void handlerMethodIsInvokedWithEventDataWhenItTakesAnArgument() throws HandlerMethodNotFoundException, IllegalAccessException {

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

    @Test
    public void wrapperWithNoHandlerInstanceResultsInNoOpWhileHandleInvoke()  {

        EventHandlerWrapper badWrapper = new EventHandlerWrapper(null);

        try {
            badWrapper.invokeHandlerMethod(new EventOne());
        } catch (Exception e) {
            Assert.fail("Expected no exception to be thrown, but " + e + " happened");
        }
    }

}


