package io.github.some_example_name.view;


import io.github.some_example_name.data.GameState;
import io.github.some_example_name.ui.Assets;

import java.util.ArrayList;
import java.util.List;

public class BoardView2 extends BaseView {
    private final GameState gameState;
    private final Assets assets;

    private final List<BaseView> childrenViews;
    private final MonsterFieldView2 monsterFieldView2;
    private final HandView2 handView;
    private final OpponentView2 opponentView2;


    public BoardView2(Assets assets, GameState gameState) {
        this.assets = assets;
        this.gameState = gameState;
        this.childrenViews = new ArrayList<>();

        //needs updating
        this.handView = new HandView2(gameState.getHand());
        this.monsterFieldView2 = new MonsterFieldView2(gameState.getMonsters());
        this.opponentView2 = new OpponentView2(gameState.getOpponents());
        addExistingChildren();
    }

    private void addExistingChildren(){
        childrenViews.add(handView);
        childrenViews.add(monsterFieldView2);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void createUI() {
        this.add(monsterFieldView2).center().left();
        this.add(handView).bottom().left();
        this.add(opponentView2).top().right();



    }
}
