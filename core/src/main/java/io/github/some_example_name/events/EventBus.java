package io.github.some_example_name.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    private final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();
    public static EventBus instance;

    public <T> void subscribe(Class<T> eventType, EventListener<T> listener) {
        listeners.computeIfAbsent((Class<?>) eventType, k -> new ArrayList<>())
            .add(listener);
    }

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }

        return instance;
    }


    @SuppressWarnings("unchecked")
    public <T> void emit(T event) {
        Class<?> eventType = event.getClass();

        List<EventListener<?>> eventListeners = listeners.get(eventType);

        if (eventListeners == null) return;

        for (EventListener<?> listener : eventListeners) {
            System.out.println("Event: " + eventType + ", Listener: " + listener.getClass().getName());
            ((EventListener<T>) listener).onEvent(event);
        }
    }
}
