package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.GdxGame;
import io.github.some_example_name.TurnDirector;
import io.github.some_example_name.data.DeckState;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.entiteRelated.Targatable;
import io.github.some_example_name.events.*;
import io.github.some_example_name.ui.Assets;
import io.github.some_example_name.ui.CardView;
import io.github.some_example_name.ui.HandView;

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
    private final Viewport uiViewport;
    private final Assets assets;
    private final HandView handView;
    private final GameState gameState;
    private final List<Opponent> opponentList;
    private final Vector3 touchPos = new Vector3();
    private final EventBus eventBus;
    private final Player player;
    private final DeckState deckState;


    private static final float ENEMY_X = 11.5f;
    private static final float BAR_SCALE = 3f;
    private static final float ENEMY_Y = 6.5f;
    private final float barOverlayPositionX = 80;
    private final float barOverlayPositionY = 1200;

    private TurnDirector turnDirector;
    private Stage stage;

    private Label turnBanner;
    private InputMultiplexer multiplexer;
    private CardView selectedCard = null;
    private ProgressBar healthBar;
    private ProgressBar manaBar;
    private Animation progressAnimationBar;
    private float stateTime;


    public CombatScreen(GameState gameState, GdxGame game, EventBus eventBus) {
        this.eventBus = eventBus;
        this.gameState = gameState;
        this.player = gameState.getPlayer();
        this.deckState = gameState.getDeckState();

        this.assets = game.getAssets();
        this.batch = game.getBatch();
        this.viewport = new ExtendViewport(16f, 9f);
        this.uiViewport = new ScreenViewport();
        this.handView = new HandView(assets);
        this.opponentList = gameState.getOpponents();
        this.progressAnimationBar = assets.getBarOverlayIconAnimation();
        populateScene2d();
        subscribe();

    }


    private ProgressBar addBar(TextureRegion bg, TextureRegion fg,
                               float max, float value,
                               float x, float y) {

        Drawable background = new TextureRegionDrawable(bg);
        Drawable fill = new TextureRegionDrawable(fg);


        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = background;
        style.knobBefore = fill;

        ProgressBar bar = new ProgressBar(0, max, 1, false, style);
        bar.setValue(value);
        background.setMinHeight(bg.getRegionHeight() * BAR_SCALE);
        fill.setMinHeight(fg.getRegionHeight() * BAR_SCALE);

        bar.setSize(fg.getRegionWidth() * BAR_SCALE, fg.getRegionHeight() * BAR_SCALE);
        bar.setPosition(x, y);

        stage.addActor(bar);
        return bar;

    }

    private void createHealthBar() {
        this.healthBar = addBar(
            assets.getBarBackground(),
            assets.getHealthBarForeground(),
            player.getMaxHealth(),
            player.getHealth(),
            barOverlayPositionX + 1,
            barOverlayPositionY + 8
        );
    }

    private void createManaBar() {

        this.manaBar = addBar(
            assets.getBarBackground(),
            assets.getManaBarForeground(),
            player.getMaxMana(),
            player.getMana(),
            barOverlayPositionX + 1,
            barOverlayPositionY + healthBar.getHeight() + 8
        );
    }


    public TextureRegion getCurrentFrame(float delta) {
        stateTime += delta;
        return (TextureRegion) progressAnimationBar.getKeyFrame(stateTime);

    }

    public void drawBarOveralay(SpriteBatch batch, float delta) {
        TextureRegion currentFrame = getCurrentFrame(delta);
        TextureRegion overlay = assets.getBarOverlayTextureRegion();

        float overlayW = overlay.getRegionWidth() * BAR_SCALE;
        float overlayH = overlay.getRegionHeight() * BAR_SCALE;
        float frameW = currentFrame.getRegionWidth() * BAR_SCALE;
        float frameH = currentFrame.getRegionHeight() * BAR_SCALE;

        batch.draw(overlay, barOverlayPositionX, barOverlayPositionY, overlayW, overlayH);
        batch.draw(currentFrame, barOverlayPositionX - frameW, barOverlayPositionY, frameW, frameH);
    }


    public void createManaAndHealthBar() {
        createHealthBar();
        createManaBar();
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
        eventBus.subscribe(EnemyTurnStartEvent.class, event -> {
            onEnemyTurnBegin();
        });
        eventBus.subscribe(PlayerTurnStartEvent.class, event -> {
            onPlayerTurnBegin();
        });
        eventBus.subscribe(PlayerTurnReadyEvent.class, event -> {
            onPlayerTurnReady();
        });
        eventBus.subscribe(EnemyEffectAppliedEvent.class, event -> showEnemyEffect());
    }

    public void showEnemyEffect() {


        showBanner(
            player.getHealth() + "-" + opponentList.get(0).getRandomEffect().getDescription(),
            Color.RED
        );

    }


    // ---- Turn lifecycle ----

    public void onEnemyTurnBegin() {
        showBanner("ENEMY TURN", Color.RED);
    }

    public void onPlayerTurnBegin() {
        showBanner("PLAYER TURN", Color.GREEN);
    }

    public void onPlayerTurnReady() {
        hideBanner();

        refreshHand();
    }

    // ---- Visual helpers ----

    public void refreshHand() {
        handView.update(gameState.getDeckState().getHand());

    }


    private void showBanner(String text, Color color) {
        turnBanner.setColor(color);
        turnBanner.setText(text);
        turnBanner.setVisible(true);
    }

    private void hideBanner() {
        turnBanner.setVisible(false);
    }





    // ---- Setup ----

    private void populateScene2d() {
        this.stage = new Stage(uiViewport);

        setupTurnBanner();
        refreshHand();
        createManaAndHealthBar();
    }




    private void setupTurnBanner() {
        Label.LabelStyle bannerStyle = new Label.LabelStyle();
        bannerStyle.font = assets.getFont();
        this.turnBanner = new Label("", bannerStyle);
        this.turnBanner.setFontScale(3f);
        this.turnBanner.setVisible(false);

        Table bannerTable = new Table();
        bannerTable.setFillParent(true);
        bannerTable.center();
        bannerTable.add(turnBanner);

        stage.addActor(bannerTable);
    }

    @Override
    public void show() {
        this.multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
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
        updateBars();
        ScreenUtils.clear(Color.BLACK);   // 1. Clear once, at the top

        // 2. Draw world
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        drawWorld(delta);
        batch.end();

        // 3. Draw overlay in UI/screen space, after world
        uiViewport.apply();
        batch.setProjectionMatrix(uiViewport.getCamera().combined);
        batch.begin();
        drawBarOveralay(batch, delta);
        batch.end();

        drawUI(); // stage on top
    }

    private void updateBars() {
        if (healthBar != null) {
            healthBar.setValue(player.getHealth());
        }

        if (manaBar != null) {
            manaBar.setValue(player.getMana());
        }
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

    private void drawUI() {
        uiViewport.apply();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
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
