package io.github.some_example_name.view;


import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.enitites.SimpleMonster;
import io.github.some_example_name.ui.Assets;

import java.util.ArrayList;
import java.util.List;


public class BoardView2 extends BaseView {
    private static final float MONSTER_FIELD_HEIGHT = 200f; // grass band between trees and hand — tune against the art

    private final GameState gameState;
    private final Assets assets;

    private final List<BaseView> childrenViews;
    private final MonsterFieldView2 monsterFieldView2;
    private final HandView2 handView;

    public BoardView2(Assets assets, GameState gameState, FitViewport viewport) {
        this.assets = assets;
        this.gameState = gameState;
        this.childrenViews = new ArrayList<>();
        setFillParent(true);
        //needs updating
        this.handView = new HandView2(gameState.getHand());
        this.monsterFieldView2 = new MonsterFieldView2(gameState.getMonsters());
        addExistingChildren();
        createUI();
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
        add()
            .expandY()
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
