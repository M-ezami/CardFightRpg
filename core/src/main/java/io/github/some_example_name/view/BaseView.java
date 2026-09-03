package io.github.some_example_name.view;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.some_example_name.ui.Assets;


//not entirely sure about this abstraction but who cares
public abstract class BaseView extends Table {
    protected Assets assets;

    public BaseView(Assets assets) {
        this.assets = assets;
        createUI();
    }
    public BaseView() {
    }
    /**
     * Every view must implement a way to refresh its display
     * when the underlying model data changes.
     * It also must implement a way to create itself which gets called in the constructor here
     */

    public abstract void createUI();
    public abstract void refresh();
}
