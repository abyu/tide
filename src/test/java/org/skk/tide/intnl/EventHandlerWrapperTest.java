package org.skk.tide.intnl;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.skk.tide.EventData;
import org.skk.tide.HandlerMethodNotFoundException;
import org.skk.tide.dimpl.BadHandler;
import org.skk.tide.dimpl.HandlerOne;
import org.skk.tide.dimpl.MethodInvoked;
import org.skk.tide.dimpl.TestHandler;
import org.skk.tide.events.EventOne;
import org.skk.tide.events.EventTwo;

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


