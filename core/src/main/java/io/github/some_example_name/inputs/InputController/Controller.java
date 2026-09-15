package io.github.some_example_name.inputs.InputController;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface Controller {

    void onClick(Actor actor);
    void onDrag(Actor actor, float x, float y);
    void touchUp(Actor actor, float x, float y);
    void touchDown(Actor actor);
}
