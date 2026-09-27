package io.github.some_example_name.view;

import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.target.parentsOrOthers.Targatable;
import io.github.some_example_name.ui.Assets;

import java.util.ArrayList;
import java.util.List;


public class BoardView extends BaseView {
    private static final float MONSTER_FIELD_HEIGHT = 200f; // grass band between trees and hand
    private static final float OPPONENT_FIELD_HEIGHT = 200f; // band for opponent's board, tune against the art

    private final GameState gameState;
    private final Assets assets;

    private final List<BaseView> childrenViews;
    private final MonsterFieldView monsterFieldView;
    private final HandView handView;
    private final OpponentView opponentView2;

    public BoardView(Assets assets, GameState gameState, FitViewport viewport) {
        setFillParent(true);

        this.assets = assets;
        this.gameState = gameState;
        this.childrenViews = new ArrayList<>();

        this.handView = new HandView(gameState.getHand());
        this.monsterFieldView = new MonsterFieldView(gameState.getMonsters());
        this.opponentView2 = new OpponentView(gameState.getOpponents());

        addExistingChildren();
        createUI();
    }

    public void highlight(List<Targatable> validTargets) {

    }


    private void addExistingChildren(){
        childrenViews.add(opponentView2);
        childrenViews.add(monsterFieldView);
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

        add(monsterFieldView)
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
