package io.github.some_example_name.view;

import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.enitites.Opponent;
import io.github.some_example_name.ui.Assets;

import java.util.ArrayList;
import java.util.List;


public class BoardView2 extends BaseView {
    private static final float MONSTER_FIELD_HEIGHT = 200f; // grass band between trees and hand
    private static final float OPPONENT_FIELD_HEIGHT = 200f; // band for opponent's board, tune against the art

    private final GameState gameState;
    private final Assets assets;

    private final List<BaseView> childrenViews;
    private final MonsterFieldView2 monsterFieldView2;
    private final HandView2 handView;
    private final OpponentView2 opponentView2;

    public BoardView2(Assets assets, GameState gameState, FitViewport viewport) {
        setFillParent(true);

        this.assets = assets;
        this.gameState = gameState;
        this.childrenViews = new ArrayList<>();

        this.handView = new HandView2(gameState.getHand());
        this.monsterFieldView2 = new MonsterFieldView2(gameState.getMonsters());
        this.opponentView2 = new OpponentView2(gameState.getOpponents());

        addExistingChildren();
        createUI();
    }

    private void addExistingChildren(){
        childrenViews.add(opponentView2);
        childrenViews.add(monsterFieldView2);
        childrenViews.add(handView);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void createUI() {
        add(opponentView2)
            .expandX()
            .expandY()
            .width(Value.percentWidth(2f / 3f, this))
            .height(OPPONENT_FIELD_HEIGHT)
            .top()
            .right()
            .row();

        add(monsterFieldView2)
            .expandX()
            .width(Value.percentWidth(2f / 3f, this))
            .height(MONSTER_FIELD_HEIGHT)
            .bottom()
            .left()
            .row();

        add(handView)
            .expandX()
            .width(Value.percentWidth(2f / 3f, this))
            .bottom()
            .left();
    }
}
