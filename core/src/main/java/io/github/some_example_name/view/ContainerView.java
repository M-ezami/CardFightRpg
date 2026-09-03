package io.github.some_example_name.view;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ContainerView<T extends BaseView> extends BaseView {
    List<T> views;

    public ContainerView(List<T> views) {
        this.views = views;
        createUI();
    }

    @Override
    public void createUI() {
        left();
        for (T view : views)
            this.add(view).spaceRight(30);
    }

    protected static <S, V extends BaseView> List<V> createViews(List<S> sources, Function<S, V> factory) {
        return sources.stream()
            .map(factory)
            .collect(Collectors.toList());
    }



}
