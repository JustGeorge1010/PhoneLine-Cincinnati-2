package com.phonelinecincinnati.game.Levels;

import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Condition;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class StageController extends GameObject {
    public int currentKills = 0;

    private ArrayList<Stage> stages;
    private Stage currentStage;

    StageController(String objectiveText) {
        stages = new ArrayList<Stage>();
        Main.levelHandler.player.hud.objectiveText = objectiveText;
    }

    public void addStage(Condition condition, Action doOnComplete, String objectiveText){
        stages.add(new Stage(condition, doOnComplete, objectiveText));
    }

    @Override
    public void update() {
        if(stages.isEmpty()) {
            return;
        }
        if(currentStage == null){
            currentStage = stages.get(0);
        }
        if(currentStage.condition.check()) {
            currentStage.doOnComplete.activate();
            Main.levelHandler.player.hud.objectiveText = currentStage.objectiveText;
            stages.remove(currentStage);
            currentStage = null;
        }
    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    public void postRender(Renderer renderer) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }
}
