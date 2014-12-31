package org.skk.tide;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation interface for use with handler methods, to mark them as event handler.
 *
 * If an object has registered for an event, the handler method for that event should be annotated with {@code @HandleEvent(eventType = eventClassName}
 *
 * This annotation is used to identify what method to call, when an event was raised.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HandleEvent {
    Class<? extends Event> eventType();
}
