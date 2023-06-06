package com.gamegdx.tanks.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.gamegdx.tanks.utils.Direction;
import com.gamegdx.tanks.utils.KeysControl;
import com.gamegdx.tanks.utils.TankOwner;
import com.gamegdx.tanks.utils.Utils;
import com.gamegdx.tanks.GameScreen;
import com.gamegdx.tanks.Item;
import com.gamegdx.tanks.ScreenManager;
import com.gamegdx.tanks.Weapon;

public class PlayerTank extends Tank {
    KeysControl keysControl;
    StringBuilder tmpString;
    int index;
    int score;
    int lives;

    public PlayerTank(int index, GameScreen game, KeysControl keysControl, TextureAtlas atlas) {
        super(game);
        this.index = index;
        this.ownerType = TankOwner.PLAYER;
        this.keysControl = keysControl;
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("playerTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(100.0f, 100.0f);
        this.speed = 100.0f;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 10;
        this.hp = this.hpMax;
        this.circle = new Circle(position.x, position.y, (width + height) / 2);
        this.lives = 5;
        this.tmpString = new StringBuilder();
    }

    public void addScore(int amount) {
        score += amount;
    }

    @Override
    public void destroy() {
        if (lives < 0){
            ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.MENU);
        }
        lives--;
        hp = hpMax;
    }

    public void update(float dt) {
        checkMovement(dt);
        if (keysControl.getTargeting() == KeysControl.Targeting.MOUSE) {
            rotateTurretToPoint(gameScreen.getMousePosition().x, gameScreen.getMousePosition().y, dt);
            if (Gdx.input.isTouched()) {
                fire();
            }
        } else {
            if (Gdx.input.isKeyPressed(keysControl.getRotateTurretLeft())) {
                turretAngle = Utils.makeRotation(turretAngle, turretAngle + 90.0f, 270.0f, dt);
                turretAngle = Utils.angleToFromNegPiToPosPi(turretAngle);
            }
            if (Gdx.input.isKeyPressed(keysControl.getRotateTurretRight())) {
                turretAngle = Utils.makeRotation(turretAngle, turretAngle - 90.0f, 270.0f, dt);
                turretAngle = Utils.angleToFromNegPiToPosPi(turretAngle);
            }
            if (Gdx.input.isKeyPressed(keysControl.getFire())) {
                fire();
            }

        }
        super.update(dt);
    }

    public void consumePowerUp(Item item) {
        switch (item.getType()) {
            case MEDKIT:
                hp += 4;
                if (hp > hpMax) {
                    hp = hpMax;
                }
                break;
            case SHIELD:
                addScore(1000); // надо будет заменить на щит
                break;
        }
    }

    public void renderHUD(SpriteBatch batch, BitmapFont font24) {
        tmpString.setLength(0);
        tmpString.append("Player: ").append(index);
        tmpString.append("\nScore: ").append(score);
        tmpString.append("\nLives: ").append(lives);
        font24.draw(batch, tmpString, 20 + (index - 1) * 200, 700);
    }

    public void checkMovement(float dt) {
        if (Gdx.input.isKeyPressed(keysControl.getLeft())) {
            move(Direction.LEFT, dt);
        } else if (Gdx.input.isKeyPressed(keysControl.getRight())) {
            move(Direction.RIGHT, dt);
        } else if (Gdx.input.isKeyPressed(keysControl.getUp())) {
            move(Direction.UP, dt);
        } else if (Gdx.input.isKeyPressed(keysControl.getDown())) {
            move(Direction.DOWN, dt);
        }
    }
}
