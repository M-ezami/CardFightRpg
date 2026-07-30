package io.github.some_example_name.events.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    private final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

    public static EventBus instance;

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

        // subscribe takes basically a eventclass as key and the value is a list of listeners
    public <T> void subscribe(Class<T> eventType, EventListener<T> listener) {
        listeners.computeIfAbsent((Class<?>) eventType, k -> new ArrayList<>())
            .add(listener);
    }
        // remove a specified listener from the list of the event class key
    public <T> void unsubscribe(Class<T> eventType, EventListener<T> listener) {
        listeners.get(eventType).remove(listener);
    }

    //whenever emit is callaed we call the stored lamdba methods all at once for that key
    @SuppressWarnings("unchecked")
    public <T> void emit(T event) {
        Class<?> eventType = event.getClass();

        List<EventListener<?>> eventListeners = listeners.get(eventType);

        if (eventListeners == null) {
            return;
        }

        List<EventListener<?>> copy = new ArrayList<>(eventListeners);

        for (EventListener<?> listener : copy) {
            ((EventListener<T>) listener).onEvent(event);
        }
    }
}
