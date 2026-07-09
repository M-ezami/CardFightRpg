package io.github.some_example_name.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.ui.MonsterAsset;
import io.github.some_example_name.cards.Monster;

import java.util.HashMap;
import java.util.List;

public class MonsterFieldView {

    private final HashMap<Monster, MonsterView> monsterViews;
    //hashmap with monstertype and monsterassets
    private final MonsterAsset monsterAsset;

    public MonsterFieldView(MonsterAsset monsterAsset) {
        this.monsterAsset = monsterAsset;
        monsterViews = new HashMap<>();
    }

    public void update(List<Monster> monsters) {
        monsterViews.keySet().removeIf(m -> !monsters.contains(m));
        for (Monster monster : monsters) {
            monsterViews.computeIfAbsent(monster, m -> new MonsterView(monsterAsset.get(monster.getType()),monster));
        }
    }

    public void draw(float x, float y, float width, float height,
                     SpriteBatch batch) {

        for (Monster monster : monsterViews.keySet()) {

            MonsterView view = monsterViews.get(monster);

            float mx = x + width / 2f;
            float my = y + height / 2f;

            view.setPosition(mx, my);
            view.setSize(width / 4, height / 4);

            view.draw(batch);
        }
    }


    public MonsterView getMonsterAtPosition(float x, float y) {
        for (Monster monster : monsterViews.keySet()) {
            MonsterView view = monsterViews.get(monster);
            if (view.contains(x, y)) {
                return view;
            }
        }
        return null;
    }
}
