package io.github.some_example_name.entiteRelated.targets;

public abstract class Targatable {
    protected int health;
    protected int maxHealth;
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public Targatable(int health, int maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public boolean isDead() {
        return health <= 0;
    }


   public void takeDamage(int amount){
       this.health -= amount;
   }

    public int getMaxHealth() {
        return maxHealth;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getHealth(){
       return this.health;
   }

    public boolean contains(float worldX, float worldY){
        return worldX >= x && worldX <= x + width &&
            worldY >= y && worldY <= y + height;
    }
}
