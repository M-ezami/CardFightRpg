package io.github.some_example_name.effects;

import io.github.some_example_name.enitites.Monster;
import io.github.some_example_name.target.parentsOrOthers.TargetingStrategy;

public class MonsterDamageBuff extends MonsterBuff {


    public MonsterDamageBuff(int amount,  TargetingStrategy targetingStrategy) {
        super(amount,  targetingStrategy);
    }

    public MonsterDamageBuff(int amount,  TargetingStrategy targetingStrategy, MultipleRoundsEffect multipleRoundsEffect) {
        super(amount,  targetingStrategy, multipleRoundsEffect);
    }

    @Override
    public void applyMonsterBuff(Monster monster) {
        System.out.println("monster damage amount before was: "+ monster.getDamage());
        monster.gainDamageAmount(2);
        System.out.println(monster.getClass().getSimpleName() + "monster gained additional : " + amount  +"damage" );
        System.out.println("monster damage amount after buff is: "+ monster.getDamage());
    }

    @Override
    public String getDescription() {
        return "";
    }
}
