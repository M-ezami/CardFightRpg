package io.github.some_example_name.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class boardLayout {

    private Rectangle playerHand;
    private Rectangle playerMonsters;
    private Rectangle enemyMonster;
    private ExtendViewport viewport;


    public boardLayout(ExtendViewport viewport) {
        this.viewport = viewport;
        this.playerHand = new Rectangle(0,0,viewport.getWorldWidth()/2,viewport.getWorldHeight()*1/3);
        this.playerMonsters = new Rectangle(viewport.getWorldWidth()*1/3,playerHand.getHeight(),viewport.getWorldWidth()/3,viewport.getWorldHeight()*1/4);
        this.enemyMonster =  new Rectangle();

    }

    public Rectangle getPlayerHand() {
        return playerHand;
    }

    public Rectangle getPlayerMonsters() {
        return playerMonsters;
    }

    public Rectangle getEnemyMonster() {
        return enemyMonster;
    }

    public ExtendViewport getViewport() {
        return viewport;
    }
}
