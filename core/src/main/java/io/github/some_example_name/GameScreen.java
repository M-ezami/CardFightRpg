package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {

    // ---- Fields ----
    private final GdxGame game;
    private final Player player;
    private final List<Opponent> opponents;
    private final Viewport viewport;
    private final BitmapFont font;

    private CombatSystem combatSystem;
    private GameState gameState;

    // ---- Constructor ----
    public GameScreen(GdxGame game) {
        this.game = game;
        this.player = new Player();
        this.opponents = new ArrayList<>();
        this.font = new BitmapFont();
        this.viewport = new ExtendViewport(16f, 9f);
        setupPlayerDeck();
    }

    // ---- Setup ----
    private void setupPlayerDeck() {
        player.getDeck().addCard(new FireCard("Card 1"));
        player.getDeck().addCard(new FireCard("Card 2"));
        player.getDeck().addCard(new FireCard("Card 3"));
        player.getDeck().addCard(new FireCard("Card 4"));
        player.getDeck().addCard(new FireCard("Card 5"));
    }

    private void createOpponents() {
        opponents.add(new EasyEnemy(7, 7,game.getAssets(),2));
    }

    // ---- Encounter ----
    private void onOpponentEncounter(List<Opponent> opponents) {
        createOpponents();
        this.gameState = new GameState(player, opponents);
        this.combatSystem = new CombatSystem(gameState);
        game.setScreen(new CombatScreen(combatSystem, game));
    }

    // ---- Render ----
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            onOpponentEncounter(opponents);
        }

        viewport.apply();
        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        game.getBatch().begin();
        font.draw(game.getBatch(), "Press SPACE to start combat", 2f, 2f);
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
