package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.some_example_name.GdxGame;
import io.github.some_example_name.TurnDirector;
import io.github.some_example_name.cards.cardParents.CardType;
import io.github.some_example_name.cards.cardParents.MonsterCard;
import io.github.some_example_name.cards.cardParents.SpellCard;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Targatable;
import io.github.some_example_name.events.*;
import io.github.some_example_name.ui.*;

import java.util.ArrayList;
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
    private final ExtendViewport viewport;
    private final ShapeRenderer shapeRenderer;

    private final List<Opponent> opponentList;
    private final Vector3 touchPos = new Vector3();
    private final EventBus eventBus;

    private TurnDirector turnDirector;
    private CardView selectedCard = null;
    private boolean isOpponentClicked = false;
    private Targatable clickedOpponent;
    private final Hud hud;
    private final BoardView boardView;
    private final GameState gameState;


    public CombatScreen(GameState gameState, GdxGame game, EventBus eventBus) {
        Assets assets = game.getAssets();
        this.gameState = gameState;
        this.eventBus = eventBus;
        this.shapeRenderer = new ShapeRenderer();
        this.batch = game.getBatch();
        this.viewport = new ExtendViewport(16f, 9f);
        this.boardView = new BoardView(this.viewport, game, gameState);

        this.opponentList = gameState.getOpponents();

        this.hud = new Hud(assets, gameState, eventBus);
        subscribe();
        hud.setupHud();
        refreshHand();

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
        eventBus.subscribe(PlayerTurnStartEvent.class, event -> {
            refreshHand();
        });

    }

    public void onPlayerTurnReady() {
        refreshHand();
        hud.hideBanner();
    }

    // ---- Visual helpers ----

    public void refreshHand() {
        boardView.onUpdateHand();
    }


    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hud.getStage());
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        hud.addButtonListener(() -> turnDirector.onPlayerEndTurn());
    }

    public boolean onPlaySpellCard() {
        clickedOpponent = getClickedOpponent(touchPos.x, touchPos.y);
        if (clickedOpponent != null) {
            turnDirector.onPlaySpellCard((SpellCard) selectedCard.getCard(), clickedOpponent);

            return true;
        }

        return false;
    }


    public boolean onPlayMonsterCard() {
        Rectangle dimensions = boardView.monsterViewDimensions();
        if (dimensions.contains(touchPos.x, touchPos.y)) {
            turnDirector.onPlayMonsterCard((MonsterCard) selectedCard.getCard());
            boardView.onUpdateMonsterField();
            return true;
        }
        return false;
    }

    public boolean getSelectedCard() {
        for (CardView cardView : boardView.getCards()) {
            if (cardView.contains(touchPos.x, touchPos.y)) {
                selectedCard = cardView;
                return true;
            }
        }
        return false;

    }

    // ---- Input ----

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        touchPos.set(screenX, screenY, 0);
        viewport.unproject(touchPos);

        if (selectedCard == null) {
            return getSelectedCard();
        }

        if (selectedCard.getCardType() == CardType.SPELL) {
            boolean result = onPlaySpellCard();
            if (result) selectedCard = null;
            return result;
        }

        if (selectedCard.getCardType() == CardType.MONSTER) {
            boolean result = onPlayMonsterCard();
            if (result) selectedCard = null;
            return result;
        }

        return false;
    }


    @Override
    public boolean touchDragged(int x, int y, int p) {
        System.out.println("Dragging at: " + x + ", " + y);
        return true;

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

        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        viewport.getCamera().update();


        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        boardView.debugDraw(shapeRenderer);
        shapeRenderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        drawWorld(delta);
        batch.end();


        hud.draw(batch, delta);
    }


    private void drawWorld(float delta) {
        batch.setColor(Color.WHITE);
        boardView.draw(batch, selectedCard, delta, isOpponentClicked);
    }


    @Override
    public void resize(int width, int height) {
        hud.getUiViewport().update(width, height, true);
        boardView.rebuild();
        viewport.update(width, height, true);
        boardView.rebuild();

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
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(float a, float b) {
        return false;
    }
}
