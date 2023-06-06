package com.gamegdx.tanks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamegdx.tanks.utils.GameType;

public class ScreenManager {
    public enum ScreenType {
        MENU, GAME
    }

    private static ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    private ScreenManager() {
    }

    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 720;

    private Game game;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private Viewport viewport;
    private Camera camera;

    public Camera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void init(Game game, SpriteBatch batch) {
        this.game = game;
        this.camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        this.camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2,0);
        this.camera.update();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        this.gameScreen = new GameScreen(batch);
        this.menuScreen = new MenuScreen(batch);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    public void setScreen(ScreenType screenType, Object... args) {
        Gdx.input.setCursorCatched(false);
        Screen currentScreen = game.getScreen();
        switch (screenType) {
            case MENU:
                game.setScreen(menuScreen);
                break;
            case GAME:
                gameScreen.setGameType((GameType)args[0]);
                game.setScreen(gameScreen);
                break;
        }
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
