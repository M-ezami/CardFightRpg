package io.github.some_example_name.ui;

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
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.events.utilities.EventBus;


public class Hud {

    private static final float BAR_SCALE = 3f;

    private final float barOverlayPositionX = 80;
    private final float barOverlayPositionY = 1200;
    private float bannerTimer = 0;

    private float stateTime;
    private final Viewport uiViewport;
    private final BitmapFont font;
    private final Stage stage;
    private final EventBus eventBus;
    private final Animation progressAnimationBar;
    private final Assets assets;
    private final Player player;
    private final Table table;
    private final GameState gameState;


    private TextButton godButton;
    private Label turnBanner;
    private ProgressBar healthBar;
    private ProgressBar manaBar;


    public Hud(Assets assets, GameState gameState) {
        this.uiViewport = new ScreenViewport();
        this.font = assets.getButtonFont();
        this.stage = new Stage(uiViewport);
        this.assets = assets;
        this.eventBus = EventBus.getInstance();
        this.gameState = gameState;
        this.player = gameState.getPlayer();
        this.progressAnimationBar = assets.getBarOverlayIconAnimation();
        this.table = new Table();
        setupHud();
    }


    public void setupHud() {
        createBars();
        setupTurnBanner();
        setupGodButton();
    }

    private void createHealthBar() {
        this.healthBar = addBar(assets.getBarBackground(), assets.getHealthBarForeground(), player.getMaxHealth(), player.getHealth(), barOverlayPositionX + 1, barOverlayPositionY + 8);
    }

    private void createManaBar() {
        this.manaBar = addBar(assets.getBarBackground(), assets.getManaBarForeground(), player.getMaxMana(), player.getMana(), barOverlayPositionX + 1, barOverlayPositionY + healthBar.getHeight() + 8);
    }

    public void createBars() {
        createHealthBar();
        createManaBar();
    }


    public void onPhaseChange() {

        this.godButton.setDisabled(false);
        switch (gameState.getRoundPhase()) {
            // if we are in spell, fight or play phase the button click enters the discard phase, changes inbetween these states does not need a
            // phaseStartRequest it happens automatically in turnsystem based on game logic f.i if a monster card gets played in spellphase we automatically swithc to playPhase
            case SPELL_PHASE, FIGHT_PHASE, PLAY_PHASE -> eventBus.emit(new ChooseCardsToDiscardEvent());
            case DISCARD_PHASE -> eventBus.emit(new DiscardEvent());
            case ENEMY_TURN -> this.godButton.setDisabled(true);

        }
    }

    public String ButtonString() {
        return switch (gameState.getRoundPhase()) {
            case DRAW_PHASE -> "draw phase";
            case SPELL_PHASE -> "Discard Phase";
            case PLAY_PHASE -> "Play Phase";
            case FIGHT_PHASE -> "fight Phase";
            case DISCARD_PHASE -> "Discard cards & End turn ";
            case ENEMY_TURN -> "Enemy Turn";
        };
    }


    public void setupGodButton() {
        float buttonScaleWidth = 6f;
        float buttonScaleHeight = 2f;
        TextureRegion buttonTextureRegion = assets.getButtonTextureRegion();
        Drawable textButtonRegion = new TextureRegionDrawable(buttonTextureRegion);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = textButtonRegion;
        //need to add down still

        this.godButton = new TextButton(ButtonString(), textButtonStyle);
        this.godButton.setText(ButtonString());
        System.out.println("actual" + godButton.getWidth());
        table.right();
        table.setFillParent(true);
        table.add(godButton).width(buttonTextureRegion.getRegionWidth() * buttonScaleWidth).height(buttonTextureRegion.getRegionHeight() * buttonScaleHeight);
        stage.addActor(table);
        this.godButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPhaseChange();
            }
        });

    }

    private ProgressBar addBar(TextureRegion bg, TextureRegion fg, float max, float value, float x, float y) {

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

//    private void showBannerPerPhase() {
//        switch (roundPhase) {
//            case SPELL_PHASE ->
//        }
//    }


    private void showBanner(String text, Color color, float delta) {
        bannerTimer += delta;
        turnBanner.setColor(color);
        turnBanner.setText(text);
        turnBanner.setVisible(true);


        float bannerTime = 2;
        if (bannerTimer >= bannerTime) {
            bannerTimer = 0;
            turnBanner.setVisible(false);
        }
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
        this.godButton.setText(ButtonString());
        batch.end();
        drawUI();
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


}
