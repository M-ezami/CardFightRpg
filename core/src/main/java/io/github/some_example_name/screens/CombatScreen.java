package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.some_example_name.GdxGame;
import io.github.some_example_name.TurnDirector;
import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.cardParents.CardType;
import io.github.some_example_name.cards.cardParents.MonsterCard;
import io.github.some_example_name.cards.cardParents.SpellCard;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Targatable;
import io.github.some_example_name.events.*;
import io.github.some_example_name.ui.*;

import java.util.List;

/**
 * Visuals and input only. No game rules live here.
 * Player actions are forwarded to TurnDirector.
 * State for rendering is read directly from GameState.
 */
public class CombatScreen extends ScreenAdapter implements InputProcessor {

    private final SpriteBatch batch;
    private final ExtendViewport viewport;
    private final ShapeRenderer shapeRenderer;
    private final Vector3 touchPos = new Vector3();
    private final EventBus eventBus;
    private final Hud hud;
    private final BoardView boardView;
    private final GameState gameState;
    private final List<Opponent> opponentList;

    private TurnDirector turnDirector;

    private CardView draggedCard = null;
    private float dragX, dragY;

    public CombatScreen(GameState gameState, GdxGame game, EventBus eventBus) {
        this.gameState = gameState;
        this.eventBus = eventBus;
        this.batch = game.getBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.viewport = new ExtendViewport(16f, 9f);
        this.boardView = new BoardView(this.viewport, game, gameState);
        this.opponentList = gameState.getOpponents();
        this.hud = new Hud(game.getAssets(), gameState, eventBus);

        subscribe();
        hud.setupHud();
        boardView.onUpdateHand();
    }

    public void setTurnDirector(TurnDirector turnDirector) {
        this.turnDirector = turnDirector;
    }

    // ---- Subscriptions ----

    public void subscribe() {
        eventBus.subscribe(CardPlayedEvent.class, e -> refreshHand());
        eventBus.subscribe(PlayerTurnReadyEvent.class, e -> onPlayerTurnReady());
        eventBus.subscribe(PlayerTurnStartEvent.class, e -> refreshHand());
    }

    public void onPlayerTurnReady() {
        refreshHand();
        hud.hideBanner();
    }

    public void refreshHand() {
        boardView.onUpdateHand();
    }

    // ---- Show ----

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hud.getStage());
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        hud.addButtonListener(() -> turnDirector.onPlayerEndTurn());
    }

    // ---- Input ----

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPos.set(screenX, screenY, 0);
        viewport.unproject(touchPos);

        for (CardView cardView : boardView.getCards()) {
            if (cardView.contains(touchPos.x, touchPos.y)) {
                draggedCard = cardView;
                dragX = touchPos.x;
                dragY = touchPos.y;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (draggedCard == null) return false;
        touchPos.set(screenX, screenY, 0);
        viewport.unproject(touchPos);
        dragX = touchPos.x;
        dragY = touchPos.y;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (draggedCard == null) return false;
        touchPos.set(screenX, screenY, 0);
        viewport.unproject(touchPos);

        tryPlayCard(draggedCard, touchPos.x, touchPos.y);
        draggedCard = null;
        return true;
    }

    private void tryPlayCard(CardView cardView, float x, float y) {
        System.out.println("trying to play card " + cardView + " at " + x + ", " + y);
        Card card = cardView.getCard();

        if (card.getCardType() == CardType.SPELL) {
            Targatable target = getOpponentAt(x, y);
            if (target == null) return;
            turnDirector.onPlaySpellCard((SpellCard) card, target);
        }

        if (card.getCardType() == CardType.MONSTER) {
            if (!boardView.monsterViewDimensions().contains(x, y)) return;
            turnDirector.onPlayMonsterCard((MonsterCard) card);
            boardView.onUpdateMonsterField();
        }
    }

    private Targatable getOpponentAt(float x, float y) {
        for (Targatable target : opponentList) {
            if (target.contains(x, y) && !target.isDead()) return target;
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
        boardView.draw(batch, draggedCard, delta, false);

        if (draggedCard != null) {
            float cardW = boardView.monsterViewDimensions().width / Math.max(boardView.getCards().size(), 1);
            float cardH = boardView.monsterViewDimensions().height;
            draggedCard.setBounds(dragX - cardW / 2f, dragY - cardH / 2f, cardW, cardH);
            draggedCard.draw(batch);
        }
    }

    @Override
    public void resize(int width, int height) {
        hud.getUiViewport().update(width, height, true);
        viewport.update(width, height, true);
        boardView.rebuild();
    }

    // ---- Unused stubs ----

    @Override public boolean keyDown(int k) { return false; }
    @Override public boolean keyUp(int k) { return false; }
    @Override public boolean keyTyped(char c) { return false; }
    @Override public boolean touchCancelled(int x, int y, int p, int b) { draggedCard = null; return false; }
    @Override public boolean mouseMoved(int x, int y) { return false; }
    @Override public boolean scrolled(float a, float b) { return false; }
}
