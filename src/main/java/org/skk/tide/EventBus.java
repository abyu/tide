package org.skk.tide;

import org.skk.tide.intnl.EventHandlerWrapper;
import org.skk.tide.intnl.EventHandlerWrappers;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public class EventBus {

    private static final EventBus instance = new EventBus();
    private final ConcurrentHashMap<Class, EventHandlerWrappers> allHandlers;

    public EventBus() {
        allHandlers = new ConcurrentHashMap<Class, EventHandlerWrappers>();
    }

    public static EventBus getInstance() {
        return instance;
    }

    public void register(EventHandler handler, Class eventType) {

        EventHandlerWrappers handlers = emptyIfNull(allHandlers.get(eventType));

        if (!handlers.contains(handler)) {
            handlers.add(new EventHandlerWrapper(handler));
        }

        allHandlers.put(eventType, handlers);
    }

    public void raiseEvent(Event event) throws InvocationTargetException, IllegalAccessException, HandlerMethodNotFoundException {

        EventHandlerWrappers weakReferencedEventHandlers = allHandlers.get(event.getClass());

        weakReferencedEventHandlers.invokeHandlerMethod(event);
    }

    public EventHandlerWrappers getHandlers(Class eventType) {

        return emptyIfNull(allHandlers.get(eventType));
    }

    private EventHandlerWrappers emptyIfNull(EventHandlerWrappers list) {
        return list != null ? list : new EventHandlerWrappers();
    }
}