package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.*;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.InteractiveModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.InteractiveSolidModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.*;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;

import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("Duplicates")
public class StoryOne extends Level {
    StoryOne(final CopyOnWriteArrayList<GameObject> activeObjects) {
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
                "September 16th, 1984", "Miami - Florida", 400);
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
        final InteractiveSolidModel phoneTable = new InteractiveSolidModel(new Vector3(6.5f, 0, -5.3f), 90, ModelName.PhoneTable1);
        phoneTable.setAction(new Action() {
            @Override
            public void activate() {
                phoneRing.stopLoopingSound();
                final SoundSource soundSource = SoundSource.buildSoundSource(0).setSound("Misc/Phone.wav");
                soundSource.playSound();
                phoneTable.updateModel(ModelName.PhoneTable2);
                player.lookAt(phoneTable.position.x, phoneTable.position.z, -47f);
                player.textBox.text.clear();
                player.textBox.text.add("(You): Hello?");
                player.textBox.text.add("Hudds: Hey Hockett!");
                player.textBox.text.add("(You) Hockett: Hudds. What sort of time do you call this?");
                player.textBox.text.add("Hudds: Jeez only one o'clock in the morning. You getting old already?");
                player.textBox.text.add("Hudds: Ever hear of a local dealer name of Rogerio?");
                player.textBox.text.add("Hockett: Hm *Murmurs negatively*");
                player.textBox.text.add("Hudds: 'Bout four weeks ago one of our detectives set himself up for a meet");
                player.textBox.text.add("Hudds: The bust went sour, our guy was shot to death");
                player.textBox.text.add("Hockett: Cincinnati thinks he's back down here?");
                player.textBox.text.add("Hudds: Yeah, alongside a thousand other bush-league pushers...");
                player.textBox.text.add("Hudds: Hockett this guy is a cop killer. We need to find a way to get to him");
                player.textBox.text.add("Hockett: You had a look at any of his connections?");
                player.textBox.text.add("Hudds: Well that's actually what I'm calling you about.");
                player.textBox.text.add("Hudds: I found an office that we think is a front for one of their arms " +
                        "dealing rackets");
                player.textBox.text.add("Hockett: And I'm guessing you need me to check the place out?");
                player.textBox.text.add("Hudds: Yeah, you always do it best.");
                player.textBox.text.add("Hockett: You got an address?");
                player.textBox.text.add("Hudds: It's the office building across from the old factories. Shouldn't be " +
                        "hard to miss");
                player.textBox.text.add("Hockett: Alright I'll be there in ten");
                player.textBox.text.add("Hudds: See you on the other side.");
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
