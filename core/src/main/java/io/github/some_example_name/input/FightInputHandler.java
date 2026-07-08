package io.github.some_example_name.input;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.view.BoardView;
import io.github.some_example_name.view.DraggedMonster;
import io.github.some_example_name.view.MonsterView;

public class FightInputHandler extends InputHandler {
    public MonsterView selectedMonsterView = null;

    public FightInputHandler(BoardView boardView, Viewport viewport) {
        super(boardView, viewport);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pixelsToWorld(screenX, screenY);
        MonsterView view = monsterFieldView.getMonsterAtPosition(touchPos.x, touchPos.y);

        if (view == null) return false;
        selectedMonsterView = view;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        pixelsToWorld(screenX, screenY);
        if(selectedMonsterView == null) return false;
        boardView.setDraggedMonster(new DraggedMonster(selectedMonsterView, touchPos.x,  touchPos.y));
        return true;

    }

    @Override
    protected boolean touchUp() {
        return false;
    }


}
