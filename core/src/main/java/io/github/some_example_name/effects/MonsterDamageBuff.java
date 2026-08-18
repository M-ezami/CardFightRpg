package io.github.some_example_name.effects;

import io.github.some_example_name.enitites.Monster;

public class MonsterDamageBuff extends MonsterBuff {

private int turns;

    public MonsterDamageBuff(int amount, int turns, boolean allMonsters) {
        super(amount,allMonsters);
        this.turns = turns;
    }

    @Override
    public void applyMonsterBuff(Monster monster) {
        System.out.println("monster damage amount before was: "+ monster.getDamage());

        monster.addDamage(amount);
        System.out.println(monster.getClass().getSimpleName() + "monster gained additional : " + amount  +"damage" );
        System.out.println("monster damage amount after buff is: "+ monster.getDamage());
    }

    @Override
    public String getDescription() {
        return "";
    }
}
