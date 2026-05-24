package io.github.some_example_name.entiteRelated;


interface TurnView {

    void onEnemyTurnBegin();

    void onPlayerTurnBegin();

    void onPlayerTurnReady();

    void refreshHandOnCardPlayed();
}
