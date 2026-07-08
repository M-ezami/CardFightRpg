package io.github.some_example_name.ui;

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

    private TextureAtlas cardAtlas;
    private TextureAtlas slimeAtlas;
    private TextureRegion cardOverlay;

    private TextureRegion textBox;
    private TextureRegion nameRegion;

    private Animation slimeIdleAnimation;
    private Animation slimeHurtAnimation;
    private Animation slimeMoveAnimation;
    private Animation slimeAttackAnimation;

    private TextureRegionDrawable drawable;

    private Texture buttonSkin;
    private Texture barBackgroundTexture;
    private Texture barForegroundTexture;
    private Texture barOverlayTexture;
    private Texture arrow;
    private TextureRegion arrowRegion;

    private TextureRegion barBackground;
    private TextureRegion healthBarForeground;
    private TextureRegion manaBarForeground;
    private Animation barOverlayIconAnimation;
    private BitmapFont buttonFont;
    private BitmapFont cardFont;
    private TextureRegion barOverlayTextureRegion;
    private Texture buttonTextures;
    private TextureRegion buttonTextureRegion;

    AssetManager assetManger = new AssetManager();


    public void load() {
        this.arrow = new Texture(Gdx.files.internal("HUD/arrow.png"));
        this.arrowRegion = new TextureRegion(this.arrow,35,64,294,200);
        loadAtlas();
        loadCardTextures();
        createFonts();
        loadSlimeAnimations();
        loadSkins();
        wrapButtonTexturetoDrawable();
        loadTextButtonStyle(this.drawable);
        loadBarAssets();
        loadButtonTextures();
    }


    private void loadBarAssets() {
        this.barBackgroundTexture = new Texture(Gdx.files.internal("HUD/HealthBarBackground.png"));
        this.barForegroundTexture = new Texture(Gdx.files.internal("HUD/HealthBarForeground.png"));
        this.barOverlayTexture = new Texture(Gdx.files.internal("HUD/HealthBarOverlay.png"));
        loadOverlayAnimations();
        this.barBackground = new TextureRegion(this.barBackgroundTexture, 27, 17, 61, 9);
        this.healthBarForeground = new TextureRegion(this.barForegroundTexture, 27, 5, 61, 9);
        this.manaBarForeground = new TextureRegion(this.barForegroundTexture, 27, 17, 61, 9);
        this.barOverlayTextureRegion = new TextureRegion(this.barOverlayTexture, 27, 5, 64, 23);

    }

    public void loadButtonTextures() {
        this.buttonTextures = new Texture(Gdx.files.internal("HUD/UI_TravelBookAnimated_Spritesheet01a.png"));
        this.buttonTextureRegion = new TextureRegion(this.buttonTextures, 1, 34, 30, 29);
    }

    public TextureRegion getButtonTextureRegion() {
        return buttonTextureRegion;
    }

    private void loadOverlayAnimations() {
        Array<TextureRegion> frames = new Array<>();
        frames.add(new TextureRegion(this.barOverlayTexture, 0, 0, 25, 28));
        frames.add(new TextureRegion(this.barOverlayTexture, 90, 0, 25, 28));
        barOverlayIconAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    private void wrapButtonTexturetoDrawable() {
        this.drawable =
            new TextureRegionDrawable(
                new TextureRegion(
                    getButtonSkin()));
    }


    public void loadSkins() {
        this.buttonSkin = new Texture(Gdx.files.internal("UI_Flat_Button01a_3.png"));
    }

    public void loadTextButtonStyle(TextureRegionDrawable drawable) {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = drawable;
        textButtonStyle.down = drawable;
        textButtonStyle.font = getButtonFont();
    }


    public void loadSlimeAnimations() {
        loadSlimeIdleAnimations();
        loadSlimeAttackAnimations();
        loadSlimeHurtAnimations();
        loadSlimeMoveAnimations();
    }

    private void loadSlimeIdleAnimations() {
        Array<TextureRegion> frames = new Array<>();
        frames.add(slimeAtlas.findRegion("slime-idle-0"));
        frames.add(slimeAtlas.findRegion("slime-idle-1"));
        frames.add(slimeAtlas.findRegion("slime-idle-2"));
        frames.add(slimeAtlas.findRegion("slime-idle-3"));
        slimeIdleAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    private void loadSlimeAttackAnimations() {
        Array<TextureRegion> frames = new Array<>();
        frames.add(slimeAtlas.findRegion("slime-attack-0"));
        frames.add(slimeAtlas.findRegion("slime-attack-1"));
        frames.add(slimeAtlas.findRegion("slime-attack-2"));
        frames.add(slimeAtlas.findRegion("slime-attack-3"));
        slimeAttackAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    private void loadSlimeHurtAnimations() {
        Array<TextureRegion> frames = new Array<>();
        frames.add(slimeAtlas.findRegion("slime-hurt-0"));
        frames.add(slimeAtlas.findRegion("slime-hurt-1"));
        frames.add(slimeAtlas.findRegion("slime-hurt-2"));
        frames.add(slimeAtlas.findRegion("slime-hurt-3"));
        slimeHurtAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    private void loadSlimeMoveAnimations() {
        Array<TextureRegion> frames = new Array<>();
        frames.add(slimeAtlas.findRegion("slime-move-0"));
        frames.add(slimeAtlas.findRegion("slime-move-1"));
        frames.add(slimeAtlas.findRegion("slime-move-2"));
        frames.add(slimeAtlas.findRegion("slime-move-3"));
        slimeMoveAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }


    public Animation getSlimeIdleAnimation() {
        return slimeIdleAnimation;
    }

    public Animation getBarOverlayIconAnimation() {
        return barOverlayIconAnimation;
    }

    public void createButtonFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("CherryCreamSoda-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;

        buttonFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public void createCardFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("BitcountGridDoubleInk-VariableFont_CRSV,ELSH,ELXP,SZP1,SZP2,XPN1,XPN2,YPN1,YPN2,slnt,wght.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;

        cardFont = generator.generateFont(parameter);
        generator.dispose();

    }

    public void createFonts(){
        createButtonFont();
        createCardFont();
    }


    public BitmapFont getCardFont() {
        return cardFont;
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

    public Texture getArrow() {
        return arrow;
    }

    public TextureRegion getArrowRegion() {
        return arrowRegion;
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

    public TextureRegionDrawable getDrawable() {
        return drawable;
    }

    public TextureRegion getBarBackground() {
        return barBackground;
    }

    public TextureRegion getHealthBarForeground() {
        return healthBarForeground;
    }

    public Texture getButtonTextures() {
        return buttonTextures;
    }

    public TextureRegion getBarOverlayTextureRegion() {
        return barOverlayTextureRegion;
    }

    public Texture getBarBackgroundTexture() {
        return barBackgroundTexture;
    }

    public Texture getBarForegroundTexture() {
        return barForegroundTexture;
    }

    public Texture getBarOverlayTexture() {
        return barOverlayTexture;
    }

    public TextureRegion getManaBarForeground() {
        return manaBarForeground;
    }

    public BitmapFont getButtonFont() {
        return buttonFont;
    }


    public Animation getSlimeHurtAnimation() {
        return slimeHurtAnimation;
    }

    public Animation getSlimeMoveAnimation() {
        return slimeMoveAnimation;
    }

    public Animation getSlimeAttackAnimation() {
        return slimeAttackAnimation;
    }



    @Override
    public void dispose() {
        this.cardAtlas.dispose();
        this.buttonFont.dispose();
        this.cardFont.dispose();
        this.arrow.dispose();
        this.buttonTextures.dispose();

    }

    public Texture getButtonSkin() {
        return buttonSkin;
    }
}
