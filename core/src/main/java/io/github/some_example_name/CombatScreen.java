package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.List;

public class CombatScreen extends ScreenAdapter implements InputProcessor {

    // ---- Fields ----
    private final CombatSystem combatSystem;
    private final SpriteBatch batch;
    private final Viewport viewport;
    private final Viewport uiViewport;
    private final Assets assets;
    private final HandView handView;
    private final List<Targatable> opponentList;
    private final Vector3 touchPos = new Vector3();
    private final float enemyPositionX = 11.5f;
    private final float enemyPositionY = 6.5f;

    private Stage stage;
    private Table table;
    private TextButton endTurnButton;
    private Label turnBanner;
    private InputMultiplexer multiplexer;
    private CardView selectedCard = null;
    private Card lastPlayedCard = null;
    private CombatFlowController combatFlowController;
    // ---- Constructor ----
    public CombatScreen(CombatSystem combatSystem, GdxGame game) {
        this.combatSystem = combatSystem;
        this.combatFlowController = new CombatFlowController(combatSystem,this);
        this.assets = game.getAssets();
        this.batch = game.getBatch();
        this.viewport = new ExtendViewport(16f, 9f);
        this.uiViewport = new ScreenViewport();
        this.handView = new HandView(assets);
        this.opponentList = combatSystem.getOpponents();
        this.handView.update(combatSystem.getPlayerHand());
        populateScene2d();
    }

    // ---- Setup ----
    private void populateScene2d() {
        this.stage = new Stage(uiViewport);
        setupEndTurnButton();
        setupTurnBanner();
    }



    public void disableEndturnButton() {
        endTurnButton.setDisabled(true);
    }


    public void enableEndturn() {
        endTurnButton.setDisabled(false);
    }


    public void showEnemyIntro() {
        disableEndturnButton();
        turnBanner.setColor(Color.RED);
        turnBanner.setText("ENEMY TURN");
        turnBanner.setVisible(true);
    }

    public void showPlayerTurnBanner() {
        turnBanner.setColor(Color.GREEN);
        turnBanner.setText("PLAYER TURN");
        turnBanner.setVisible(true);
    }

    public void hideBanner() {
        turnBanner.setVisible(false);
    }

    private void setupEndTurnButton() {
        this.endTurnButton = new TextButton("END TURN", assets.getTextButtonStyle());

        this.table = new Table();
        table.setFillParent(true);
        table.right();
        table.add(endTurnButton)
            .width(200)
            .height(100)
            .padBottom(20)
            .padRight(20);

        endTurnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                combatFlowController.onEndTurn();
            }
        });

        stage.addActor(table);
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
        if (combatSystem.getGameState().getCurrentPhase() == Phase.ENEMYTURN) return false;

        touchPos.set(screenX, screenY, 0);
        viewport.unproject(touchPos);

        Targatable clickedOpponent = getClickedOpponent(touchPos.x, touchPos.y);

        if (clickedOpponent != null) {
            combatSystem.setSelectedOpponent(clickedOpponent);
        }

        if (selectedCard != null && clickedOpponent != null) {
            combatSystem.onPlayCard(selectedCard.getCard());
            lastPlayedCard = selectedCard.getCard();
            selectedCard = null;
            onHandChanged();
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
        batch.begin();
        drawWorld(delta);
        batch.end();
        drawUI();
    }

    private void drawWorld(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        Opponent selectedOpponent = combatSystem.getGameState().getTargetOpponent();
        if (selectedOpponent != null && !selectedOpponent.isDead()) {
            if (selectedCard != null) {
                selectedOpponent.setPosition(enemyPositionX, enemyPositionY + 0.5f);
                batch.setColor(Color.YELLOW);
            } else {
                selectedOpponent.setPosition(enemyPositionX, enemyPositionY);
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

    // ---- Helpers ----
    public void onHandChanged() {
        handView.update(combatSystem.getPlayerHand());
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
        viewport.update(width, height, true);
    }

    // ---- Unused InputProcessor stubs ----
    @Override public boolean keyDown(int k) { return false; }
    @Override public boolean keyUp(int k) { return false; }
    @Override public boolean keyTyped(char c) { return false; }
    @Override public boolean touchUp(int x, int y, int p, int b) { return false; }
    @Override public boolean touchCancelled(int x, int y, int p, int b) { return false; }
    @Override public boolean touchDragged(int x, int y, int p) { return false; }
    @Override public boolean mouseMoved(int x, int y) { return false; }
    @Override public boolean scrolled(float a, float b) { return false; }
}
