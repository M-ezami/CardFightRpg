package io.github.some_example_name.view;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.assets.CardAssets;
import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.cards.cardRelated.parents.Card;


public class CardView extends BaseView {
    private final Card card;
    private final CardAssets assets;

    /*
    // CardView is a table scence2D component which acts as the first layer and on top/below whatever u want to call it is the second layer.
    // this layer consists of the things that are on top of the background of a card which is our cardlayout.
    // so on the first layer/background there are other tables in the second layer.
    // for example the titletable which consists also of a background a label which is the name.
    */

    public CardView(Card card) {
        super();
        this.card = card;
        this.assets = CardAssets.getCardAssets();

    }

    @Override
    public void createUI() {

        Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getCardFont(), Color.BLACK);

        setBackground(new TextureRegionDrawable(assets.getCardOverlay()));
        setSize(0.7f, 1.4f);

        Label titleLabel = new Label(card.getName() != null ? card.getName() : "Unknown", labelStyle);
        titleLabel.setAlignment(Align.center);

        Label descriptionLabel = new Label(card.getDescription() != null ? card.getDescription() : "Unknown", labelStyle);
        descriptionLabel.setAlignment(Align.left);
        descriptionLabel.setWrap(true);

        Table titleTable = new Table();
        add(titleTable).center().expandX().fillX();
        titleTable.setBackground(new TextureRegionDrawable(assets.getTitleArea()));
        titleTable.add(titleLabel).center();


        Table descriptionTable = new Table();
        row();
        add(descriptionTable).expandX().fillX().expandY().fillY();
        descriptionTable.setBackground(new TextureRegionDrawable(assets.getTextBox()));
        descriptionTable.add(descriptionLabel);

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

