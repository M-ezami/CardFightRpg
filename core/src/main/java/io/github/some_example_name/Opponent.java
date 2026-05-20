package io.github.some_example_name;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import io.github.some_example_name.Effects.Effect;


import java.util.Map;

public abstract class Opponent implements Targatable {
    private int health;
    int maxHealth;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected Map<Effect, Integer> pool;



    public Opponent(int health, int maxHealth, int damage) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.pool = createEffectPool();
    }

    protected abstract Map<Effect, Integer> createEffectPool();

    public Effect getRandomEffect() {

        int totalWeight = 0;

        for (int weight : pool.values()) {
            totalWeight += weight;
        }

        int random = MathUtils.random(totalWeight - 1);

        int currentWeight = 0;

        for (Map.Entry<Effect, Integer> entry : pool.entrySet()) {

            currentWeight += entry.getValue();

            if (random < currentWeight) {
                return entry.getKey();
            }
        }

        return null;
    }





    public boolean contains(float worldX, float worldY) {
        return worldX >= x && worldX <= x +width &&
            worldY >= y && worldY <= y + height;
    }

    public void takeDamage(int amount) {
        if (health <= 0) return;
        this.health -= amount;

    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    abstract void draw(SpriteBatch batch, float delta);
}
