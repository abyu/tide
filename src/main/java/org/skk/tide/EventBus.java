package org.skk.tide;

import org.skk.tide.intnl.EventHandlerWrapper;
import org.skk.tide.intnl.EventHandlerWrappers;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event orchestrator which allows to register and raise an event.
 *
 * If using a Dependency injection framework, mark instances of EventBus to singleton, if not use {@code getInstance()} method.
 * Do not create objects yourself, it will not work, if it is not the same object across your application.
 *
 * The constructor is provided in order to be able to test it.
 *
 * @author Kishore
 */

public class EventBus {

    private static final EventBus instance = new EventBus();
    private final ConcurrentHashMap<Class<? extends Event>, EventHandlerWrappers> allHandlers;

    /**
     * It is not recommended to use the constructor directly, use {@code getInstance()} or mark it as singleton in case a DI framework is used.
     */
    public EventBus() {
        allHandlers = new ConcurrentHashMap<Class<? extends Event>, EventHandlerWrappers>();
    }

    /**
     *  static Factory method to get an instance of EventBus, always use this to raise or register to an event.
     *
     *  @return a singleton EventBus instance
     */
    public static EventBus getInstance() {
        return instance;
    }


    /**
     * Use to register to an event of type Event, with the handler class.
     * A handler class can register to multiple events, handle each event by annotating with {@link org.skk.tide.HandleEvent}
     *
     * @param handler The handler instance that handles the event, it has be of type {@link org.skk.tide.EventHandler}
     *
     * @param eventType The class name of the Event, which handler wants to handled, the events need inherit from {@link org.skk.tide.Event}
     *
     */
    public void register(EventHandler handler, Class<? extends Event> eventType) {

        EventHandlerWrappers handlers = emptyIfNull(allHandlers.get(eventType));

        if (!handlers.contains(handler)) {
            handlers.add(new EventHandlerWrapper(handler));
        }

        allHandlers.put(eventType, handlers);
    }

    /**
     * Use to raise an event, passing in the event object.
     * The event object needs to contain any data the handler requires.
     * @see org.skk.tide.Event
     *
     * @param event An instance of the required event along with any data.
     * @throws InvocationTargetException when the handler method throws any exception
     * @throws IllegalAccessException when the handler method is not public
     * @throws HandlerMethodNotFoundException when any registered handler does not handle the specified event it registered to.
     */
    public void raiseEvent(Event event) throws InvocationTargetException, IllegalAccessException, HandlerMethodNotFoundException {

        EventHandlerWrappers weakReferencedEventHandlers = allHandlers.get(event.getClass());

        weakReferencedEventHandlers.invokeHandlerMethod(event);
    }

    /**
     * Returns a List of all Handlers registered to the given EventType.
     * You don't have to use this, this method was created for testing purposes.
     *
     * @param eventType the class name of required event. The type should inherit from Event
     *                  @see org.skk.tide.Event
     * @return EventHandlerWrappers, a collection class
     * @see org.skk.tide.intnl.EventHandlerWrappers
     */
    public EventHandlerWrappers getHandlers(Class<? extends Event> eventType) {

        return emptyIfNull(allHandlers.get(eventType));
    }

    private EventHandlerWrappers emptyIfNull(EventHandlerWrappers list) {
        return list != null ? list : new EventHandlerWrappers();
    }
}