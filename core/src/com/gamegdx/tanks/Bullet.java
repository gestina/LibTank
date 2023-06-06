package com.gamegdx.tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gamegdx.tanks.units.Tank;

public class Bullet {
    private Tank owner;
    private Vector2 position;
    private Vector2 velocity;
    private int damage;
    private boolean active;
    private float currentTime;
    private float maxTime;

    public boolean isActive() {
        return active;
    }

    public Tank getOwner() {
        return owner;
    }

    public int getDamage() {
        return damage;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Bullet() {
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.active = false;
        this.damage = 0;
    }

    public void activate(Tank owner, float x, float y, float vx, float vy, int damage, float maxTime) {
        this.owner = owner;
        this.active = true;
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.damage = damage;
        this.maxTime = maxTime;
        this.currentTime = 0.0f;
    }

    public void deactivate() {
        active = false;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        currentTime += dt;
        if (currentTime >= maxTime) {
            deactivate();
        }
        if (position.x < 0.0f || position.x > Gdx.graphics.getWidth() || position.y < 0.0f || position.y > Gdx.graphics.getHeight()) {
            deactivate();
        }
    }
}
