package io.github.some_example_name;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import io.github.some_example_name.Effects.DamageEffect;
import io.github.some_example_name.Effects.Effect;

import java.util.Map;


public class EasyEnemy extends Opponent {

    private float stateTime;
    private final Assets assets;
    private final Animation<TextureRegion> idleAnimation;



    public EasyEnemy(int health, int maxHealth, Assets assets, int damage) {
        super(health, maxHealth, damage);
        this.width = 2.5f;
        this.assets = assets;
        this.idleAnimation = assets.getSlimeIdleAnimation();

    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }


    @Override
    protected Map<Effect, Integer> createEffectPool() {
        return Map.of(
            new DamageEffect(MathUtils.random(0,5),new PlayerTarget()),70
            );

        }




    @Override
    void draw(SpriteBatch batch, float delta) {
        TextureRegion currentFrame = getCurrentFrame(delta);
        height = width * assets.calculateTextureAspectRatio(currentFrame);
        batch.draw(currentFrame, x, y, width, height);
    }



    private TextureRegion getCurrentFrame(float delta) {
        stateTime += delta;
        return idleAnimation.getKeyFrame(stateTime);
    }
}
