package org.skk.evented;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public final class EventRepository {

    private final ConcurrentHashMap<Class, EventHandlerWrappers> allHandlers;

    private static EventRepository instance = new EventRepository();

    public EventRepository() {
        allHandlers = new ConcurrentHashMap<Class, EventHandlerWrappers>();
    }

    public static EventRepository getInstance() {
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