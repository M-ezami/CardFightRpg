package io.github.some_example_name.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.data.GameState;
import io.github.some_example_name.entiteRelated.Opponent;

import java.util.List;

public class OpponentView {

    private final List<Opponent> opponentList;


    public OpponentView(GameState gameState) {
        this.opponentList = gameState.getOpponents();
    }

    public Opponent getOpponentAt(float x,  float y) {
        for (Opponent opponent : opponentList) {
            if (opponent.contains(x, y)) {
                return  opponent;
            }
        }
        return null;
    }


    public void draw(float x, float y, float width, float height, SpriteBatch batch, float delta) {
        for (final Opponent opponent : opponentList) {

            x += width /2;
            y += height /2;
            opponent.setPosition(x, y);
            opponent.draw(batch, delta);

        }
    }


}
