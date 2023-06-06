package com.gamegdx.tanks.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.gamegdx.tanks.utils.Direction;
import com.gamegdx.tanks.utils.TankOwner;
import com.gamegdx.tanks.utils.Utils;
import com.gamegdx.tanks.GameScreen;
import com.gamegdx.tanks.Weapon;

public abstract class Tank {
    GameScreen gameScreen;
    TankOwner ownerType;
    Weapon weapon;
    TextureRegion texture;
    TextureRegion textureHp;
    Vector2 position;
    Vector2 tmp;
    Circle circle;

    int hp;
    int hpMax;

    float speed;
    float angle;

    float turretAngle;
    float fireTimer;

    int width;
    int height;

    public Vector2 getPosition() {
        return position;
    }

    public TankOwner getOwnerType() {
        return ownerType;
    }

    public Circle getCircle() {
        return circle;
    }

    public Tank(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.tmp = new Vector2(0.0f, 0.0f);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - width / 2, position.y - height / 2, width / 2, height / 2, width, height, 1, 1, angle);
        batch.draw(weapon.getTexture(), position.x - width / 2, position.y - height / 2, width / 2, height / 2, width, height, 1, 1, turretAngle);
        if (hp < hpMax) {
            batch.setColor(0, 0, 0, 1);
            batch.draw(textureHp, position.x - width / 2 - 2, position.y + height / 2 - 8 - 2, 44, 12);
            batch.setColor(0, 1, 0, 1);
            batch.draw(textureHp, position.x - width / 2, position.y + height / 2 - 8, ((float) hp / hpMax) * 40, 8);
            batch.setColor(1, 1, 1, 1);
        }
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            destroy();
        }
    }

    public abstract void destroy();

    public void update(float dt) {
        fireTimer += dt;
        if (position.x < 0.0f) {
            position.x = 0.0f;
        }
        if (position.x > Gdx.graphics.getWidth()) {
            position.x = Gdx.graphics.getWidth();
        }
        if (position.y < 0.0f) {
            position.y = 0.0f;
        }
        if (position.y > Gdx.graphics.getHeight()) {
            position.y = Gdx.graphics.getHeight();
        }
        circle.setPosition(position);
    }

    public void move(Direction direction, float dt) {
        tmp.set(position);
        tmp.add(speed * direction.getVx() * dt, speed * direction.getVy() * dt);
        if (gameScreen.getMap().isAreaClear(tmp.x, tmp.y, width / 2)) {
            angle = direction.getAngle();
            position.set(tmp);
        }
    }

    public void rotateTurretToPoint(float pointX, float pointY, float dt) {
        float angleTo = Utils.getAngle(position.x, position.y, pointX, pointY);
        turretAngle = Utils.makeRotation(turretAngle, angleTo, 270.0f, dt);
        turretAngle = Utils.angleToFromNegPiToPosPi(turretAngle);
    }

    public void fire() {
        if (fireTimer >= weapon.getFirePeriod()) {
            fireTimer = 0.0f;
            float angleRad = (float) Math.toRadians(turretAngle);
            gameScreen.getBulletEmitter().activate(this, position.x, position.y, weapon.getProjectileSpeed() * (float) Math.cos(angleRad), weapon.getProjectileSpeed() * (float) Math.sin(angleRad), weapon.getDamage(), weapon.getProjectileLifetime());
        }
    }
}
