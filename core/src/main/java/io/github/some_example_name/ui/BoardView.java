package io.github.some_example_name.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.some_example_name.GdxGame;
import io.github.some_example_name.cards.Monster;
import io.github.some_example_name.data.GameState;

import java.util.List;

public class BoardView {
    private final BoardLayout boardLayout;
    private final HandView handView;
    private final OpponentView opponentView;
    private final MonsterFieldView monsterFieldView;
    private final Assets assets;


    private final GameState gameState;

    public BoardView(ExtendViewport viewport, GdxGame game, GameState gameState) {
        this.gameState = gameState;
        this.opponentView = new OpponentView(gameState);
        this.assets = game.getAssets();
        this.handView = new HandView(assets);
        this.boardLayout = new BoardLayout(viewport);
        this.monsterFieldView = new MonsterFieldView(game.getMonsterAssets());
    }

    public void debugDraw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(
            boardLayout.getPlayerHand().x,
            boardLayout.getPlayerHand().y,
            boardLayout.getPlayerHand().width,
            boardLayout.getPlayerHand().height
        );

        shapeRenderer.rect(
            boardLayout.getEnemyMonster().x,
            boardLayout.getEnemyMonster().y,
            boardLayout.getEnemyMonster().width,
            boardLayout.getEnemyMonster().height
        );

        shapeRenderer.rect(
            boardLayout.getPlayerMonsters().x,
            boardLayout.getPlayerMonsters().y,
            boardLayout.getPlayerMonsters().width,
            boardLayout.getPlayerMonsters().height
        );

    }
    public Rectangle monsterViewDimensions(){
        return boardLayout.getPlayerMonsters();
    }



    public void rebuild() {
        boardLayout.rebuild();
    }

    public void onUpdateHand() {
        handView.update(gameState.getDeckState().getHand());
    }

    public void onUpdateMonsterField() {
        monsterFieldView.update(gameState.getMonsters());
    }

    public List<CardView> getCards() {
        return handView.getCardViews();
    }



    public void draw(SpriteBatch batch, CardView selectedCard, float delta, Boolean isOpponentClicked) {
        handView.draw(batch,
            boardLayout.getPlayerHand().x,
            boardLayout.getPlayerHand().y,
            boardLayout.getPlayerHand().width,
            boardLayout.getPlayerHand().height,
            selectedCard);

        this.opponentView.draw(
            boardLayout.getEnemyMonster().x,
            boardLayout.getEnemyMonster().y,
            boardLayout.getEnemyMonster().width,
            boardLayout.getEnemyMonster().height,
            batch,
            delta,
            isOpponentClicked);

        this.monsterFieldView.draw(boardLayout.getPlayerMonsters().x,
            boardLayout.getPlayerMonsters().y,
            boardLayout.getPlayerMonsters().width,
            boardLayout.getPlayerMonsters().height,batch);
    }




}
