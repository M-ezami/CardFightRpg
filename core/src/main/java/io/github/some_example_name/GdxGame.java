package io.github.some_example_name;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.screens.GameScreen;
import io.github.some_example_name.ui.Assets;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class GdxGame extends Game {
    private SpriteBatch batch;
    private Assets assets;
    private EventBus eventBus;


    @Override
    public void create() {
        this.eventBus = new EventBus();
        batch = new SpriteBatch();
        this.assets = new Assets();
        assets.load();
        this.setScreen(new GameScreen(this));
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Assets getAssets() {
        return assets;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
