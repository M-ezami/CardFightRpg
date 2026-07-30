package io.github.some_example_name.enitites;


interface TurnView {

    void onEnemyTurnBegin();

    void onPlayerTurnBegin();

    void onPlayerTurnReady();

    void refreshHandOnCardPlayed();
}
