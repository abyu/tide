package org.skk.evented;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleParamHandlerMethod extends HandlerMethod{

    private final EventHandler handler;
    private Method handlerMethod;
    private final EventData eventData;

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
