package org.skk.evented.intnl;

import org.skk.evented.Event;
import org.skk.evented.EventHandler;
import org.skk.evented.HandlerMethodNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class HandlerMethod{

    public abstract void invoke() throws InvocationTargetException, IllegalAccessException;

    public static HandlerMethod get(EventHandler handler, Method handlerMethod, Event event) throws HandlerMethodNotFoundException {
        if(handler == null) {
            return new EmptyHandlerMethod();
        }

        if(handlerMethod.getParameterTypes().length == 0)
            return new NoParamHandlerMethod(handler, handlerMethod);

        if(handlerMethod.getParameterTypes().length == 1)
            return new SingleParamHandlerMethod(handler, handlerMethod, event.getEventData());

        throw new HandlerMethodNotFoundException(handler.getClass(), event.getClass());
    }
}
