package io.github.some_example_name.inputs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.some_example_name.inputs.InputController.CardController;
import io.github.some_example_name.inputs.InputController.Controller;
import io.github.some_example_name.view.CardView;
import io.github.some_example_name.view.MonsterView;

import java.util.HashMap;
import java.util.Map;

public class GameInput {

    private final Map<Class<? extends Actor>, Controller<? extends Actor>> controllers =
        new HashMap<>();

    public GameInput(Stage stage) {

        controllers.put(CardView.class, new CardController());
        controllers.put(MonsterView.class, new MonsterController());

        stage.addListener(new InputListener() {

            @Override
            public boolean touchDown(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button
            ) {
                Actor actor = event.getTarget().getParent();
                if(actor == null ) return false;
                Controller controller = controllers.get(actor.getClass());

                System.out.println("touch down was pressed on " + actor.getClass());



                if (controller != null) {
                    controller.touchDown(actor, x, y);
                }


                return true;
            }



            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button
            ) {
                Actor actor = event.getTarget().getParent();

                Controller controller = controllers.get(actor.getClass());

                if (controller != null) {
                    controller.touchUp(actor, x, y);
                }
            }
        });
    }



}
