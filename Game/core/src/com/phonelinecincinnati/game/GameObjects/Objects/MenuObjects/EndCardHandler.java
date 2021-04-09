package com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Fade;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;
import com.phonelinecincinnati.game.Levels.LevelHandler;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class EndCardHandler extends GameObject {
    private CopyOnWriteArrayList<GameObject> activeObjects;
    private ArrayList<String> results;
    private GlyphLayout glyphLayout;
    private MovingText movingText = new MovingText(Renderer.hudLargeTopFont, Renderer.hudLargeBottomFont);

    private ArrayList<String> performedActions;
    private ArrayList<SwayingText> performedActionsHud;
    private SoundSource actionShowSound;

    private int timerBeforeStyleShown = 100;
    private boolean showPlayStyle = false;
    private SoundSource styleShowSound;

    public EndCardHandler(CopyOnWriteArrayList<GameObject> activeObjects,  ArrayList<String> results) {
        this.activeObjects = activeObjects;
        this.results = results;
        glyphLayout = new GlyphLayout();

        performedActions = new ArrayList<String>();
        performedActions.addAll(results.subList(3, results.size()-1));
        performedActionsHud = new ArrayList<SwayingText>();

        actionShowSound = SoundSource.buildSoundSource(0).setSound("Combat/EnemyDoorHit.wav");
        styleShowSound = SoundSource.buildSoundSource(0).setSound("Combat/EnemyDeath.wav");
    }

    @Override
    public void update() {
        movingText.update();

        glyphLayout.setText(Renderer.hudTopFont, "G");
        float spawnBaseX = Gdx.graphics.getWidth()-10;
        float spawnBaseY = glyphLayout.height + 12;
        int lastI = performedActionsHud.size()-1;

        if(performedActions.isEmpty()) {
            if(timerBeforeStyleShown < 0) {
                if(!showPlayStyle) {
                    styleShowSound.playSound();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            activeObjects.add(new Fade(false, new Action() {
                                @Override
                                public void activate() {
                                    Main.levelHandler.loadNext();
                                }
                            }, 0.04f));
                        }
                    }, 5000);
                }
                showPlayStyle = true;
                return;
            }
            timerBeforeStyleShown--;
            return;
        }

        if(performedActionsHud.isEmpty() || performedActionsHud.get(lastI).originY > spawnBaseY+glyphLayout.height+10) {
            glyphLayout.setText(Renderer.hudTopFont, performedActions.get(0));
            float x = spawnBaseX-glyphLayout.width;

            SwayingText swayingText = new SwayingText(x, spawnBaseY, performedActions.get(0), Renderer.hudTopFont);
            performedActionsHud.add(swayingText);
            activeObjects.add(swayingText);
            performedActions.remove(0);

            actionShowSound.playSound();
        }

        if(performedActionsHud.size() > 12) {
            activeObjects.remove(performedActionsHud.get(0));
            performedActionsHud.remove(0);
        }

        for(SwayingText text : performedActionsHud) {
            text.originY += 2;
        }
    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    public void postRender(Renderer renderer) {
        glyphLayout.setText(Renderer.scriptFont, results.get(2));
        float topY = Gdx.graphics.getHeight()-10;
        renderer.renderText(10, topY, results.get(0), Renderer.scriptFont);
        renderer.renderText(10, topY-10-glyphLayout.height, results.get(1), Renderer.scriptFont);

        renderer.renderText(Gdx.graphics.getWidth()-10-glyphLayout.width, topY, results.get(2), Renderer.scriptFont);

        glyphLayout.setText(Renderer.hudLargeBottomFont, results.get(results.size()-1));
        float lowerTextY = glyphLayout.height + (glyphLayout.height/2);
        glyphLayout.setText(Renderer.scriptFont, results.get(results.size()-1));

        if(showPlayStyle) {
            renderer.renderText(40, lowerTextY+glyphLayout.height+10, "Play style:", Renderer.scriptFont);
            movingText.render(renderer, 30, lowerTextY, results.get(results.size()-1));
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        return null;
    }
}
