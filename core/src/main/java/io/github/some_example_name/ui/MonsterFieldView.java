package io.github.some_example_name.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.MonsterAsset;
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
            monsterViews.computeIfAbsent(monster, m -> new MonsterView(monsterAsset.get(monster.getType())));
        }
    }

    public void draw(float x, float y, float width, float height,
                     SpriteBatch batch) {

        for (Monster monster : monsterViews.keySet()) {

            MonsterView view = monsterViews.get(monster);

            float mx = x + width / 2f;
            float my = y + height / 2f;

            view.setPosition(mx, my);
            view.setSize(width/4, height/4);
            System.out.println(monster.getType());
            view.draw(batch);
        }
    }
}
