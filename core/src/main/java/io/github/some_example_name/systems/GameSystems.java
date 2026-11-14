package io.github.some_example_name.systems;

public class GameSystems {
    private final CardSystem cardSystem;

    public GameSystems() {
    this.cardSystem = new CardSystem();

    }


    public CardSystem getCardSystem() {
        return cardSystem;
    }
}
