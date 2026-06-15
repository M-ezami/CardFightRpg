package io.github.some_example_name.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.cards.Card;
import io.github.some_example_name.cards.cardParents.CardType;

public class CardView {

    private final Card card;
    private final Assets assets;

    private final TextureRegion cardOverlay;
    private final TextureRegion textBox;
    private final TextureRegion nameRegion;

    private final BitmapFont font;
    private final GlyphLayout glyphLayout;

    // world bounds (single source of truth)
    private float x, y, width, height;

    private final String description;
    private final String name;


    public CardView(Card card, Assets assets) {
        this.card = card;
        this.assets = assets;
        this.font = assets.getCardFont();
        this.glyphLayout = new GlyphLayout();

        this.cardOverlay = assets.getCardOverlay();
        this.textBox = assets.getTextBox();
        this.nameRegion = assets.getNameRegion();

        this.name = card.getName();
        this.description = card.getDescription();

        // DO NOT scale font per instance (do it once in Assets instead)
    }

    public CardType getCardType() {
        return card.getCardType();
    }


    public void setBounds(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private float toWorldY(float pixelOffsetFromBottom) {
        return y + (pixelOffsetFromBottom / cardOverlay.getRegionHeight()) * height;
    }

    private float toWorldH(TextureRegion region) {
        return (region.getRegionHeight() / (float) cardOverlay.getRegionHeight()) * height;
    }

    public void draw(SpriteBatch batch) {

        batch.draw(cardOverlay, x, y, width, height);

        float tbWorldY = toWorldY(22f);
        float tbWorldH = toWorldH(textBox);
        batch.draw(textBox, x, tbWorldY, width, tbWorldH);

        float nameWorldY = toWorldY(110f);
        float nameWorldH = toWorldH(nameRegion);
        batch.draw(nameRegion, x, nameWorldY, width, nameWorldH);

        font.setColor(Color.BLACK);
        glyphLayout.setText(font, description, Color.BLACK, width, Align.center, true);

        font.draw(
            batch,
            glyphLayout,
            x,
            tbWorldY + tbWorldH
        );
    }

    public boolean contains(float worldX, float worldY) {
        return worldX >= x && worldX <= x + width &&
            worldY >= y && worldY <= y + height;
    }

    public Card getCard() {
        return card;
    }
}
