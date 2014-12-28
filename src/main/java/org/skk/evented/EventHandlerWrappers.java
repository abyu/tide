package org.skk.evented;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class EventHandlerWrappers extends ArrayList<EventHandlerWrapper> {

    public boolean contains(EventHandler handler){
        for (int index = 0; index < size(); index++) {
            if(get(index).hasHandler(handler))
                return true;
        }

        return false;
    }

    public void invokeHandlerMethod(final Event event) throws IllegalAccessException, HandlerMethodNotFoundException, InvocationTargetException {

        for (int i = 0; i < size(); i++) {
            get(i).invokeHandlerMethod(event);
        }

    }
}