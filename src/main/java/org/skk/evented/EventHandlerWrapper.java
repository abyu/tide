package org.skk.evented;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventHandlerWrapper {

    private final WeakReference<EventHandler> weakHandler;

    public EventHandlerWrapper(EventHandler handler) {
        weakHandler = new WeakReference<EventHandler>(handler);
    }

    public boolean hasHandler(EventHandler handler){
        EventHandler eventHandler = weakHandler.get();
        return eventHandler != null && eventHandler == handler;
    }

    public void invokeHandlerMethod(Event event) throws InvocationTargetException, IllegalAccessException, HandlerMethodNotFoundException {
        Method handlerMethod = getHandlerMethodFor(event.getClass());

        if(handlerMethod.getParameterTypes().length == 0)
            handlerMethod.invoke(weakHandler.get());
        else
            handlerMethod.invoke(weakHandler.get(), event.getEventData());
    }

    private Method getHandlerMethodFor(Class<? extends Event> eventClass) throws HandlerMethodNotFoundException{
        EventHandler handler = weakHandler.get();

        Method[] declaredMethods = handler.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (isHandleMethodFor(method, eventClass)) {
                return method;
            }
        }

        throw new HandlerMethodNotFoundException(handler.getClass(), eventClass);
    }


    private boolean isHandleMethodFor(Method method, Class event) {

        HandleEvent annotation = method.getAnnotation(HandleEvent.class);

        return annotation != null && annotation.eventType().equals(event);
    }
}