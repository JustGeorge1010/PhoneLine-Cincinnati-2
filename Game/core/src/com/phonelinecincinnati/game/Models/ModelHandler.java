package com.phonelinecincinnati.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.graphics.VertexAttributes.*;

public class ModelHandler {
    private ModelLoader modelLoader;
    private ModelBuilder modelBuilder;

    private Map<ModelName, Model> models;
    public Map<TextureName, Texture> textures;

    private ArrayList<Model> runtimeModels;

    private Model xyzGrid;
    private Model sharedGrid;

    public ModelHandler() {
        modelBuilder = new ModelBuilder();
        modelLoader = new ObjLoader();
        models = new HashMap<ModelName, Model>();
        textures = new HashMap<TextureName, Texture>();

        setTextures();

        runtimeModels = new ArrayList<Model>();
    }

    public void setupAllModels() {
        models.put(ModelName.nul, modelBuilder.createBox(1, 1, 1,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                Usage.Position| Usage.Normal
        ));

        HouseholdItems();
        TotallyRad();
        KitchenItems();
        BathroomItems();
        Clothing();
        OfficeItems();

        //Misc
        putModel("Misc", ModelName.GravelPieces);
        putModel("Misc", ModelName.TrashPiece);

        //Car
        putModel("Vehicles/PlayerCar", ModelName.PlayerCar);
        putModel("Vehicles/PlayerCar", ModelName.PlayerCarDoor);

        putModel("Level1", ModelName.SubwayTracks);
        putModel("Level1/SubwayBenches", ModelName.SubwayBench1);
        putModel("Level1/SubwayBenches", ModelName.SubwayBench2);
        putModel("Level1/SubwayBenches", ModelName.SubwayBench3);

        addMafia();

        //Structure
        putModel("Wall/Passages", ModelName.OpeningFrame);
        putModel("Wall/Passages", ModelName.Door);
        putModel("Wall/Passages", ModelName.DoorFrame);
        putModel("Wall/Passages", ModelName.DoubleDoorFrame);
        putModel("Wall/Corners", ModelName.Pillar);
        putModel("Wall/Glass", ModelName.LargeWindow);
        putModel("Wall/Glass", ModelName.FullWindow);
        putModel("Misc", ModelName.Step);
        putModel("Misc", ModelName.StepCover);

        //Weapons
        putModel("Weapons", ModelName.Bat);
        putModel("Weapons", ModelName.GolfClub);
        putModel("Weapons", ModelName.M16);

        putModel("Weapons", ModelName.Bullet);
    }

    private void putModel(String category, ModelName name) {
        if(!category.equals("")) {
            category = category+"/";
        }
        models.put(name, modelLoader.loadModel(Gdx.files.internal("Models/"+category+name.toString()+"/"+name.toString()+".obj")));
    }

    //Todo: remove
    private void OldHome() {
        /*
        //Bedroom
        putModel("PlayerHome", ModelName.Bed);
        putModel("PlayerHome", ModelName.Bed2);
        putModel("PlayerHome", ModelName.Table);
        putModel("PlayerHome", ModelName.TableLamp);
        putModel("PlayerHome", ModelName.NES);
        putModel("PlayerHome", ModelName.TV);
        //Kitchen
        putModel("PlayerHome", ModelName.KitchenTable);
        putModel("PlayerHome", ModelName.KitchenChair);
        putModel("PlayerHome", ModelName.KitchenFridge);
        putModel("PlayerHome", ModelName.KitchenSink);
        putModel("PlayerHome", ModelName.KitchenCabinet);
        putModel("PlayerHome", ModelName.KitchenCabinetOverhead);
        //Hallway
        putModel("PlayerHome", ModelName.BloodyJacket);
        putModel("PlayerHome", ModelName.DoorMat);
        //Bathroom
        putModel("PlayerHome", ModelName.MiamiShirt);
        putModel("PlayerHome", ModelName.BathTub);
        putModel("PlayerHome", ModelName.Sink);
        putModel("PlayerHome", ModelName.WallShower);
        //Living room
        putModel("PlayerHome", ModelName.GreenSofa);
        putModel("PlayerHome", ModelName.MaroonCarpet);
        putModel("PlayerHome", ModelName.LivingRoomTable);
        putModel("PlayerHome/PhoneTable", ModelName.PhoneTable1);
        putModel("PlayerHome/PhoneTable", ModelName.PhoneTable2);
        putModel("PlayerHome/PhoneTable", ModelName.PhoneTable3);
        //Landing
        putModel("PlayerHome", ModelName.Box);
        putModel("PlayerHome", ModelName.BoxOpen);

        putModel("PlayerHome/PlayerStairs", ModelName.PlayerStairsP1);
        putModel("PlayerHome/PlayerStairs", ModelName.PlayerStairsP2);
        putModel("PlayerHome/PlayerStairs", ModelName.PlayerStairsP3);
        putModel("PlayerHome/PlayerStairs", ModelName.PlayerStairsP4);
         */
    }

    private void HouseholdItems() {
        //Bedroom
        putModel("HouseholdItems", ModelName.MasterBed);
        putModel("HouseholdItems", ModelName.SingleBed);
        putModel("HouseholdItems", ModelName.Crib);
        putModel("HouseholdItems", ModelName.MobileBase);
        putModel("HouseholdItems", ModelName.MobilePart);
        putModel("HouseholdItems", ModelName.TableLamp);
        putModel("HouseholdItems", ModelName.Wardrobe);
        putModel("HouseholdItems", ModelName.Drawers);
        putModel("HouseholdItems", ModelName.WoodenChair);
        putModel("HouseholdItems", ModelName.Shelf);
        putModel("HouseholdItems", ModelName.Radiator);
        putModel("HouseholdItems", ModelName.LongMirror);
        putModel("HouseholdItems", ModelName.WideMirror);
        putModel("HouseholdItems", ModelName.VanityTable);
        putModel("HouseholdItems", ModelName.BlackSofa);
        putModel("HouseholdItems", ModelName.GreenSofa);
        putModel("HouseholdItems", ModelName.GreenArmchair);
        putModel("HouseholdItems", ModelName.WritingTable);
        putModel("HouseholdItems", ModelName.Bookshelf);
        putModel("HouseholdItems", ModelName.TrophyCase);
        putModel("HouseholdItems", ModelName.Noteboard);
        putModel("HouseholdItems", ModelName.WhiskeyShelf);
        putModel("HouseholdItems", ModelName.DoorMat);
        putModel("HouseholdItems", ModelName.LivingRoomTable);
        putModel("HouseholdItems", ModelName.MaroonCarpet);
        putModel("HouseholdItems", ModelName.Divider);

        putModel("HouseholdItems/PhoneTable", ModelName.PhoneTable1);
        putModel("HouseholdItems/PhoneTable", ModelName.PhoneTable2);
        putModel("HouseholdItems/PhoneTable", ModelName.PhoneTable3);
    }

    private void TotallyRad() {
        putModel("TotallyRad", ModelName.TV);
        putModel("TotallyRad", ModelName.LargeTV);
        putModel("TotallyRad", ModelName.NES);
        putModel("TotallyRad", ModelName.PlayMat);
        putModel("TotallyRad", ModelName.Stereo);
    }

    private void KitchenItems() {
        putModel("KitchenItems", ModelName.Fridge);
        putModel("KitchenItems", ModelName.KitchenSink);
        putModel("KitchenItems", ModelName.KitchenCabinet);
        putModel("KitchenItems", ModelName.KitchenCabinetOverhead);
        putModel("KitchenItems", ModelName.KitchenCabinetCorner);
        putModel("KitchenItems", ModelName.Oven);
        putModel("KitchenItems", ModelName.ExtractorCabinet);
        putModel("KitchenItems", ModelName.CoffeeMachine);
        putModel("KitchenItems", ModelName.Kettle);
        putModel("KitchenItems", ModelName.DiningTable);
        putModel("KitchenItems", ModelName.WaterCooler);
    }

    private void BathroomItems() {
        putModel("BathroomItems", ModelName.Shower);
        putModel("BathroomItems", ModelName.ShowerDoor);
        putModel("BathroomItems", ModelName.WallShower);
        putModel("BathroomItems", ModelName.BathTub);
        putModel("BathroomItems", ModelName.Sink);
        putModel("BathroomItems", ModelName.Toilet);
        putModel("BathroomItems", ModelName.ToiletRoll);
        putModel("BathroomItems", ModelName.WashingMachine);
        putModel("BathroomItems", ModelName.BathroomShelf);
    }

    private void IndustrialItems() {
        putModel("IndustrialItems", ModelName.ShippingCrateBlue);
        putModel("IndustrialItems", ModelName.ShippingCrateGrey);
        putModel("IndustrialItems", ModelName.ShippingCrateRed);
        putModel("IndustrialItems", ModelName.ShippingCrateYellow);
    }

    private void Clothing() {
        putModel("Clothing", ModelName.MiamiShirt);
        putModel("Clothing", ModelName.BloodyShirt);
        putModel("Clothing", ModelName.PinkShirt);
        putModel("Clothing", ModelName.BlueShirt);
        putModel("Clothing", ModelName.WhiteShirt);
    }

    private void OfficeItems() {
        putModel("OfficeItems", ModelName.ElevatorDoor);
        putModel("OfficeItems", ModelName.StandingLight);
        putModel("OfficeItems", ModelName.CeilingLight);
        putModel("OfficeItems", ModelName.OrnatePillar);
        putModel("OfficeItems", ModelName.WorkCubicle1);
        putModel("OfficeItems", ModelName.WorkCubicle2);
        putModel("OfficeItems", ModelName.CubicleEnd);
        putModel("OfficeItems", ModelName.FrontDesk);
    }

    private void addMafia() {
        putModel("Enemies/Mafia", ModelName.EnemyBody);
        putModel("Enemies/Mafia", ModelName.EnemyHead);
        putModel("Enemies/Mafia", ModelName.UpperArm);
        putModel("Enemies/Mafia", ModelName.LowerLeftArm);
        putModel("Enemies/mafia", ModelName.LowerRightArm);
        putModel("Enemies/Mafia", ModelName.UpperLeg);
        putModel("Enemies/Mafia", ModelName.LowerLeg);

        putModel("Enemies/Mafia/Down", ModelName.EnemyBack1);
        putModel("Enemies/Mafia/Down", ModelName.EnemyBluntKill1);
        putModel("Enemies/Mafia/Down", ModelName.EnemyBluntKill2);
        putModel("Enemies/Mafia/Down", ModelName.EnemyBluntKill3);

        putModel("Enemies/Mafia/Wall", ModelName.EnemyWall);
        putModel("Enemies/Mafia/Wall", ModelName.EnemyWallDead);
    }

    private void setTextures() {
        addWrappedTexture("Floor", TextureName.FakeLimbo);
        addWrappedTexture("Floor", TextureName.Wood);
        addWrappedTexture("Floor", TextureName.BrownGrayKitchenTile);
        addWrappedTexture("Floor", TextureName.GrayCarpet);
        addWrappedTexture("Floor", TextureName.RedCarpet);
        addWrappedTexture("Floor", TextureName.DarkGreenCarpet);
        addWrappedTexture("Floor", TextureName.BrownSmallTiles);
        addWrappedTexture("Floor", TextureName.BrownTinyTiles);
        addWrappedTexture("Floor", TextureName.GreenSmallTiles);
        addWrappedTexture("Floor", TextureName.DarkBrownCheckeredTiles);
        addWrappedTexture("Floor", TextureName.BlackWhiteCheckeredTiles);
        addWrappedTexture("Floor", TextureName.DarkGreenDiamonds);
        addWrappedTexture("Floor", TextureName.LargeWhiteTile);
        addWrappedTexture("Floor", TextureName.LargeGreyTile);
        addWrappedTexture("Floor", TextureName.Road);

        addWrappedTexture("Wall", TextureName.Concrete1);
        addWrappedTexture("Wall", TextureName.Brick);
        addWrappedTexture("Wall", TextureName.FloralBeige);
        addWrappedTexture("Wall", TextureName.ArtisticBeige);
        addWrappedTexture("Wall", TextureName.Cool);

        addWrappedTexture("Roof", TextureName.Concrete);
        addWrappedTexture("Roof", TextureName.WhiteDiagonal);

        textures.put(TextureName.RoadFade1, new Texture(Gdx.files.internal("Textures/Decals/RoadFade1.png")));
        textures.put(TextureName.RoadFade2, new Texture(Gdx.files.internal("Textures/Decals/RoadFade2.png")));
        textures.put(TextureName.Dirt1, new Texture(Gdx.files.internal("Textures/Decals/Dirt/Dirt1.png")));
        textures.put(TextureName.Dirt2, new Texture(Gdx.files.internal("Textures/Decals/Dirt/Dirt2.png")));
        textures.put(TextureName.Dirt3, new Texture(Gdx.files.internal("Textures/Decals/Dirt/Dirt3.png")));
        addWrappedTexture("Decals", TextureName.Glass);
        addWrappedTexture("Decals", TextureName.DarkGlass);

        textures.put(TextureName.BloodPool, new Texture(Gdx.files.internal("Textures/Decals/Gore/BloodPool.png")));

        textures.put(TextureName.ShowerDrain, new Texture(Gdx.files.internal("Textures/Decals/Decor/ShowerDrain.png")));
        textures.put(TextureName.MidnightAnimal, new Texture(Gdx.files.internal("Textures/Decals/Posters/MidnightAnimal.png")));
        textures.put(TextureName.MVCalendar, new Texture(Gdx.files.internal("Textures/Decals/Posters/MVCalendar.png")));
        textures.put(TextureName.NeverendingStory, new Texture(Gdx.files.internal("Textures/Decals/Posters/NeverendingStory.png")));
        textures.put(TextureName.DarkCrystal, new Texture(Gdx.files.internal("Textures/Decals/Posters/DarKCrystal.png")));
        textures.put(TextureName.BatteriesNotIncluded, new Texture(Gdx.files.internal("Textures/Decals/Posters/BatteriesNotIncluded.png")));
        textures.put(TextureName.CouplePhoto, new Texture(Gdx.files.internal("Textures/Decals/Posters/CouplePhoto.png")));

        textures.put(TextureName.Miami, new Texture(Gdx.files.internal("Textures/Decals/TV/Miami.png")));

        textures.put(TextureName.MaskLetter, new Texture(Gdx.files.internal("Textures/Misc/MaskLetter.png")));
        textures.put(TextureName.Ammo, new Texture(Gdx.files.internal("Textures/Misc/Ammo.png")));

        textures.put(TextureName.BlueCircle, new Texture(Gdx.files.internal("Textures/Debug/BlueCircle.png")));
    }

    private void addWrappedTexture(String category, TextureName name) {
        Texture texture = new Texture(Gdx.files.internal("Textures/"+category+"/"+name.toString()+".png"));
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        textures.put(name, texture);
    }

    public ModelInstance getModel(ModelName name) {
        if(models.containsKey(name)) {
            return new ModelInstance(models.get(name));
        } else {
            return new ModelInstance(models.get(ModelName.nul));
        }
    }

    public ModelInstance getBox(float x, float y, float z, float width, float height, float depth, TextureName name) {
        Material material = new Material(TextureAttribute.createDiffuse(textures.get(name)),
                ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(8f));
        long attributes = Usage.Position | Usage.Normal | Usage.TextureCoordinates;

        modelBuilder.begin();
        MeshPartBuilder builder = Main.modelHandler.modelBuilder.part(name.toString(), GL20.GL_TRIANGLES, attributes, material);
        if(height > 0.1f) {
            if(width <= 1) {
                builder.setUVRange(0, 0, height, depth);
            } else {
                builder.setUVRange(0, 0, height, width);
            }
        } else {
            builder.setUVRange(0, 0, depth, width);
        }
        BoxShapeBuilder.build(builder, width, height, depth);
        Model box = modelBuilder.end();
        runtimeModels.add(box);
        return new ModelInstance(box, x, y, z);
    }

    public ModelInstance getLine(Vector3 start, Vector3 end) {
        Gdx.gl.glLineWidth(5);

        modelBuilder.begin();
        MeshPartBuilder builder = modelBuilder.part("line", 1, 3, new Material());
        builder.setColor(Color.RED);
        builder.line(start, end);
        Model lineModel = modelBuilder.end();
        runtimeModels.add(lineModel);

        Gdx.gl.glLineWidth(1);
        return new ModelInstance(lineModel);
    }

    public ModelInstance getSphere(Vector3 size) {
        Material material = new Material(
                TextureAttribute.createDiffuse(textures.get(TextureName.BlueCircle)),
                ColorAttribute.createSpecular(0, 0, 1, 1),
                FloatAttribute.createShininess(8f)
        );
        long attributes = Usage.Position | Usage.Normal | Usage.TextureCoordinates;
        Model sphereModel = modelBuilder.createSphere(size.x, size.y, size.z, 24, 24, material, attributes);
        runtimeModels.add(sphereModel);

        return new ModelInstance(sphereModel);
    }

    public ModelInstance getSharedGrid(float gridSize) {
        if(sharedGrid != null) {
            xyzGrid.dispose();
            sharedGrid.dispose();
        }
        xyzGrid = modelBuilder.createXYZCoordinates(20, new Material(), Usage.Position | Usage.ColorPacked);
        sharedGrid = modelBuilder.createLineGrid(100, 100, gridSize, gridSize, new Material(ColorAttribute.createDiffuse(Color.WHITE)), Usage.Position);
        return new ModelInstance(sharedGrid);
    }

    public void dispose() {
        if(sharedGrid != null) {
            xyzGrid.dispose();
            sharedGrid.dispose();
        }
        for(Model model : models.values()) {
            model.dispose();
        }
        for(Texture texture : textures.values()) {
            texture.dispose();
        }

        for(Model model : runtimeModels) {
            model.dispose();
        }
    }
}
