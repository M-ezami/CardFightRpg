package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.ui.Assets;

public class CardView {

    private final Card card;
    private final Assets assets;

    private final TextureRegion cardOverlay;
    private final TextureRegion textBox;
    private final TextureRegion nameRegion;

    private final BitmapFont font;
    private final GlyphLayout glyphLayout;

    private float x, y, width, height;

    private final String description;
    private final String name;

    // Temporary vectors to avoid garbage collection inside render loop
    private final Vector3 tempCoords1 = new Vector3();
    private final Vector3 tempCoords2 = new Vector3();
    private final Matrix4 originalMatrix = new Matrix4();
    private final Matrix4 screenMatrix = new Matrix4();

    public CardView(Card card, Assets assets) {
        this.card = card;
        this.assets = assets;
        this.font = assets.getCardFont();
        this.glyphLayout = new GlyphLayout();

        this.cardOverlay = assets.getCardOverlay();
        this.textBox = assets.getTextBox();
        this.nameRegion = assets.getNameRegion();

        this.name = card.getName() != null ? card.getName() : "Unknown";
        this.description = card.getDescription() != null ? card.getDescription() : "";
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
        // 1. Draw your card textures in World Space normally
        batch.draw(cardOverlay, x, y, width, height);

        float tbWorldY = toWorldY(22f);
        float tbWorldH = toWorldH(textBox);
        batch.draw(textBox, x, tbWorldY, width, tbWorldH);

        float nameWorldY = toWorldY(110f);
        float nameWorldH = toWorldH(nameRegion);
        batch.draw(nameRegion, x, nameWorldY, width, nameWorldH);

        // -----------------------------------------------------------------
        // GUARANTEED VISIBILITY: Project world coordinates into screen pixel coordinates
        // -----------------------------------------------------------------

        // This calculates the screen projection manually using the current batch setup
        Matrix4 combined = batch.getProjectionMatrix().cpy().mul(batch.getTransformMatrix());

        // Project bottom-left and top-right of text box
        tempCoords1.set(x, tbWorldY, 0).prj(combined);
        tempCoords2.set(x + width, tbWorldY + tbWorldH, 0).prj(combined);

        // Convert normalized device coordinates [-1, 1] to pixel coordinates [0, Width/Height]
        float screenTbX = (tempCoords1.x + 1) * Gdx.graphics.getWidth() / 2f;
        float screenTbY = (tempCoords1.y + 1) * Gdx.graphics.getHeight() / 2f;
        float screenTbW = (tempCoords2.x - tempCoords1.x) * Gdx.graphics.getWidth() / 2f;
        float screenTbH = (tempCoords2.y - tempCoords1.y) * Gdx.graphics.getHeight() / 2f;

        // Project Name Box Bounds to Screen Pixels
        tempCoords1.set(x, nameWorldY, 0).prj(combined);
        tempCoords2.set(x + width, nameWorldY + nameWorldH, 0).prj(combined);

        float screenNameX = (tempCoords1.x + 1) * Gdx.graphics.getWidth() / 2f;
        float screenNameY = (tempCoords1.y + 1) * Gdx.graphics.getHeight() / 2f;
        float screenNameW = (tempCoords2.x - tempCoords1.x) * Gdx.graphics.getWidth() / 2f;
        float screenNameH = (tempCoords2.y - tempCoords1.y) * Gdx.graphics.getHeight() / 2f;

        // 2. Stop the current batch rendering configuration temporary
        batch.end();

        // 3. Switch the batch matrix setup to Screen Space Pixels (Same approach HUD uses)
        originalMatrix.set(batch.getProjectionMatrix());
        screenMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setProjectionMatrix(screenMatrix);
        batch.begin();

        // 4. Save font scale settings
        float oldScaleX = font.getScaleX();
        float oldScaleY = font.getScaleY();

        // CHANGE 1: Made the font scale smaller.
        // We cut the scale down by dividing it further (adjust 220f up if it is still too large)
        float fontScale = screenTbH / 220f;
        font.getData().setScale(Math.max(0.1f, fontScale));

        // 5. Render text inside Screen Space Pixels
        font.setColor(Color.BLACK);

        // CHANGE 2: Name Text adjustments
        // Pushed the name further down inside the tape/banner region using a larger vertical padding
        float namePadding = screenNameH * 0.45f;
        font.draw(batch, name, screenNameX, screenNameY + screenNameH - namePadding, screenNameW, Align.center, false);

        // CHANGE 3: Description Text adjustments
        // Increased padY so the description starts lower down within the text box space
        float padX = screenTbW * 0.12f;
        float padY = screenTbH * 0.35f;
        font.draw(batch, description, screenTbX + padX, screenTbY + screenTbH - padY, screenTbW - (padX * 2), Align.center, true);

        // 6. Tear down text configurations and restore standard world space layout matrices
        font.getData().setScale(oldScaleX, oldScaleY);
        batch.end();
        batch.setProjectionMatrix(originalMatrix);
        batch.begin();
    }

    public boolean contains(float worldX, float worldY) {
        return worldX >= x && worldX <= x + width &&
            worldY >= y && worldY <= y + height;
    }

    public Card getCard() {
        return card;
    }
}
