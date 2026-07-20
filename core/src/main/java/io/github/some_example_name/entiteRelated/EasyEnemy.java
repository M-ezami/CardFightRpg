package io.github.some_example_name.entiteRelated;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import io.github.some_example_name.effects.DamageEffect;
import io.github.some_example_name.effects.Effect;
import io.github.some_example_name.entiteRelated.targets.PlayerOrMonsterTarget;
import io.github.some_example_name.entiteRelated.targets.PlayerTarget;
import io.github.some_example_name.ui.Assets;

import java.util.Map;

public class EasyEnemy extends Opponent {

    private float stateTime;
    private final Assets assets;
    private Animation animation;
    private EnemyAnimationState enemyAnimationState;

    public EasyEnemy(int health, int maxHealth, Assets assets) {
        super(health, maxHealth);
        this.width = 2.5f;
        this.assets = assets;
        setAnimationState(EnemyAnimationState.IDLE);
    }

    @Override
    public void setAnimationState(EnemyAnimationState state) {
        if (this.enemyAnimationState == state) return;
        this.enemyAnimationState = state;
        this.stateTime = 0f;
        switch (state) {
            case HURT:   this.animation = assets.getSlimeHurtAnimation();   break;
            case IDLE:   this.animation = assets.getSlimeIdleAnimation();   break;
            case ATTACK: this.animation = assets.getSlimeAttackAnimation(); break;
            case DEATH:
        }
    }

    public EnemyAnimationState getEnemyAnimationState() {
        return enemyAnimationState;
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
             new DamageEffect(MathUtils.random(0, 5), new PlayerOrMonsterTarget()), 70
        );

    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        TextureRegion currentFrame = getCurrentFrame(delta);
        height = width * assets.calculateTextureAspectRatio(currentFrame);
        batch.draw(currentFrame, x, y, width, height);
    }

    private TextureRegion getCurrentFrame(float delta) {
        stateTime += delta;
        if (enemyAnimationState != EnemyAnimationState.IDLE && animation.isAnimationFinished(stateTime)) {
            setAnimationState(EnemyAnimationState.IDLE);
        }
        return (TextureRegion) animation.getKeyFrame(stateTime);
    }


}
