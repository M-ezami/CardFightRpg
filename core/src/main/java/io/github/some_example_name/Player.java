package io.github.some_example_name;

public class Player implements Targatable {

    private final Deck deck;
    private final CombatStats stats;
    private int health;

    public Player() {
        this.health = 10;
        this.deck = new Deck();
        this.stats = new CombatStats(3);
    }

    public CombatStats getStats() {
        return stats;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getCurrentMana() {
        return stats.currentMana;
    }

    public void spendMana(int mana) {
        stats.currentMana -= mana;
    }

    public void resetMana() {
        stats.currentMana = stats.maxMana;
    }

    public void resetHealth() {
        stats.currentHealth = stats.maxHealth;
    }


    @Override
    public void takeDamage(int amount) {
            this.health -= amount;
    }


    public int getHealth() {
        return this.health;
    }

    @Override
    public boolean contains(float x, float y) {
        return false;
    }

    @Override
    public boolean isDead() {
        return false;
    }
}
