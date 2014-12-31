package org.skk.tide.intnl;

import org.skk.tide.Event;
import org.skk.tide.EventHandler;
import org.skk.tide.HandlerMethodNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class HandlerMethod {

    /**
     * strategy to get the right handler method invoker.
     *
     * EventHandler is stored as a weak reference, if it got GCed, we should not fail. Hence an EmptyHandlerMethod
     *
     * @param handler the handler instance
     * @param handlerMethod the method which handles the event
     * @param event the event that was raised
     * @return the right handler method invoker
     * @throws HandlerMethodNotFoundException when handler method takes more than one parameter
     */
    public static HandlerMethod get(EventHandler handler, Method handlerMethod, Event event) throws HandlerMethodNotFoundException {

        if (handler == null) {
            return new EmptyHandlerMethod();
        }

        if (handlerMethod.getParameterTypes().length == 0)
            return new NoParamHandlerMethod(handler, handlerMethod);

        if (handlerMethod.getParameterTypes().length == 1)
            return new SingleParamHandlerMethod(handler, handlerMethod, event.getEventData());

        throw new HandlerMethodNotFoundException(handler.getClass(), event.getClass());
    }

    public abstract void invoke() throws InvocationTargetException, IllegalAccessException;
}
