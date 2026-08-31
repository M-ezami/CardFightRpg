package io.github.some_example_name.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import io.github.some_example_name.enitites.Monster;
import io.github.some_example_name.ui.MonsterAsset;

public class MonsterView extends BaseView {

    private final Texture texture;
    private final Monster monster;

    private Image monsterImage;
    private ProgressBar healthBar;
    private MonsterAsset monsterAsset;

    public MonsterView(Texture texture, Monster monster) {
        this.texture = texture;
        this.monster = monster;
    }

    public MonsterView(Monster monster) {
        this.monsterAsset = MonsterAsset.getMonsterAsset();
        this.texture = monsterAsset.getCurrentTexture();
        this.monster = monster;
    }


    @Override
    public void createUI() {
        monsterImage = new Image(texture);
//        healthBar = new ProgressBar(
//            monster.getHealth(),
//            monster.getMaxHealth()
//        );

        add(healthBar)
            .width(100)
            .height(10);

        row();

        add(monsterImage);
    }


    @Override
    public void refresh() {

    }

    public Monster getMonster() {
        return monster;
    }
}
