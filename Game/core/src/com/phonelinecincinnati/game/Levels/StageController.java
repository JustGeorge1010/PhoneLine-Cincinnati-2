package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Condition;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.MovingText;

import java.util.ArrayList;

public class StageController extends GameObject {
    public int currentKills = 0;

    private ArrayList<Stage> stages;
    private Stage currentStage;
    private String objectiveText;
    private MovingText movingText;

    private static GlyphLayout glyphLayout = new GlyphLayout();

    StageController(String objectiveText) {
        stages = new ArrayList<Stage>();
        this.objectiveText = objectiveText;
        movingText = new MovingText(Renderer.hudTopFont, Renderer.hudBottomFont);
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
            this.objectiveText = currentStage.objectiveText;
            stages.remove(currentStage);
            currentStage = null;
        }

        movingText.update();
    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    public void postRender(Renderer renderer) {
        glyphLayout.setText(renderer.hudBottomFont, objectiveText);
        float width = glyphLayout.width;
        movingText.render(renderer, (Gdx.graphics.getWidth()-width)-10, 100, objectiveText);
    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }
}
