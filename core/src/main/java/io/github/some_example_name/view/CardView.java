package io.github.some_example_name.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.assets.CardAssets;
import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.cards.cardRelated.parents.Card;

public class CardView extends BaseView {

    public static final float CARD_WIDTH = 220f;
    private static final float TITLE_HEIGHT = 40f;
    public static final float CARD_HEIGHT = 400f;
    private static final float IMAGE_HEIGHT = CARD_HEIGHT/2+20f;

    private static final float DESC_HEIGHT = CARD_HEIGHT- IMAGE_HEIGHT- TITLE_HEIGHT;

    private final Card card;
    private final CardAssets assets = CardAssets.getCardAssets();

    /*
    // CardView is a table scene2D component which acts as the first layer, and on top/below whatever u want to call it is the second layer.
    // this layer consists of the things that are on top of the background of a card which is our cardlayout.
    // so on the first layer/background there are other tables in the second layer.
    // for example the titletable which consists also of a background a label which is the name.
    */
    public CardView(Card card) {
        this.card = card;
        createUI();
    }

    @Override
    public void createUI() {
        setBackground(new TextureRegionDrawable(assets.getCardOverlay()));
        setClip(true);

        TextureRegionDrawable titleBg = new TextureRegionDrawable(assets.getTitleArea());
        titleBg.setLeftWidth(12f);
        titleBg.setRightWidth(12f);
        titleBg.setTopHeight(4f);
        titleBg.setBottomHeight(4f);
        Label.LabelStyle titleStyle = new Label.LabelStyle(CardAssets.getCardAssets().getCardFont(), Color.BLACK);
        titleStyle.background = titleBg;

        TextureRegionDrawable descBg = new TextureRegionDrawable(assets.getTextBox());
        descBg.setLeftWidth(16f);
        descBg.setRightWidth(16f);
        descBg.setTopHeight(12f);
        descBg.setBottomHeight(12f);
        Label.LabelStyle descStyle = new Label.LabelStyle(CardAssets.getCardAssets().getCardFont(), Color.BLACK);
        descStyle.background = descBg;

        Label titleLabel = new Label(card.getName() != null ? card.getName() : "Unknown", titleStyle);
        titleLabel.setAlignment(Align.center);
        titleLabel.setWrap(true);

        Label descriptionLabel = new Label(card.getDescription() != null ? card.getDescription() : "Unknown", descStyle);
        descriptionLabel.setWrap(true);
        descriptionLabel.setAlignment(Align.center);

        add().height(IMAGE_HEIGHT).width(CARD_WIDTH).row();
        add(titleLabel).width(CARD_WIDTH).height(TITLE_HEIGHT).row();
        add(descriptionLabel).width(CARD_WIDTH).height(DESC_HEIGHT);

        setDebug(true);
    }

    @Override
    public void refresh() {
    }

    public Card getCard() {
        return card;
    }

    public CardType getCardType() {
        return card.getCardType();
    }
}
