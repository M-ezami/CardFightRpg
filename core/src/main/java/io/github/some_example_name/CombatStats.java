package io.github.some_example_name;

public class CombatStats {

    int maxMana;
    int maxHealth;
    int currentMana;
    int currentHealth = maxHealth;

    public CombatStats(int maxMana) {
        this.maxMana = maxMana;
        this.currentMana = maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
}
