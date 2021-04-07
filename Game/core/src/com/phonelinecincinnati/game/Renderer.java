package com.phonelinecincinnati.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Renderer {
    private PerspectiveCamera camera, weaponCamera;

    private ModelBatch modelBatch;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private Environment environment;

    public DecalBatch decalBatch;

    public static BitmapFont defaultFont, scriptFont, hudTopFont, hudBottomFont, hudLargeTopFont, hudLargeBottomFont;

    Renderer(PerspectiveCamera camera, PerspectiveCamera weaponCamera) {
        this.camera = camera;
        this.weaponCamera = weaponCamera;

        modelBatch = new ModelBatch();

        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

        decalBatch = new DecalBatch(new CameraGroupStrategy(camera));

        defaultFont = new BitmapFont();
        defaultFont.setColor(Color.BLACK);
        defaultFont.getData().scale(1.5f);

        scriptFont = new BitmapFont(Gdx.files.internal("Fonts/InGame/Text.fnt"));
        scriptFont.setColor(Color.WHITE);
        scriptFont.getData().scale(0.5f);

        hudTopFont = new BitmapFont(Gdx.files.internal("Fonts/InGame/HudTop.fnt"));
        hudTopFont.setColor(Color.WHITE);
        hudTopFont.getData().scale(2f);

        hudBottomFont = new BitmapFont(Gdx.files.internal("Fonts/InGame/HudBottom.fnt"));
        hudBottomFont.setColor(Color.WHITE);
        hudBottomFont.getData().scale(2f);

        hudLargeTopFont = new BitmapFont(Gdx.files.internal("Fonts/InGame/HudTop.fnt"));
        hudLargeTopFont.setColor(Color.WHITE);
        hudLargeTopFont.getData().scale(4f);

        hudLargeBottomFont = new BitmapFont(Gdx.files.internal("Fonts/InGame/HudBottom.fnt"));
        hudLargeBottomFont.setColor(Color.WHITE);
        hudLargeBottomFont.getData().scale(4f);
    }

    public void setBackgroundColor(float r, float g, float b, float a) {
        Main.backgroundColor.set(r, g, b, a);
    }

    public void setBackgroundColor(Color color) {
        Main.backgroundColor.set(color.a, color.g, color.b, color.a);
    }

    public void setAmbientLight(float red, float green, float blue, float alpha) {
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, red, green, blue, alpha));
    }

    public void renderModel(ModelInstance model) {
        modelBatch.begin(camera);
        modelBatch.render(model, environment);
        modelBatch.end();
    }

    public void renderModelToScreen(ModelInstance model) {
        modelBatch.begin(weaponCamera);
        modelBatch.render(model, environment);
        modelBatch.end();
    }

    public void renderSprite(float x, float y, float width, float height, Sprite sprite) {
        spriteBatch.begin();
        spriteBatch.draw(sprite, x, y, width, height);
        spriteBatch.end();
    }

    public void renderRect(float x, float y, float width, float height, ShapeRenderer.ShapeType type, Color color) {
        shapeRenderer.begin(type);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    public void renderTransparentRect(float x, float y, float width, float height, ShapeRenderer.ShapeType type, Color color) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderRect(x, y, width, height, type, color);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void renderText(float x, float y, String text, BitmapFont font) {
        spriteBatch.begin();
        font.draw(spriteBatch, text, x, y);
        spriteBatch.end();
    }

    public void dispose() {
        modelBatch.dispose();
        spriteBatch.dispose();
    }
}
