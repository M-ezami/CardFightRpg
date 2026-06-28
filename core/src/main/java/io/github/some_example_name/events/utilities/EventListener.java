package io.github.some_example_name.events.utilities;

public interface EventListener<T> {
    void onEvent(T event);

}
