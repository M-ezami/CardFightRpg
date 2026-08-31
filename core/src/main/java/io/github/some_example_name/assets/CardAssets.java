package io.github.some_example_name.assets;


import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import io.github.some_example_name.cards.cardRelated.parents.Card;

public class CardAssets {

    private TextureAtlas cardAtlas;

    private TextureRegion cardOverlay;
    private TextureRegion textBox;
    private TextureRegion titleArea;

    private BitmapFont cardFont;

    private static final CardAssets cardAssets = new CardAssets();

    public CardAssets() {
        load();
    }
    public static CardAssets getCardAssets(){
        return cardAssets;
    }

    public void load() {
        loadAtlas();
        loadTextures();
        createFont();
    }

    private void loadAtlas() {
        cardAtlas = new TextureAtlas("ui/ui.atlas");
    }

    private void loadTextures() {
        cardOverlay = cardAtlas.findRegion("borders");
        textBox = cardAtlas.findRegion("paper");
        titleArea = cardAtlas.findRegion("tape_top");
    }

    private void createFont() {
        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(
                com.badlogic.gdx.Gdx.files.internal("CherryCreamSoda-Regular.ttf")
            );

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 32;
        parameter.genMipMaps = true;
        parameter.minFilter = Texture.TextureFilter.MipMapLinearLinear;
        parameter.magFilter = Texture.TextureFilter.Linear;

        cardFont = generator.generateFont(parameter);

        generator.dispose();
    }

    public TextureRegion getCardOverlay() {
        return cardOverlay;
    }

    public TextureRegion getTextBox() {
        return textBox;
    }

    public TextureRegion getTitleArea() {
        return titleArea;
    }

    public BitmapFont getCardFont() {
        return cardFont;
    }

    public void dispose() {
        cardAtlas.dispose();
        cardFont.dispose();
    }
}
