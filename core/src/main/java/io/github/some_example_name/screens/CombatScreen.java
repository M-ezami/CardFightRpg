package io.github.some_example_name.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.some_example_name.GdxGame;
import io.github.some_example_name.InputRouter;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.events.CardPlayedEvent;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.events.PlayerTurnReadyEvent;
import io.github.some_example_name.events.PlayerTurnStartEvent;
import io.github.some_example_name.ui.BoardView;
import io.github.some_example_name.ui.Hud;

/**
 * Visuals and input only. No game rules live here.
 * Player actions are forwarded to TurnDirector.
 * State for rendering is read directly from GameState.
 */
public class CombatScreen extends ScreenAdapter {

    private final SpriteBatch batch;
    private final ExtendViewport viewport;
    private final ShapeRenderer shapeRenderer;
    private final EventBus eventBus;
    private final Hud hud;
    private final BoardView boardView;
    private final InputRouter inputRouter;
    // this can be further decoupled it shouldnt know about targets

    public CombatScreen(GameState gameState, GdxGame game, EventBus eventBus) {
        this.eventBus = EventBus.getInstance();
        this.batch = game.getBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.viewport = new ExtendViewport(16f, 9f);
        this.boardView = new BoardView(this.viewport, game, gameState);
        this.hud = new Hud(game.getAssets(), gameState, eventBus);

        subscribe();
        this.inputRouter = new InputRouter(viewport, boardView);
        hud.setupHud();

    }

    @Override
    public void resize(int width, int height) {
        hud.getUiViewport().update(width, height, true);
        viewport.update(width, height, true);
        boardView.rebuild();
    }



    public void subscribe() {
        eventBus.subscribe(PlayerTurnReadyEvent.class, e -> onPlayerTurnReady());
        eventBus.subscribe(MonsterPlayedEvent.class, e -> updateMonsterField());
    }

    public void updateMonsterField() {
        boardView.onUpdateMonsterField();
    }

    public void onPlayerTurnReady() {
        hud.hideBanner();
    }

    public void update(float delta) {
        boardView.updateHand();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        viewport.getCamera().update();

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        boardView.debugDraw(shapeRenderer);
        shapeRenderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        update(delta);
        batch.begin();
        drawWorld(delta);
        batch.end();

        hud.draw(batch, delta);
    }

    private void drawWorld(float delta) {
        batch.setColor(Color.WHITE);
        boardView.draw(batch, delta);
    }


}
