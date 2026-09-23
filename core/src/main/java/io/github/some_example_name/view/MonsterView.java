package io.github.some_example_name.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.some_example_name.enitites.Monster;
import io.github.some_example_name.ui.MonsterAsset;

public class MonsterView extends BaseView {

    private static final float MONSTER_WIDTH = 200;
    private static final float MONSTER_HEIGHT = 150;



    private final Texture texture;
    private final Monster monster;
    private Image monsterImage;
    private MonsterAsset monsterAsset;



    public MonsterView(Texture texture, Monster monster) {
        this.texture = texture;
        this.monster = monster;
    }

    public MonsterView(Monster monster) {
        this.monsterAsset = MonsterAsset.getMonsterAsset();
        this.texture = monsterAsset.getCurrentTexture();
        this.monster = monster;
        createUI();
    }

    @Override
    public void createUI() {
        monsterImage = new Image(texture);
        add(monsterImage).height(MONSTER_HEIGHT).width(MONSTER_WIDTH);

        setDebug(true);
    }

    @Override
    public void refresh() {
    }

    public Monster getMonster() {
        return monster;
    }
}
