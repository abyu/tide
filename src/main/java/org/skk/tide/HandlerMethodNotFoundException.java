package org.skk.tide;

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
