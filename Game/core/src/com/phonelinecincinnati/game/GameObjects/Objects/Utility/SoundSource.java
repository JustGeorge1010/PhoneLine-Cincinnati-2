package com.phonelinecincinnati.game.GameObjects.Objects.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class SoundSource extends GameObject {

    private Music music;
    private Sound sound;

    public int id;
    private static float globalMusicVolume = -1f, globalSFXVolume = -1f;

    private SoundSource(int id) {
        if(globalMusicVolume == -1f && globalSFXVolume == -1f) {
            FileHandle file = Gdx.files.internal("Config/volume.txt");
            String text = file.readString();
            globalMusicVolume = Float.parseFloat(text.substring(0, 3));
            globalSFXVolume = Float.parseFloat(text.substring(5, 8));
        }
        this.id = id;
    }

    public static SoundSource buildSoundSource(int id) {
        return new SoundSource(id);
    }

    public SoundSource setMusic(String name) {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/"+name));
        music.setVolume(globalMusicVolume);
        music.setLooping(true);
        return this;
    }

    public SoundSource playMusic() {
        music.play();
        return this;
    }

    public void pauseMusic() {
        music.pause();
    }

    public void stopMusic() {
        music.stop();
    }

    public SoundSource setSound(String name) {
        sound = Gdx.audio.newSound(Gdx.files.internal("SFX/"+name));
        return this;
    }

    public void playSound() {
        sound.setVolume(sound.play(globalSFXVolume), globalSFXVolume);
    }

    public static void setGlobalMusicVolume(float volume) {
        globalMusicVolume = volume;
        saveToFile();
    }

    public static float getGlobalMusicVolume() {
        return globalMusicVolume;
    }

    public static void setGlobalSFXVolume(float volume) {
        globalSFXVolume = volume;
        saveToFile();
    }

    public static float getGlobalSFXVolume() {
        return globalSFXVolume;
    }

    private static void saveToFile() {
        FileHandle file = Gdx.files.local("Config/volume.txt");
        file.writeString(globalMusicVolume+", "+globalSFXVolume, false);
    }

    @Override
    public void update() {
        if(globalMusicVolume != music.getVolume()) {
            music.setVolume(globalMusicVolume);
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
        if(music != null) music.dispose();
        if(sound != null) sound.dispose();
    }

    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }
}
