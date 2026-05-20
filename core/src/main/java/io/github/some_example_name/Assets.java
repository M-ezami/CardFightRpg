package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {


    private Texture backgroundTexture;
    private Texture cardTexure;
    private TextureAtlas cardAtlas;
    private TextureAtlas slimeAtlas;
    private TextureRegion cardOverlay;
    private TextureRegion textBox;
    private TextureRegion nameRegion;
    private Animation slimeIdleAnimation;
    private Texture buttonSkin;
    private TextButton.TextButtonStyle textButtonStyle;
    private TextureRegionDrawable drawable;

    private BitmapFont font;

    AssetManager assetManger = new AssetManager();



    public void load() {
        loadAtlas();
        loadCardTextures();
        createFont();
        loadSlimeAnimations();
        loadSkins();
        wrapButtonTexturetoDrawable();
        loadTextButtonStyle(this.drawable);
        // add this
    }

    public void wrapButtonTexturetoDrawable(){
       this.drawable =
            new TextureRegionDrawable(
                new TextureRegion(
                    getButtonSkin()));
    }


    public void loadSkins() {
        this.buttonSkin = new Texture(Gdx.files.internal("UI_Flat_Button01a_3.png"));
    }

    public TextButton.TextButtonStyle loadTextButtonStyle(TextureRegionDrawable drawable) {
        this.textButtonStyle = new TextButton.TextButtonStyle();
        this.textButtonStyle.up = drawable;
        this.textButtonStyle.down = drawable;
        this.textButtonStyle.font = getFont();
        return this.textButtonStyle;

    }

    public TextButton.TextButtonStyle getTextButtonStyle() {
        return this.textButtonStyle;
    }

    private void loadSlimeAnimations() {
        Array<TextureRegion> frames = new Array<>();
        frames.add(slimeAtlas.findRegion("slime-idle-0"));
        frames.add(slimeAtlas.findRegion("slime-idle-1"));
        frames.add(slimeAtlas.findRegion("slime-idle-2"));
        frames.add(slimeAtlas.findRegion("slime-idle-3"));
        slimeIdleAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    public Animation<TextureRegion> getSlimeIdleAnimation() {
        return slimeIdleAnimation; // just return it
    }


    public void createFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("CherryCreamSoda-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;

        font = generator.generateFont(parameter);
        generator.dispose();
    }

    public BitmapFont getCardFont() {
        return font;
    }

    public TextureRegion getNameRegion() {
        return nameRegion;
    }

    public void loadAtlas() {
        this.cardAtlas = new TextureAtlas("ui/ui.atlas");
        this.slimeAtlas = new TextureAtlas("slime/slime.atlas");
    }


    public void loadCardTextures() {
        this.cardOverlay = cardAtlas.findRegion("borders");
        this.textBox = cardAtlas.findRegion("paper");
        this.nameRegion = cardAtlas.findRegion("tape_top");
    }


    public float calculateTextureAspectRatio(TextureRegion texture) {
        return (float) texture.getRegionHeight() / texture.getRegionWidth();
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public Texture getCardTexure() {
        return cardTexure;
    }

    public TextureAtlas getCardAtlas() {
        return cardAtlas;
    }

    public TextureRegion getCardOverlay() {
        return cardOverlay;
    }

    public TextureRegion getTextBox() {
        return textBox;
    }

    public AssetManager getAssetManger() {
        return assetManger;
    }

    public TextureAtlas getSlimeAtlas() {
        return slimeAtlas;
    }


    public BitmapFont getFont() {
        return font;
    }

    @Override
    public void dispose() {
        this.backgroundTexture.dispose();
        this.cardAtlas.dispose();
    }

    public Texture getButtonSkin() {
        return buttonSkin;
    }
}
