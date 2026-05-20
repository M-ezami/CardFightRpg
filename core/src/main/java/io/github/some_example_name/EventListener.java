package io.github.some_example_name;

public interface EventListener<T> {
    void onEvent(T event);
}
