package io.github.some_example_name.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.events.utilities.EventBus;
import io.github.some_example_name.view.BoardView;
import io.github.some_example_name.view.MonsterView;

import java.util.HashMap;


public class Hud {

    private static final float BAR_SCALE = 3f;

    private final float barOverlayPositionX = 80;
    private final float barOverlayPositionY = 1200;
    private float bannerTimer = 0;

    private float stateTime;
    private final Viewport uiViewport;
    private final BitmapFont font;
    private final Stage stage;
    private final Animation progressAnimationBar;
    private final Assets assets;
    private final Player player;
    private final Table table;
    private final GameState gameState;
    private final BoardView boardView;
    private final EventBus eventBus;

    private TextButton godButton;
    private Label turnBanner;
    private ProgressBar healthBar;
    private ProgressBar manaBar;
    private ProgressBar enemyHealthBar;
    private final HashMap<MonsterView, ProgressBar> monsterHealthBar;

    public Hud(Assets assets, GameState gameState, BoardView boardView, Viewport uiViewport) {
        this.boardView = boardView;
        this.eventBus = EventBus.getInstance();
        this.uiViewport = uiViewport;
        this.font = assets.getButtonFont();
        this.stage = new Stage(uiViewport);
        this.assets = assets;
        this.gameState = gameState;
        this.player = gameState.getPlayer();
        this.progressAnimationBar = assets.getBarOverlayIconAnimation();
        this.table = new Table();
        this.monsterHealthBar = new HashMap<>();
        setupHud();
    }

    private void updateMonsterBars() {

        var currentMonsterViews = boardView.getMonsterFieldView().getMonsterViews().values();


        for (MonsterView monsterView : currentMonsterViews) {

            if (monsterView.getMonster().isDead()) {
                continue;
            }

            ProgressBar bar = monsterHealthBar.get(monsterView);

            if (bar == null) {
                bar = addBar(
                    assets.getBarBackground(),
                    assets.getHealthBarForeground(),
                    monsterView.getMonster().getMaxHealth(),
                    monsterView.getMonster().getHealth(),
                    0,
                    0
                );
                monsterHealthBar.put(monsterView, bar);
            }

            updateMonsterBar(monsterView, bar);
        }


        monsterHealthBar.entrySet().removeIf(entry -> {
            MonsterView mv = entry.getKey();
            boolean stillAliveAndPresent = currentMonsterViews.contains(mv) && !mv.getMonster().isDead();
            if (!stillAliveAndPresent) {
                entry.getValue().remove();
            }
            return !stillAliveAndPresent;
        });
    }

    private void updateMonsterBar(MonsterView monsterView, ProgressBar bar) {
        Vector3 pos = new Vector3(
            (float) (monsterView.getRectangle().x + 0.15),
            monsterView.getRectangle().y + monsterView.getRectangle().height,
            0
        );

        boardView.getViewport().project(pos);

        bar.setPosition(pos.x, pos.y);
        bar.setValue(monsterView.getMonster().getHealth());
    }

    private void setupHud() {
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

    private void createBars() {
        createHealthBar();
        createManaBar();
        createEnemyHealthBar();
    }


    private void createEnemyHealthBar() {
        this.enemyHealthBar = addBar(assets.getBarBackground(), assets.getHealthBarForeground(), gameState.getOpponents().get(0).getMaxHealth(), gameState.getOpponents().get(0).getHealth(), barOverlayPositionX + 2200, barOverlayPositionY + 100);
    }


    private void onPhaseChange() {

        this.godButton.setDisabled(false);
        switch (gameState.getRoundPhase()) {
            // if we are in spell, fight or play phase the button click enters the discard phase, changes inbetween these states does not need a
            // phaseStartRequest it happens automatically in turnsystem based on game logic f.i if a monster card gets played in spellphase we automatically swithc to playPhase
            case SPELL_PHASE, FIGHT_PHASE, MONSTER_PHASE -> eventBus.emit(new ChooseCardsToDiscardEvent());
            case DISCARD_PHASE -> eventBus.emit(new DiscardEvent());
            case ENEMY_TURN -> this.godButton.setDisabled(true);

        }
    }

    private String ButtonString() {
        return switch (gameState.getRoundPhase()) {
            case SPELL_PHASE -> "Discard Phase";
            case MONSTER_PHASE -> "Play Phase";
            case FIGHT_PHASE -> "fight Phase";
            case DISCARD_PHASE -> "Discard cards & End turn ";
            case ENEMY_TURN -> "Enemy Turn";
        };
    }


    private void setupGodButton() {
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
        stage.act();
        stage.draw();
    }

    private TextureRegion getCurrentFrame(float delta) {
        stateTime += delta;
        return (TextureRegion) progressAnimationBar.getKeyFrame(stateTime);

    }

    private void drawBarOveralay(SpriteBatch batch, float delta) {
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

        if (!gameState.getOpponents().isEmpty()) {
            if (enemyHealthBar != null) {
                enemyHealthBar.setValue(gameState.getOpponents().get(0).getHealth());

                if (gameState.getOpponents().get(0).getHealth() <= 0) {
                    enemyHealthBar.setVisible(false);
                    enemyHealthBar.remove();
                    enemyHealthBar = null;
                }
            }
        }
        updateMonsterBars();
    }


    public Stage getStage() {
        return stage;
    }

    public Viewport getUiViewport() {
        return uiViewport;
    }


}
