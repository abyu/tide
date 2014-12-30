package org.skk.tide.intnl;

import org.skk.tide.Event;
import org.skk.tide.EventHandler;
import org.skk.tide.HandlerMethodNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class HandlerMethod {

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
