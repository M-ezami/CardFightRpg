package io.github.some_example_name.inputs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.some_example_name.inputs.InputController.CardController;
import io.github.some_example_name.inputs.InputController.Controller;
import io.github.some_example_name.view.CardView;

import java.util.HashMap;
import java.util.Map;

public class GameInput {

    private final Map<Class<? extends Actor>, Controller> controllers = new HashMap<>();

    public GameInput(Stage stage, CardController cardController) {

        controllers.put(CardView.class, cardController);

        stage.addListener(new InputListener() {

            @Override
            public boolean touchDown(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button
            ) {
                Actor actor = event.getTarget();

                Controller controller = controllers.get(actor.getClass());

                if (controller != null) {
                    controller.onClick(actor);
                }

                return true;
            }

            @Override
            public void touchDragged(
                InputEvent event,
                float x,
                float y,
                int pointer
            ) {
                Actor actor = event.getTarget();

                Controller controller = controllers.get(actor.getClass());

                if (controller != null) {
                    controller.onDrag(actor, x, y);
                }
            }

            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button
            ) {
                Actor actor = event.getTarget();

                Controller controller = controllers.get(actor.getClass());

                if (controller != null) {
                    controller.touchUp(actor, x, y);
                }
            }
        });
    }
}
