package io.github.some_example_name.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.some_example_name.data.Card;


public class CardView {

    private TextureRegion cardOverlay;
    private TextureRegion textBox;
    private TextureRegion nameRegion;

    private final Card card;
    private final String description;
    private final String name;
    private float width;
    private float height;

    private BitmapFont font;
    private Assets assets;
    private GlyphLayout glyphLayout;
    private float drawX, drawY;

    public CardView(Card card, Assets assets) {
        this.assets = assets;
        this.font = assets.getCardFont();
        this.glyphLayout = new GlyphLayout();
        this.card = card;
        this.name = this.card.getName();
        this.description = this.card.getDescription();
        buildCardUi();
//        float aspectRatio =
//            (float) texture.getHeight() / texture.getWidth();
        float aspectRatio = this.assets.calculateTextureAspectRatio(cardOverlay);
        this.width = 2f;
        this.height = width * aspectRatio;

        this.font.getData().setScale(0.009f); // add this
        this.font.setUseIntegerPositions(false);

    }

    public Card getCard() {
        return card;
    }

    public void buildCardUi() {
        this.cardOverlay = assets.getCardOverlay();
        this.textBox = assets.getTextBox();
        this.nameRegion = assets.getNameRegion();

    }



    private float toWorldY(float y, float pixelOffsetFromBottom) {
        return y + (pixelOffsetFromBottom / cardOverlay.getRegionHeight()) * height;
    }

    private float toWorldH(TextureRegion region) {
        return (region.getRegionHeight() / (float) cardOverlay.getRegionHeight()) * height;
    }

    public void draw(SpriteBatch batch, float x, float y) {
        this.drawX = x;
        this.drawY = y;
        batch.draw(cardOverlay, x, y, width, height);

        float tbWorldY = toWorldY(y, 22f);
        float tbWorldH = toWorldH(textBox);
        batch.draw(textBox, x, tbWorldY, width, tbWorldH);

        float nameWorldY = toWorldY(y, 110f);
        float nameWorldH = toWorldH(nameRegion);
        batch.draw(nameRegion, x, nameWorldY, width, nameWorldH);

        font.setColor(Color.WHITE);
        glyphLayout.setText(font, description);
        font.draw(batch, description, x + (width / 2) - glyphLayout.width / 2, tbWorldY + tbWorldH - 0.1f);
    }

    public boolean contains(float worldX, float worldY) {
        return worldX >= drawX && worldX <= drawX + width &&
            worldY >= drawY && worldY <= drawY + height;
    }

}
