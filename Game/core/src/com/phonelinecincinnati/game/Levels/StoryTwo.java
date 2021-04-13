package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.TitleCard;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.InteractiveModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.InteractiveSolidModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;

import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("Duplicates")
public class StoryTwo extends Level {
    StoryTwo(final CopyOnWriteArrayList<GameObject> activeObjects) {
        this.activeObjects = activeObjects;
        load(false, false);
    }

    @Override
    public void load(boolean reloading, boolean retainPlayer) {
        name = "PlayerHouse";
        Main.levelHandler.levelName = name;
        Main.backgroundColor.set(0.043137256f, 0.043137256f, 0.19215687f, 1f);
        Main.levelHandler.score.display = false;

        activeObjects.add(SoundSource.buildSoundSource(0).setMusic("Jan Hammer - Crockett's Theme.mp3").playMusic());
        final SoundSource phoneRing =
                SoundSource.buildSoundSource(1).setLoopingSound("Misc/PhoneCall.wav").playLoopingSound();
        activeObjects.add(phoneRing);

        final Player player =
                createPlayer(new Vector3(0, 0f, 0), new Vector3(0, 0, 1), null,
                        reloading, retainPlayer);

        Main.levelHandler.loadFromJson(name);
        activeObjects.add(player);

        final PauseMenuHandler pauseMenuHandler = new PauseMenuHandler(false);
        activeObjects.add(pauseMenuHandler);

        final TitleCard titleCard = new TitleCard(
                "September 18th, 1984", "Miami - Florida", 400);
        titleCard.addAction(new Action() {
            @Override
            public void activate() {
                player.giveControl();
                pauseMenuHandler.enablePausing();
                titleCard.rendering = false;
            }
        });
        activeObjects.add(titleCard);

        player.hud.objectiveText = "The phone is ringing...";
        final InteractiveSolidModel phoneTable = new InteractiveSolidModel(new Vector3(6.5f, 0, -5.3f),
                90, ModelName.PhoneTable1);

        phoneTable.setAction(new Action() {
            @Override
            public void activate() {
                phoneRing.stopLoopingSound();
                final SoundSource soundSource = SoundSource.buildSoundSource(0).setSound("Misc/Phone.wav");
                soundSource.playSound();
                phoneTable.updateModel(ModelName.PhoneTable2);
                player.lookAt(phoneTable.position.x, phoneTable.position.z, -47f);
                player.textBox.text.clear();
                player.textBox.text.add("Hockett: He-");
                player.textBox.text.add("Hudds: Hockett! Finally!");
                player.textBox.text.add("Hockett: Hudds are you okay?");
                player.textBox.text.add("Hudds: I'm real bad Sonny, I'm shot");
                player.textBox.text.add("Hockett: Where are you?");
                player.textBox.text.add("Hudds: I'm at the port, hiding behind one of the containers");
                player.textBox.text.add("Hockett: Okay I'll be right there");
                player.textBox.text.add("Hudds: Sonny...");
                player.textBox.text.add("Hudds: They know about us. They know we want to take Rogerigo down");
                player.textBox.text.add("Hockett: Don't worry about that now. Hold in there.");
                player.textBox.text.add("*CLICK*");
                player.textBox.setExitAction(new Action() {
                    @Override
                    public void activate() {
                        soundSource.playSound();
                        phoneTable.updateModel(ModelName.PhoneTable3);
                        phoneTable.removeAction();
                        player.hud.objectiveText = "Leave your home";
                        final InteractiveModel door = new InteractiveSolidModel(new Vector3(10.5f, 0, -21f),
                            -90, ModelName.PhoneTable1);
                        door.setAction(new Action() {
                            @Override
                            public void activate() {
                                Main.levelHandler.loadNext();
                            }
                        }, "(RMB) Exit");
                        activeObjects.add(door);
                    }
                });
                player.textBox.open();
            }
        }, "(RMB) to answer phone");
        activeObjects.add(phoneTable);
}

    @Override
    public void reload() {
        Main.levelHandler.clearActiveObjects();
        load(true, false);
    }
}
