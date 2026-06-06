package io.github.some_example_name.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MonsterView {

    private Rectangle rectangle;
    private Texture texture;

    public MonsterView(Texture texture) {
        this.texture = texture;
        this.rectangle = new Rectangle();
    }


    public void setPosition(float x, float y) {
        rectangle.setPosition(x, y);
    }

    public void setSize(float width, float height) {
        rectangle.setSize(width, height);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }


}
