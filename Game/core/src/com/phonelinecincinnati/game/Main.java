package com.phonelinecincinnati.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Action;
import com.phonelinecincinnati.game.Levels.LevelHandler;
import com.phonelinecincinnati.game.Models.ModelHandler;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    //todo debugVariables
    public static boolean levelEditor = false;
    public static boolean debug = false;
    public static boolean debugBlindEnemies = false;
    public static boolean debugDrawPaths = false;
    public static boolean debugDrawBounds = false;
    public static boolean debugShowPosition = true;

    public static Color backgroundColor;

    public static PerspectiveCamera camera, weaponCamera;
    public static ModelHandler modelHandler;
    public static LevelHandler levelHandler;
    public static ControlHandler controlHandler;

    public static List<Action> postUpdateSchedule;

    private Renderer renderer;

	@Override
	public void create () {
	    postUpdateSchedule = new ArrayList<Action>();

	    backgroundColor = new Color(0, 0, 0, 1);

        camera = new PerspectiveCamera(75f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 0f, -1f);
        camera.lookAt(0f, 0f, 0f);
        camera.near = 0.01f;
        camera.far = 300f;

        weaponCamera = new PerspectiveCamera(75f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        weaponCamera.position.set(0f, 0f, -1f);
        weaponCamera.lookAt(0f, 0f, 0f);
        weaponCamera.near = 0.01f;
        weaponCamera.far = 300f;

        renderer = new Renderer(camera, weaponCamera);
        modelHandler = new ModelHandler();
        levelHandler = new LevelHandler();
        controlHandler = new ControlHandler();

        if(levelEditor) {
            debug = true;
            levelHandler.loadLevelEditor("");
        } else {
            levelHandler.loadMenu();
        }

        Gdx.input.setCursorCatched(true);
        Gdx.input.setInputProcessor(controlHandler);
	}

	private void update() {
        camera.update();
        weaponCamera.update();
        levelHandler.score.update();
        for(GameObject object : levelHandler.getActiveObjects()) {
            object.update();
        }
    }

	@Override
	public void render () {
        update();

        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        //Gdx.gl.glEnable(GL20.GL_CULL_FACE); TODO: Maybe re-enable
        Gdx.gl.glCullFace(GL20.GL_FRONT);

        for(GameObject object : levelHandler.getActiveObjects()) {
            object.render(renderer);
        }
        renderer.decalBatch.flush();
        Gdx.gl20.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        for(GameObject object : levelHandler.getActiveObjects()) {
            object.postRender(renderer);
        }
        if(debugShowPosition && levelHandler.player != null && levelHandler.player.position != null) {
            renderer.renderText(10, Gdx.graphics.getHeight()-10, "X/Y/Z:"+levelHandler.player.position, renderer.scriptFont);
        }

        for(Action action : postUpdateSchedule) {
            action.activate();
        }
        postUpdateSchedule.clear();
	}
	
	@Override
	public void dispose () {
	    renderer.dispose();

	    levelHandler.dispose();
	    modelHandler.dispose();
	}
}
