package io.github.some_example_name.effects;

import io.github.some_example_name.entiteRelated.Targatable;

public interface MonsterEffect extends Targatable {


    void onStartTurn();
    void onEndTurn();
    void onAttack();
    void onBeingAttacked();




}
