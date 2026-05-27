package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.GdxGame;
import io.github.some_example_name.TurnDirector;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.entiteRelated.Targatable;
import io.github.some_example_name.events.CardPlayedEvent;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.events.EventListener;
import io.github.some_example_name.events.PlayerTurnReadyEvent;
import io.github.some_example_name.ui.Assets;
import io.github.some_example_name.ui.CardView;
import io.github.some_example_name.ui.HandView;
import io.github.some_example_name.ui.Hud;

import java.util.List;

/**
 * Visuals and input only. No game rules live here.
 * Player actions are forwarded to TurnDirector.
 * State for rendering is read directly from GameState.
 * <p>
 * Depends on: TurnDirector (actions), GameState (read-only rendering)
 */
public class CombatScreen extends ScreenAdapter implements InputProcessor {

    private final SpriteBatch batch;
    private final Viewport viewport;

    private final HandView handView;
    private final GameState gameState;
    private final List<Opponent> opponentList;
    private final Vector3 touchPos = new Vector3();
    private final EventBus eventBus;
    private final Player player;


    private static final float ENEMY_X = 11.5f;

    private static final float ENEMY_Y = 6.5f;


    private TurnDirector turnDirector;


    private InputMultiplexer multiplexer;
    private CardView selectedCard = null;

    private final Hud hud;

    public CombatScreen(GameState gameState, GdxGame game, EventBus eventBus) {
        Assets assets = game.getAssets();
        this.eventBus = eventBus;
        this.gameState = gameState;
        this.player = gameState.getPlayer();
        this.batch = game.getBatch();
        this.viewport = new ExtendViewport(16f, 9f);
        this.handView = new HandView(assets);
        this.opponentList = gameState.getOpponents();

        this.hud = new Hud(assets, gameState, eventBus);
        subscribe();
        hud.setupHud(() -> turnDirector.onPlayerEndTurn());
    }

    public void setTurnDirector(TurnDirector turnDirector) {
        this.turnDirector = turnDirector;
    }

    public void subscribe() {
        eventBus.subscribe(CardPlayedEvent.class, new EventListener<CardPlayedEvent>() {
            @Override
            public void onEvent(CardPlayedEvent event) {
                refreshHand();
            }
        });
        eventBus.subscribe(PlayerTurnReadyEvent.class, event -> {
            onPlayerTurnReady();
        });

    }

    public void onPlayerTurnReady() {
        refreshHand();
    }

    // ---- Visual helpers ----

    public void refreshHand() {
        handView.update(gameState.getDeckState().getHand());

    }


    @Override
    public void show() {
        this.multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hud.getStage());
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    // ---- Input ----

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPos.set(screenX, screenY, 0);
        viewport.unproject(touchPos);

        Targatable clickedOpponent = getClickedOpponent(touchPos.x, touchPos.y);

        if (selectedCard != null && clickedOpponent != null) {
            turnDirector.onPlayCard(selectedCard.getCard(), clickedOpponent);
            selectedCard = null;
            return true;
        }

        for (CardView cardView : handView.getCardViews()) {
            if (cardView.contains(touchPos.x, touchPos.y)) {
                selectedCard = cardView;
                return true;
            }
        }


        selectedCard = null;
        return false;
    }

    private Targatable getClickedOpponent(float x, float y) {
        for (Targatable target : opponentList) {
            if (target.contains(x, y) && !target.isDead()) {
                return target;
            }
        }
        return null;
    }

    // ---- Render ----

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.BLACK);   // 1. Clear once, at the top

        // 2. Draw world
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        drawWorld(delta);
        batch.end();
        hud.draw(batch, delta);

    }


    private void drawWorld(float delta) {

        Opponent selectedOpponent = gameState.getTargetOpponent();
        if (selectedOpponent != null && !selectedOpponent.isDead()) {
            if (selectedCard != null) {
                selectedOpponent.setPosition(ENEMY_X, ENEMY_Y + 0.5f);
                batch.setColor(Color.YELLOW);
            } else {
                selectedOpponent.setPosition(ENEMY_X, ENEMY_Y);
                batch.setColor(Color.WHITE);
            }
            selectedOpponent.draw(batch, delta);
        }

        batch.setColor(Color.WHITE);
        handView.draw(batch, selectedCard);
    }


    @Override
    public void resize(int width, int height) {
        hud.getUiViewport().update(width, height, true);
        viewport.update(width, height, true);

    }

    // ---- Unused InputProcessor stubs ----

    @Override
    public boolean keyDown(int k) {
        return false;
    }

    @Override
    public boolean keyUp(int k) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int p, int b) {
        return false;
    }

    @Override
    public boolean touchCancelled(int x, int y, int p, int b) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int p) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(float a, float b) {
        return false;
    }
}
