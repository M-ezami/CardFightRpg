package io.github.some_example_name.events;

public interface EventListener<T> {
    void onEvent(T event);

}
