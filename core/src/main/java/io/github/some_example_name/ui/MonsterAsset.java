package io.github.some_example_name.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import io.github.some_example_name.cards.cardRelated.MonsterType;
import io.github.some_example_name.enitites.Monster;

import java.util.HashMap;
import java.util.Map;

public class MonsterAsset implements Disposable {

    private final Map<MonsterType, Texture> textures = new HashMap<>();
    private final Texture mageTexture = new Texture("/home/mosa/Storage/projects/petProjects/games/cardFightSystem2/assets/slime/slime-idle-0.png");

    private static MonsterAsset monsterAsset = new MonsterAsset();

    public MonsterAsset(){
        load();
    }

    public static MonsterAsset getMonsterAsset(){
        if(monsterAsset == null){
            monsterAsset = new MonsterAsset();
        }
        return monsterAsset;
    }

    public void load() {
        textures.put(MonsterType.MAGE, new Texture("/home/mosa/Storage/projects/petProjects/games/cardFightSystem2/assets/slime/slime-idle-0.png"));
    }

    public Texture get(MonsterType type) {
        return textures.get(type);
    }

    public void dispose() {
        for (Texture t : textures.values()) {
            t.dispose();
        }
    }

    public Texture getCurrentTexture(){
        return mageTexture;

    }

}
