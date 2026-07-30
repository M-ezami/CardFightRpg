package io.github.some_example_name.events.utilities;

@FunctionalInterface
public interface EventListener<T> {
    void onEvent(T event);

}
