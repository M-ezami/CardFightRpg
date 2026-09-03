package io.github.some_example_name.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.some_example_name.GdxGame;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.view.BoardView2;

/**
 * Visuals and input only. No game rules live here.
 * Player actions are forwarded to TurnDirector.
 * State for rendering is read directly from GameState.
 */
public class CombatScreen extends ScreenAdapter {

    private static final float MIN_WORLD_WIDTH = 1920f;
    private static final float MIN_WORLD_HEIGHT = 1080f;

    private final SpriteBatch batch;
    private final FitViewport viewport; // guarantees at least 1920x1080 visible, extends beyond that on mismatched aspect ratios
    private final BoardView2 boardView;
    private final Texture bgdTexture;
    private final Stage stage;

    public CombatScreen(GameState gameState, GdxGame game) {
        this.batch = game.getBatch();
        this.viewport = new FitViewport(MIN_WORLD_WIDTH, MIN_WORLD_HEIGHT, new OrthographicCamera());
        this.boardView = new BoardView2(game.getAssets(), gameState, viewport);
        this.bgdTexture = new Texture("background.png");
        this.stage = new Stage(viewport, batch);
        stage.addActor(boardView);
        stage.setDebugAll(true);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        boardView.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        boardView.setPosition(0, 0);
        boardView.invalidateHierarchy();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(bgdTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }
}
