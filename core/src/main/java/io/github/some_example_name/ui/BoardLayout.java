package io.github.some_example_name.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class BoardLayout {

    private final ExtendViewport viewport;

    private Rectangle playerHand;
    private Rectangle playerMonsters;
    private Rectangle enemyMonster;

    public BoardLayout(ExtendViewport viewport) {
        this.viewport = viewport;
        rebuild();
    }

    public void rebuild() {
        float w = viewport.getWorldWidth();
        float h = viewport.getWorldHeight();

        playerHand = new Rectangle(
            0,
            0,
            w / 1.5f,
            h / 3f
        );

        playerMonsters = new Rectangle(
            w / 3f,
            playerHand.height,
            w / 3f,
            h / 4f
        );

        enemyMonster = new Rectangle(
            w * 0.6f,
            h * 0.6f,
            w / 3f,
            h / 4f
        );
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
}
