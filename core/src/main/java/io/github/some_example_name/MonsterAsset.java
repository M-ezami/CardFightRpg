package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class MonsterAsset implements Disposable {

    private final Map<MonsterType, Texture> textures = new HashMap<>();

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
}
