package io.github.some_example_name.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.GdxGame;
import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.data.DraggedCard;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.ui.Assets;
import io.github.some_example_name.ui.BoardLayout;

import java.util.List;

public class BoardView {
    private final BoardLayout boardLayout;
    private final HandView handView;
    private final OpponentView opponentView;
    private final MonsterFieldView monsterFieldView;
    private final Assets assets;
    private final GameState gameState;
    private DraggedMonster draggedMonster;
    private DraggedCard draggedCard;
    private final Viewport viewport;

    // I really think this should be refactored into a seperate view for drawing and layout and saaame for all child classes
    // another good thing would be if this doesnt know its children it just knows it has a list of children to call update and render but also not that important rn
    public BoardView(ExtendViewport viewport, GdxGame game, GameState gameState) {
        this.viewport = viewport;
        this.gameState = gameState;
        this.opponentView = new OpponentView(gameState);
        this.assets = game.getAssets();
        this.handView = new HandView(assets, gameState);
        this.boardLayout = new BoardLayout(viewport);
        this.monsterFieldView = new MonsterFieldView(game.getMonsterAssets());
    }

    public Viewport getViewport() {
        return viewport;
    }

    public TextureRegion getPointingMonsterArrow(){
        return this.assets.getArrowRegion();
    }

    public HandView getHandView() {
        return handView;
    }

    public OpponentView getOpponentView() {
        return opponentView;
    }

    public List<Card> getSelectedCards() {
        return handView.getSelectedCards();
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

    public MonsterFieldView getMonsterFieldView() {
        return monsterFieldView;
    }

    public void updateHand() {
        handView.update();
    }

    public Rectangle monsterViewDimensions() {
        return boardLayout.getPlayerMonsters();
    }

    public void rebuild() {
        boardLayout.rebuild();
    }


    public void onUpdateMonsterField() {
        monsterFieldView.update(gameState.getMonsters());
    }

    public void update(){
        onUpdateMonsterField();
        updateHand();
    }

    public List<CardView> getCards() {
        return handView.getCardViews();
    }

    public void setDraggedCard(DraggedCard draggedCard) {
        this.draggedCard = draggedCard;
    }

    public void setDraggedMonster(DraggedMonster dragedMonster){
        this.draggedMonster = dragedMonster;
    }


    public void draw(SpriteBatch batch, float delta) {
        handView.draw(batch,
            boardLayout.getPlayerHand().x,
            boardLayout.getPlayerHand().y,
            boardLayout.getPlayerHand().width,
            boardLayout.getPlayerHand().height,
            draggedCard);

        this.opponentView.draw(
            boardLayout.getEnemyMonster().x,
            boardLayout.getEnemyMonster().y,
            boardLayout.getEnemyMonster().width,
            boardLayout.getEnemyMonster().height,
            batch,
            delta);

        this.monsterFieldView.draw(boardLayout.getPlayerMonsters().x,
            boardLayout.getPlayerMonsters().y,
            boardLayout.getPlayerMonsters().width,
            boardLayout.getPlayerMonsters().height, batch);

        if(draggedMonster != null){
            batch.draw(getPointingMonsterArrow(),draggedMonster.x(),draggedMonster.y(), (float) (getPointingMonsterArrow().getRegionHeight()*0.01), (float) (getPointingMonsterArrow().getRegionWidth()*0.01));
        }

    }




}
