package io.github.some_example_name.view;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.some_example_name.ui.Assets;

public abstract class BaseView extends Table {
    protected Assets assets;

    public BaseView(Assets assets) {
        this.assets = assets;
        createUI();
    }
    public BaseView(){}

    /**
     * Every view must implement a way to refresh its display
     * when the underlying model data changes.
     */
    public abstract void refresh();

    public abstract void createUI();
}
