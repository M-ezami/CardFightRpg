package io.github.some_example_name.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.cards.cardRelated.CardType;
import io.github.some_example_name.cards.cardRelated.parents.Card;
import io.github.some_example_name.ui.Assets;

public class CardView extends Table {
    private final Card card;
    private final Label titleLabel;
    private final Label descriptionLabel;

    private final Table titleTable;
    private final Table descriptionTable;



    // CardView is a table scence2D component which acts as the first layer and on top/below whatever u want to call it is the second layer.
    // this layer consists of the things that are on top of the background of a card which is our cardlayout.
    // so on the first layer/background there are other tables in the second layer.
    // for example the titletable which consists also of a background a label which is the name.
        public CardView(Card card, Assets assets) {
        this.card = card;
        Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getCardFont(), Color.BLACK);

        setBackground(new TextureRegionDrawable(assets.getCardOverlay()));

        titleLabel = new Label(card.getName() != null ? card.getName() : "Unknown", labelStyle);
        titleLabel.setAlignment(Align.center);

        descriptionLabel = new Label(card.getDescription() != null ? card.getName() : "Unknown", labelStyle);
        descriptionLabel.setAlignment(Align.left);

        titleTable = new Table();
        titleTable.center();
        titleTable.setBackground(new TextureRegionDrawable(assets.getTitleAreaAsset()));
        titleTable.add(titleLabel).center();

        descriptionTable = new Table();
        descriptionTable.bottom();
        descriptionTable.setBackground(new TextureRegionDrawable(assets.getTextBox()));
        descriptionTable.add(descriptionLabel);



    }



    public Card getCard() {
        return card;
    }

    public CardType getCardType() {
        return card.getCardType();
    }
}
