package com.gamegdx.tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamegdx.tanks.utils.GameType;

public class MenuScreen extends AbstractScreen {
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private BitmapFont font24;
    private Stage stage;

    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        atlas = new TextureAtlas("game.pack");
        font24 = new BitmapFont(Gdx.files.internal("font24.fnt"));
        stage = new Stage();

        Skin skin = new Skin();
        skin.add("simpleButton", new TextureRegion(atlas.findRegion("SimpleButton")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("simpleButton");
        textButtonStyle.font = font24;

        Group group = new Group();
        final TextButton start1Button = new TextButton("Start 1P", textButtonStyle);
        final TextButton start2Button = new TextButton("Start 2P", textButtonStyle);
        final TextButton exitButton = new TextButton("Exit", textButtonStyle);

        start1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME, GameType.ONE_PLAYER);
            }
        });
        start2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME, GameType.TWO_PLAYERS);
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        start1Button.setPosition(0, 80);
        start2Button.setPosition(0, 40);
        exitButton.setPosition(0, 0);
        group.addActor(start1Button);
        group.addActor(start2Button);
        group.addActor(exitButton);
        group.setPosition(580, 40);
        stage.addActor(group);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        font24.dispose();
        stage.dispose();
    }
}
