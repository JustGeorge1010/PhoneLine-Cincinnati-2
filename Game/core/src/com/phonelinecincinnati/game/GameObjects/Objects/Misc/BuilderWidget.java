package com.phonelinecincinnati.game.GameObjects.Objects.Misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.*;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.Model;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Vertical.Stairs;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.Direction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

enum Base {
    Plane,
    Wall
}

enum BuildMode {
    Place,
    Delete
}

enum BrushMode {
    Base,
    Model,
    Decal,
    Stairs
}

public class BuilderWidget extends GameObject{
    private Thread inputThread;

    private int textureIndex = 1;
    private TextureName textureName = TextureName.values()[textureIndex];
    private int secondaryTextureIndex = 1;
    private TextureName secondaryTextureName = TextureName.values()[secondaryTextureIndex];

    private int modelIndex;

    private boolean isSolid = true;

    private BrushMode brushMode = BrushMode.Base;
    private BuildMode buildMode = BuildMode.Place;
    private int deleteIndex = 0;

    private Vector3 size = new Vector3(1, 0.1f, 1);
    private boolean hardRotation = true;
    private int rotationX;
    private int rotationY;
    private int rotationZ;

    private Base selectedBase = Base.Plane;
    private GameObject currentObject;

    private float gridLockAmount = 1;

    private BufferedReader inputReader;

    private String levelName = "";

    public BuilderWidget() {
        position = new Vector3();

        inputReader = new BufferedReader(new InputStreamReader(System.in));
        inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        processCommand(inputReader.readLine());
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    } catch (IOException ignored) {
                        ignored.printStackTrace();
                    }
                }
            }
        });
        inputThread.start();
    }

    private void processCommand(String command) {
        String[] commandParts = command.split(" ");

        String base = commandParts[0];
        if(base.equals("help") || base.equals("h")) {
            System.out.println("Controls");
            System.out.println("ls [Bases, Textures]");
            System.out.println("BrushMode [Base, Model]");
            System.out.println("SetTexture [Texture]");
            System.out.println("SetGridLock [Float]");
            System.out.println("SetSize [x] [y] [z]");
            System.out.println("Save [LevelName]");
            System.out.println("Load [LevelName]");
        }
        else if(base.equals("Controls")) {
            System.out.println("Arrow keys: Move builder");
            System.out.println("Numpad 8/5: Raise/Lower move builder");
            System.out.println("Pg Up/Down: Change wall height");
            System.out.println("R: Rotate by 90");
            System.out.println("CTRL + R: Rotate by 1");
            System.out.println("Delete: switch between placement and delete modes");
            System.out.println("Numpad 4/6: Cycle through Base tools (Wall, Plane)");
            System.out.println("Numpad 2: toggle object solidness (Can be collided with)");
            System.out.println("Numpad 1/3: increase/decrease grid units");
            System.out.println("Numpad + -: increase Base tool size by 1 grid unit");
            System.out.println("Numpad 7/9: Cycle through brush modes (Base, Model");
            System.out.println("Square Brackets: cycle through variants (Textures/Models/Objects to delete)");
            System.out.println("CTRL + Square Brackets: cycle through alternate variants");
            System.out.println("Enter: Place/Delete Object");
        }
        else if(base.equals("ls")) {
            String option;
            try {
                option = commandParts[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Command is used like: ls [Items, Textures]");
                System.out.println("Type help (h) for more details");
                return;
            }
            if(option.equals("Bases")) {
                for(Base b : Base.values()) {
                    System.out.println(b);
                }
            }
            else if(option.equals("Textures")) {
                for(TextureName texture : TextureName.values()) {
                    System.out.println(texture);
                }
            }
        }
        else if(base.equals("BrushMode")) {
            try {
                brushMode = BrushMode.valueOf(commandParts[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Command is used like: BrushMode [Base, Model]");
                System.out.println("Type help (h) for more details");
            }
        }
        else if(base.equals("SetTexture")) {
            try {
                textureName = TextureName.valueOf(commandParts[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Command is used like: SetTexture [Texture]");
                System.out.println("Type help (h) for more details");
            } catch (IllegalArgumentException e) {
                System.out.println("No such texture "+commandParts[1]+"!");
                System.out.println("Use 'ls Textures' to list all Textures");
                System.out.println("Type help (h) for more details");
            }
        }
        else if(base.equals("SetGridLock")) {
            try {
                float amount = Float.parseFloat(commandParts[1]);
                if(amount == Float.NaN) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                gridLockAmount = amount;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Command is used like: SetGridLock [Float]");
                System.out.println("Type help (h) for more details");
            }
        }
        else if(base.equals("SetSize")) {
            try {
                float x = Float.parseFloat(commandParts[1]);
                float y = Float.parseFloat(commandParts[2]);
                float z = Float.parseFloat(commandParts[3]);
                //noinspection ConstantConditions
                if(x == Float.NaN || y == Float.NaN || z == Float.NaN) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                size = new Vector3(x, y, z);
            } catch (Exception e) {
                System.out.println("Command is used like: SetSize [x] [y] [z]");
                System.out.println("Type help (h) for more details");
            }
        }

        else if (base.equals("Save")) {
            try {
                String levelName = commandParts[1];
                Main.levelHandler.SaveToJson(levelName);
                System.out.println("Successfully saved level to '"+levelName+"'");
            } catch (Exception e) {
                System.out.println("Command is used like: Save [LevelName]");
                System.out.println("Type help (h) for more details");
            }
        }
        else if (base.equals("Load")) {
            try {
                levelName = commandParts[1];
            } catch (Exception e) {
                System.out.println("Command is used like: Load [LevelName]");
                System.out.println("Type help (h) for more details");
            }
        }
    }

    @Override
    public void update() {
        if(!levelName.equals("")) {
            inputThread.interrupt();
            Main.levelHandler.loadLevelEditor(levelName);
            System.out.println("Loaded level '"+levelName+"'");
            levelName = "";
        }

        position.x = Math.round(position.x/gridLockAmount)*gridLockAmount;
        position.y = Math.round(position.y/gridLockAmount)*gridLockAmount;
        position.z = Math.round(position.z/gridLockAmount)*gridLockAmount;

        size.x = Math.round(size.x/gridLockAmount)*gridLockAmount;
        size.z = Math.round(size.z/gridLockAmount)*gridLockAmount;

        handleKeyInput();

        if(buildMode == BuildMode.Place) {
            setSelectedObject();
        }
        else {
            getSelectedObject();
        }

        if(Main.controlHandler.getKeyTapped(Input.Keys.ENTER)) {
            if(buildMode == BuildMode.Place) {
                Main.levelHandler.addObjectToCurrentLevel(currentObject);
            }
            else {
                Main.levelHandler.getActiveObjects().remove(currentObject);
            }
        }
    }

    private void handleKeyInput() {
        applyTransforms();

        if(Main.controlHandler.getKeyTapped(Input.Keys.NUMPAD_2)) {
            isSolid = !isSolid;
        }

        if(Main.controlHandler.getKeyTapped(Input.Keys.NUMPAD_7) ||
                Main.controlHandler.getKeyTapped(Input.Keys.NUMPAD_9)) {
            if(brushMode == BrushMode.Base) {
                brushMode = BrushMode.Model;
            }
            else if(brushMode == BrushMode.Model) {
                brushMode = BrushMode.Decal;
            }
            else if(brushMode == BrushMode.Decal) {
                brushMode = BrushMode.Stairs;
            }
            else {
                brushMode = BrushMode.Base;
            }
        }

        if(Main.controlHandler.getKeyTapped(Input.Keys.FORWARD_DEL)) {
            if(buildMode == BuildMode.Place) {
                buildMode = BuildMode.Delete;
            }
            else {
                buildMode = BuildMode.Place;
            }
        }

        int brushIncrement = 0;
        if(Main.controlHandler.getKeyTapped(Input.Keys.LEFT_BRACKET))
            if(Main.controlHandler.keys.get(Input.Keys.CONTROL_LEFT)) {
                secondaryTextureIndex--;
            }
            else {
                brushIncrement--;
            }
        else if(Main.controlHandler.getKeyTapped(Input.Keys.RIGHT_BRACKET)) {
            if(Main.controlHandler.keys.get(Input.Keys.CONTROL_LEFT)) {
                secondaryTextureIndex++;
            }
            else {
                brushIncrement++;
            }
        }

        if (secondaryTextureIndex > TextureName.values().length-1) {
            secondaryTextureIndex = 0;
        } else if(secondaryTextureIndex < 0) {
            secondaryTextureIndex = TextureName.values().length-1;
        }
        secondaryTextureName = TextureName.values()[secondaryTextureIndex];

        if(buildMode == BuildMode.Delete) {
            deleteIndex += brushIncrement;
        }
        else if(brushMode == BrushMode.Base) {
            textureIndex += brushIncrement;
            if(textureIndex < 0) {
                textureIndex = TextureName.values().length-1;
            }
            else if(textureIndex >= TextureName.values().length) {
                textureIndex = 0;
            }
            textureName = TextureName.values()[textureIndex];

            if(Main.controlHandler.getKeyTapped(Input.Keys.NUMPAD_6) ||
                    Main.controlHandler.getKeyTapped(Input.Keys.NUMPAD_4)) {
                if(selectedBase == Base.Plane) {
                    size.y = 6f;
                    selectedBase = Base.Wall;
                } else {
                    size.y = 0.1f;
                    selectedBase = Base.Plane;
                }
            }
        }
        else if(brushMode == BrushMode.Model) {
            modelIndex += brushIncrement;
            if(modelIndex < 0) {
                modelIndex = ModelName.values().length-1;
            }
            else if(modelIndex >= ModelName.values().length) {
                modelIndex = 0;
            }
        }
        else if(brushMode == BrushMode.Decal) {
            textureIndex += brushIncrement;
            if(textureIndex < 0) {
                textureIndex = TextureName.values().length-1;
            }
            else if(textureIndex >= TextureName.values().length) {
                textureIndex = 0;
            }
            textureName = TextureName.values()[textureIndex];
        }

        if(Main.controlHandler.getKeyTapped(Input.Keys.NUMPAD_1)) {
            gridLockAmount -= 0.1f;
        }
        else if(Main.controlHandler.getKeyTapped(Input.Keys.NUMPAD_3)) {
            gridLockAmount += 0.1f;
        }
        if(gridLockAmount <= 0) {
            gridLockAmount = 0.1f;
        }
    }

    private void applyTransforms() {
        Vector3 camDirVector = Main.camera.direction;
        Direction lookDir;
        if(Math.abs(camDirVector.x) > Math.abs(camDirVector.z)) {
            if(camDirVector.x > 0) {
                lookDir = Direction.West;
            } else {
                lookDir = Direction.East;
            }
        }
        else {
            if(camDirVector.z > 0) {
                lookDir = Direction.North;
            } else {
                lookDir = Direction.South;
            }
        }

        if(Main.controlHandler.getKeyTapped(Input.Keys.LEFT)) {
            switch (lookDir) {
                case North:
                    position.x += gridLockAmount;
                    break;
                case South:
                    position.x -= gridLockAmount;
                    break;
                case East:
                    position.z += gridLockAmount;
                    break;
                case West:
                    position.z -= gridLockAmount;
                    break;
            }
        }
        if(Main.controlHandler.getKeyTapped(Input.Keys.RIGHT)) {
            switch (lookDir) {
                case North:
                    position.x -= gridLockAmount;
                    break;
                case South:
                    position.x += gridLockAmount;
                    break;
                case East:
                    position.z -= gridLockAmount;
                    break;
                case West:
                    position.z += gridLockAmount;
                    break;
            }
        }
        if(Main.controlHandler.getKeyTapped(Input.Keys.UP)) {
            switch (lookDir) {
                case North:
                    position.z += gridLockAmount;
                    break;
                case South:
                    position.z -= gridLockAmount;
                    break;
                case East:
                    position.x -= gridLockAmount;
                    break;
                case West:
                    position.x += gridLockAmount;
                    break;
            }
        }
        if(Main.controlHandler.getKeyTapped(Input.Keys.DOWN)) {
            switch (lookDir) {
                case North:
                    position.z -= gridLockAmount;
                    break;
                case South:
                    position.z += gridLockAmount;
                    break;
                case East:
                    position.x += gridLockAmount;
                    break;
                case West:
                    position.x -= gridLockAmount;
                    break;
            }
        }
        if(Main.controlHandler.keys.get(Input.Keys.CONTROL_LEFT)) {
            if(Main.controlHandler.getKeyTapped(Input.Keys.R)) {
                hardRotation = false;
                rotationY += 1;
            }
            else if(Main.controlHandler.getKeyTapped(Input.Keys.T)) {
                hardRotation = false;
                rotationZ += 1;
            }
            else if(Main.controlHandler.getKeyTapped(Input.Keys.G)) {
                hardRotation = false;
                rotationX += 1;
            }
        }
        else {
            if(Main.controlHandler.getKeyTapped(Input.Keys.R)) {
                if(hardRotation) {
                    rotationY += 90;
                } else {
                    hardRotation = true;
                    rotationY = Math.round(rotationY/90f)*90;
                }
            }
            else if(Main.controlHandler.getKeyTapped(Input.Keys.T)) {
                if(hardRotation) {
                    rotationZ += 90;
                } else {
                    hardRotation = true;
                    rotationZ = Math.round(rotationZ/90f)*90;
                }
            }
            else if(Main.controlHandler.getKeyTapped(Input.Keys.G)) {
                if(hardRotation) {
                    rotationX += 90;
                } else {
                    hardRotation = true;
                    rotationX = Math.round(rotationX/90f)*90;
                }
            }
        }
        if(rotationY >= 360) rotationY = 0;
        if(rotationZ >= 360) rotationZ = 0;
        if(rotationX >= 360) rotationX = 0;

        if(Main.controlHandler.getKeyTapped(Input.Keys.PLUS)) {
            if(Main.controlHandler.keys.get(Input.Keys.CONTROL_LEFT)) {
                if(lookDir == Direction.North || lookDir == Direction.South) {
                    size.z += gridLockAmount;
                }
                else {
                    size.x += gridLockAmount;
                }
            } else {
                if(lookDir == Direction.North || lookDir == Direction.South) {
                    size.x += gridLockAmount;
                }
                else {
                    size.z += gridLockAmount;
                }
            }
        }
        else if(Main.controlHandler.getKeyTapped(Input.Keys.MINUS)) {
            if(Main.controlHandler.keys.get(Input.Keys.CONTROL_LEFT)) {
                if(lookDir == Direction.North || lookDir == Direction.South) {
                    size.z -= gridLockAmount;
                }
                else {
                    size.x -= gridLockAmount;
                }
            } else {
                if(lookDir == Direction.North || lookDir == Direction.South) {
                    size.x -= gridLockAmount;
                }
                else {
                    size.z -= gridLockAmount;
                }
            }
        }

        if(Main.controlHandler.getKeyTapped(Input.Keys.PAGE_UP)) {
            size.y += gridLockAmount;
            size.y = Math.round(size.y/gridLockAmount)*gridLockAmount;
        }
        else if(Main.controlHandler.getKeyTapped(Input.Keys.PAGE_DOWN)) {
            size.y -= gridLockAmount;
            size.y = Math.round(size.y/gridLockAmount)*gridLockAmount;
        }

        if(Main.controlHandler.getKeyTapped(Input.Keys.NUMPAD_8)) {
            position.y += gridLockAmount;
        }
        else if(Main.controlHandler.getKeyTapped(Input.Keys.NUMPAD_5)) {
            position.y -= gridLockAmount;
        }
    }

    private void setSelectedObject() {
        if(brushMode == BrushMode.Base) {
            if(selectedBase == Base.Plane) {
                currentObject = new Plane(new Vector3(position), new Vector3(size), textureName);
            }
            else if(selectedBase == Base.Wall) {
                Vector3 wallSize;
                if(rotationY == 0 || rotationY == 180) {
                    wallSize = new Vector3(size.x, size.y, 0.5f);
                } else {
                    wallSize = new Vector3(0.5f, size.y, size.x);
                }
                currentObject = new DoubleWall(new Vector3(position), wallSize, textureName, secondaryTextureName);
            }
        }
        else if(brushMode == BrushMode.Model) {
            if(isSolid) {
                currentObject = new SolidModel(position.cpy(), rotationY, ModelName.values()[modelIndex]);
            }
            else {
                currentObject = new Model(position.cpy(), rotationY, ModelName.values()[modelIndex]);
            }
        }
        else if(brushMode == BrushMode.Decal) {
            Vector3 rot = new Vector3(rotationX, rotationY, rotationZ);
            currentObject = new GameDecal(position.cpy(), size.x, size.z, rot, textureName);
        }
        else if(brushMode == BrushMode.Stairs) {
            Direction direction = Direction.North;
            if(rotationY == 90) {
                direction = Direction.East;
            }
            else if(rotationY == 180) {
                direction = Direction.South;
            }
            else if(rotationY == 270) {
                direction = Direction.West;
            }

            //noinspection SuspiciousNameCombination
            currentObject = new Stairs(true, direction, position.cpy(), (int)size.y, size.x);
        }
    }

    private void getSelectedObject() {
        float smallestDistance = Float.MAX_VALUE;
        List<GameObject> closestObjects = new ArrayList<GameObject>();
        for(GameObject gameObject : Main.levelHandler.getActiveObjects()) {
            if(gameObject.getClass() == BuilderWidget.class) {
                continue;
            }
            else if(gameObject.getClass() == Player.class) {
                continue;
            }

            float distanceToPointer = gameObject.position.dst(position);
            if(distanceToPointer < smallestDistance) {
                closestObjects.clear();
                smallestDistance = distanceToPointer;
                closestObjects.add(gameObject);
            }
            else if(distanceToPointer == smallestDistance) {
                closestObjects.add(gameObject);
            }
        }

        if(closestObjects.size() != 0) {
            if(deleteIndex < 0) {
                deleteIndex = 0;
            }
            else if(deleteIndex >= closestObjects.size()) {
                deleteIndex = closestObjects.size()-1;
            }
            currentObject = closestObjects.get(deleteIndex);
        }
        else {
            currentObject = null;
        }
    }

    @Override
    public void render(Renderer renderer) {
        ModelInstance grid = Main.modelHandler.getSharedGrid(gridLockAmount);
        grid.transform.setToTranslation(position.x, position.y, position.z);
        renderer.renderModel(grid);
        if(buildMode == BuildMode.Place && currentObject != null) {
            currentObject.render(renderer);
        }
    }

    @Override
    public void postRender(Renderer renderer) {
        ModelInstance sphere = Main.modelHandler.getSphere(new Vector3(0.2f, 0.2f, 0.2f));
        sphere.transform.setToTranslation(position.x, position.y, position.z);
        renderer.renderModel(sphere);
        renderer.renderText(10, Gdx.graphics.getHeight()-10, "gridLockAmount="+gridLockAmount, renderer.scriptFont);
        renderer.renderText(10, Gdx.graphics.getHeight()-60, "isSolid="+isSolid, renderer.scriptFont);
        renderer.renderText(10, Gdx.graphics.getHeight()-110, "BuildMode="+buildMode.toString(), renderer.scriptFont);
        String rotStr = "Rotation="+ rotationY;
        if(brushMode == BrushMode.Decal) {
            rotStr += ","+ rotationZ;
        }
        renderer.renderText(10, Gdx.graphics.getHeight()-160, rotStr, renderer.scriptFont);
        if(buildMode == BuildMode.Delete && currentObject != null) {
            renderer.renderText(10, Gdx.graphics.getHeight()-210, "ObjectToDelete="+currentObject.getClass().getSimpleName(), renderer.scriptFont);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }
}
