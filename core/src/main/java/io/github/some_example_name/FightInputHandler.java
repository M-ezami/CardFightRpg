package io.github.some_example_name;

import io.github.some_example_name.view.BoardView;
import io.github.some_example_name.view.MonsterView;

public class FightInputHandler extends InputHandler {
    public MonsterView selectedMonsterView = null;

    public FightInputHandler(BoardView boardView) {
        super(boardView);
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
        return false;
    }

    @Override
    protected boolean onCardReleased() {
        return false;
    }


}
