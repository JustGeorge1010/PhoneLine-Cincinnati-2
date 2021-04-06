package com.phonelinecincinnati.game.Levels;

import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Condition;

public class Stage {
    public Condition condition;
    public Action doOnComplete;
    public String objectiveText;

    public Stage(Condition condition, Action doOnComplete, String objectiveText) {
        this.condition = condition;
        this.doOnComplete = doOnComplete;
        this.objectiveText = objectiveText;
    }
}
