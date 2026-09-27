package io.github.some_example_name.inputs.InputController;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface Controller<T extends Actor> {

    void touchDown(T actor, float x, float y);



    void touchUp(T actor, float x, float y);
}
