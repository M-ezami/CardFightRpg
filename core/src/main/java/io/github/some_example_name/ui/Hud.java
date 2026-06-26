package io.github.some_example_name.ui;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.PhaseStartEvent;
import io.github.some_example_name.businessLogic.AdvancePhaseEvent;
import io.github.some_example_name.businessLogic.RoundPhase;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.events.EnemyEffectAppliedEvent;
import io.github.some_example_name.events.EnemyTurnStartEvent;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.events.PlayerTurnStartEvent;

import java.util.List;

public class Hud {

    private static final float BAR_SCALE = 3f;

    private final Viewport uiViewport;
    private final BitmapFont font;
    private final Stage stage;
    private final float barOverlayPositionX = 80;
    private final float barOverlayPositionY = 1200;
    private final EventBus eventBus;
    private final Animation progressAnimationBar;
    private final Assets assets;
    private final Player player;
    private final List<Opponent> opponents;


    private TextButton endTurnButton;
    private Label turnBanner;
    private ProgressBar healthBar;
    private ProgressBar manaBar;

    private float stateTime;


    public Hud(Assets assets, GameState gameState) {
        this.uiViewport = new ScreenViewport();
        this.font = assets.getButtonFont();
        this.stage = new Stage(uiViewport);
        this.assets = assets;
        this.eventBus = EventBus.getInstance();
        this.player = gameState.getPlayer();
        this.opponents = gameState.getOpponents();
        this.progressAnimationBar = assets.getBarOverlayIconAnimation();
        setupHud();
        addButtonListener();

    }



    public void setupHud() {
        createBars();
        setupTurnBanner();
        setupEndTurnButton();
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

    public void createBars() {
        createHealthBar();
        createManaBar();
    }

    public void addButtonListener() {
        System.out.println("reaching this but not that");
        this.endTurnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("reached that");
                eventBus.emit(new PhaseStartEvent(RoundPhase.ENEMY_TURN));
                System.out.println("enemy turn has started");
            }

        });

    }

    public void setupEndTurnButton() {
        float buttonScaleWidth = 6f;
        float buttonScaleHeight = 2f;
        TextureRegion buttonTextureRegion = assets.getButtonTextureRegion();
        Drawable textButtonRegion = new TextureRegionDrawable(buttonTextureRegion);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = textButtonRegion;
        //need to add down still

        this.endTurnButton = new TextButton("End turn", textButtonStyle);
        System.out.println("actual" + endTurnButton.getWidth());
        Table table = new Table();
        table.right();
        table.setFillParent(true);
        table.add(endTurnButton)
            .width(buttonTextureRegion.getRegionWidth() * buttonScaleWidth)
            .height(buttonTextureRegion.getRegionHeight() * buttonScaleHeight);
        stage.addActor(table);


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

    private void drawUI() {
        uiViewport.apply();
        stage.act();
        stage.draw();
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

    private void showBanner(String text, Color color) {
        turnBanner.setColor(color);
        turnBanner.setText(text);
        turnBanner.setVisible(true);
    }

    public void hideBanner() {
        turnBanner.setVisible(false);
    }

    private void setupTurnBanner() {
        Label.LabelStyle bannerStyle = new Label.LabelStyle();
        bannerStyle.font = font;
        this.turnBanner = new Label("", bannerStyle);
        this.turnBanner.setFontScale(3f);
        this.turnBanner.setVisible(false);

        Table bannerTable = new Table();
        bannerTable.setFillParent(true);
        bannerTable.center();
        bannerTable.add(turnBanner);

        stage.addActor(bannerTable);
    }

    public void draw(SpriteBatch batch, float delta) {
        updateBars();
        uiViewport.apply();
        batch.setProjectionMatrix(uiViewport.getCamera().combined);
        batch.begin();
        drawBarOveralay(batch, delta);
        batch.end();

        drawUI();
    }

    public void showEnemyEffect() {
        showBanner(
            player.getHealth() + "-" + opponents.getFirst().getRandomEffect().getDescription(),
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


    private void updateBars() {
        if (healthBar != null) {
            healthBar.setValue(player.getHealth());
        }

        if (manaBar != null) {
            manaBar.setValue(player.getMana());
        }
    }


    public Stage getStage() {
        return stage;
    }

    public Viewport getUiViewport() {
        return uiViewport;
    }

    public void addToMultiplexer(InputMultiplexer multiplexer) {
        multiplexer.addProcessor(stage);
    }

    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
    }


}
