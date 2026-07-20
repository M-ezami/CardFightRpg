package io.github.some_example_name.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.GdxGame;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.input.InputRouter;
import io.github.some_example_name.ui.Hud;
import io.github.some_example_name.view.BoardView;

/**
 * Visuals and input only. No game rules live here.
 * Player actions are forwarded to TurnDirector.
 * State for rendering is read directly from GameState.
 */
public class CombatScreen extends ScreenAdapter {

    private final SpriteBatch batch;
    private final ExtendViewport viewport;
    private final ScreenViewport uiViewport;
    private final ShapeRenderer shapeRenderer;
    private final Hud hud;
    private final BoardView boardView;
    private final InputRouter inputRouter;

    // this can be further decoupled it shouldnt know about targets

    public CombatScreen(GameState gameState, GdxGame game) {
        this.batch = game.getBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.uiViewport = new ScreenViewport();
        this.viewport = new ExtendViewport(16f, 9f);
        this.boardView = new BoardView(this.viewport, game, gameState);
        this.hud = new Hud(game.getAssets(), gameState, boardView, uiViewport);
        this.inputRouter = new InputRouter(viewport, boardView, hud, gameState);
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
        viewport.update(width, height, true);
        boardView.rebuild();
    }



    public void update(float delta) {
        boardView.update();
        inputRouter.update();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        viewport.apply();
        viewport.getCamera().update();

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        boardView.debugDraw(shapeRenderer);
        shapeRenderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        update(delta);
        batch.begin();
        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        drawWorld(delta);
        batch.end();
        uiViewport.apply();
        batch.setProjectionMatrix(uiViewport.getCamera().combined);
        hud.draw(batch, delta);


    }

    private void drawWorld(float delta) {
        batch.setColor(Color.WHITE);
        boardView.draw(batch, delta);
    }


}
