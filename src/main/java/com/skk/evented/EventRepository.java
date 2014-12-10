package com.skk.evented;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public final class EventRepository {

    private final ConcurrentHashMap<String, ArrayList<WeakReference<EventHandler>>> events;
    private static EventRepository instance = new EventRepository();

    public EventRepository() {
        events = new ConcurrentHashMap<String, ArrayList<WeakReference<EventHandler>>>();
    }

    public static EventRepository getInstance() {
        return instance;
    }

    public void register(EventHandler handler, Event eventName) {

        ArrayList<WeakReference<EventHandler>> eventHandlers = emptyIfNull(events.get(eventName));

        if (!getInstances(eventHandlers).contains(handler))
            eventHandlers.add(new WeakReference<EventHandler>(handler));

        events.put(eventName.getId(), eventHandlers);
    }

    public void raiseEvent(Event event) throws InvocationTargetException, IllegalAccessException {
        ArrayList<EventHandler> handlers = getHandlers(event);
        for (EventHandler handler : handlers) {
            Method method = getMethod(handler, event);
            if (method != null) {
                if(method.getParameterTypes().length == 0){
                    method.invoke(handler);
                }else {
                    method.invoke(handler, event.getEventData());
                }
            }
        }
    }

    public ArrayList<EventHandler> getHandlers(Event event) {
        ArrayList<WeakReference<EventHandler>> weakHandlers = events.get(event.getId());

        return getInstances(weakHandlers);
    }

    private <T> ArrayList<T> emptyIfNull(ArrayList<T> list) {
        return list != null ? list : new ArrayList<T>();
    }

    private <T> ArrayList<T> getInstances(ArrayList<WeakReference<T>> weakReferences) {
        ArrayList<T> instances = new ArrayList<T>();

        for (WeakReference<T> weakReference : emptyIfNull(weakReferences)) {
            T instance = weakReference.get();
            if (instance != null) {
                instances.add(instance);
            }
        }

        return instances;
    }

    private Method getMethod(EventHandler handler, Event event) {
        Method[] declaredMethods = handler.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (isHandleMethodFor(method, event)) {
                return method;
            }
        }

        return null;
    }

    private boolean isHandleMethodFor(Method method, Event event) {

        HandleEvent annotation = method.getAnnotation(HandleEvent.class);

        return annotation != null && annotation.eventType().equals(event.getClass());
    }

}


