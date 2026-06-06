package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.AnimationDirector;
import io.github.some_example_name.GdxGame;
import io.github.some_example_name.TurnDirector;
import io.github.some_example_name.businessLogic.CombatSystem;
import io.github.some_example_name.cards.FireCard;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.EasyEnemy;
import io.github.some_example_name.entiteRelated.Opponent;
import io.github.some_example_name.entiteRelated.Player;
import io.github.some_example_name.events.EventBus;
import io.github.some_example_name.ui.Assets;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for a combat encounter.
 * Responsible for constructing and wiring the object graph:
 * GameState → CombatSystem → TurnDirector
 * → CombatScreen → TurnDirector (back-ref)
 */
public class GameScreen extends ScreenAdapter {

    // ---- Fields ----

    private final GdxGame game;
    private final Player player;
    private final List<Opponent> opponents;
    private final Viewport viewport;
    private final BitmapFont font;
    // ---- Constructor ----

    public GameScreen(GdxGame game, Assets assets) {
        this.game = game;
        this.player = new Player();
        this.opponents = new ArrayList<>();
        this.font = assets.getButtonFont();
        this.viewport = new ExtendViewport(16f, 9f);
        setupPlayerDeck();
    }

    // ---- Setup ----

    private void setupPlayerDeck() {
        player.getDeck().addCard(new FireCard("Card 1"));
        player.getDeck().addCard(new FireCard("Card 2"));
        player.getDeck().addCard(new FireCard("Card 3"));
        player.getDeck().addCard(new FireCard("Card 4"));
        player.getDeck().addCard(new SimpleMonsterCard());
    }

    private void createOpponents() {
        opponents.add(new EasyEnemy(12, 12, game.getAssets()));
    }

    // ---- Encounter ----

    private void startCombat() {
        EventBus eventBus = new EventBus();
        createOpponents();

        GameState gameState = new GameState(player, opponents);
        CombatSystem combatSystem = new CombatSystem(gameState);
        CombatScreen combatScreen = new CombatScreen(gameState, game, eventBus);
        TurnDirector turnDirector = new TurnDirector(combatSystem, eventBus);
        AnimationDirector animationDirector = new AnimationDirector(eventBus,opponents);
        combatScreen.setTurnDirector(turnDirector);
        game.setScreen(combatScreen);

    }

    // ---- Render ----

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            startCombat();
        }

        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        game.getBatch().begin();
        font.draw(game.getBatch(), "Press SPACE to start combat", 4f, 7f);

        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
