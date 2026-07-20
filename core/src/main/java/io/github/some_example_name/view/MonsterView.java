package io.github.some_example_name.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.cards.Monster;

public class MonsterView {

    private Rectangle rectangle;
    private final Texture texture;
    private final Monster monster;

    public MonsterView(Texture texture, Monster monster) {
        this.texture = texture;
        this.rectangle = new Rectangle();
        this.monster = monster;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Monster getMonster() {
        return monster;
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

    public boolean contains(float worldX, float worldY) {
        return worldX >= rectangle.x && worldX <= rectangle.x + rectangle.width &&
            worldY >= rectangle.y && worldY <= rectangle.y + rectangle.height;
    }



}
