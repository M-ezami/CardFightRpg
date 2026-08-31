package io.github.some_example_name.view;

import java.util.List;

public abstract class ContainerView<T extends BaseView> extends BaseView {
    List<T> views;

    public ContainerView(List<T> views) {
        this.views = views;

    }

    @Override
    public void createUI() {
        left();
        for (T view : views)
            this.add(view).spaceRight(10);
    }
}


