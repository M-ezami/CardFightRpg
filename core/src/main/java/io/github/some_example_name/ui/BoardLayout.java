package io.github.some_example_name.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class BoardLayout {

    private final ExtendViewport viewport;

    private Rectangle playerHandArea;
    private Rectangle playerMonsterArea;
    private Rectangle enemyMonsterArea;
    private Rectangle tempSpellCardsArea;
    private Rectangle tempMonsterSpellCardsArea;

    public BoardLayout(ExtendViewport viewport) {
        this.viewport = viewport;
        rebuild();
    }

    public void rebuild() {
        float w = viewport.getWorldWidth();
        float h = viewport.getWorldHeight();
        float marginToTempCards = (float) (w * 0.05);
        playerHandArea = new Rectangle(
            0,
            0,
            w / 1.5f,
            h / 3f
        );

        playerMonsterArea = new Rectangle(
            w / 3f,
            playerHandArea.height,
            w / 3f,
            h / 4f
        );

        enemyMonsterArea = new Rectangle(
            w * 0.6f,
            h * 0.6f,
            w / 3f,
            h / 4f
        );

        tempSpellCardsArea = new Rectangle(playerHandArea.width + marginToTempCards, 0, w- playerHandArea.width, playerHandArea.height);
    }

    public Rectangle getPlayerHandArea() {
        return playerHandArea;
    }

    public Rectangle getPlayerMonsterArea() {
        return playerMonsterArea;
    }

    public Rectangle getEnemyMonsterArea() {
        return enemyMonsterArea;
    }

    public Rectangle getTempSpellCardsArea() {
        return tempSpellCardsArea;
    }


}
