package org.skk.tide.intnl;

import org.skk.tide.EventData;
import org.skk.tide.EventHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleParamHandlerMethod extends HandlerMethod {

    private final EventHandler handler;
    private final EventData eventData;
    private Method handlerMethod;

    public SingleParamHandlerMethod(EventHandler handler, Method handlerMethod, EventData eventData) {

        this.handler = handler;
        this.handlerMethod = handlerMethod;
        this.eventData = eventData;
    }

    @Override
    public void invoke() throws InvocationTargetException, IllegalAccessException {
        handlerMethod.invoke(handler, eventData);
    }
}
