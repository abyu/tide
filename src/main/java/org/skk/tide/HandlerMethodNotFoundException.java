package org.skk.tide;


/**
 * The runtime exception thrown when a registered handler does handle the specified event.
 *
 * When a handler registers for an event, the handle method needs to be annotated with {@link org.skk.tide.HandleEvent}.
 * If it does not, {@link org.skk.tide.HandlerMethodNotFoundException} is thrown at runtime
 */
public class HandlerMethodNotFoundException extends Exception {
    private final Class<? extends EventHandler> handlerClass;
    private final Class<? extends Event> eventClass;

    public HandlerMethodNotFoundException(Class<? extends EventHandler> handlerClass, Class<? extends Event> eventClass) {

        this.handlerClass = handlerClass;
        this.eventClass = eventClass;
    }

    @Override
    public String getMessage() {
        return "No handler method found for event " + eventClass + " on handler " + handlerClass + ". Make sure the method is annotated with @HandleEvent passing the event class and it takes only one or no arguments";
    }
}
