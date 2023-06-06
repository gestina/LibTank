package com.gamegdx.tanks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Weapon {
    private TextureRegion texture;
    private float firePeriod;
    private float radius;
    private float projectileSpeed;
    private float projectileLifetime;
    private int damage;

    public TextureRegion getTexture() {
        return texture;
    }

    public float getFirePeriod() {
        return firePeriod;
    }

    public int getDamage() {
        return damage;
    }

    public float getRadius() {
        return radius;
    }

    public float getProjectileSpeed() {
        return projectileSpeed;
    }

    public float getProjectileLifetime() {
        return projectileLifetime;
    }

    public Weapon(TextureAtlas atlas) {
        this.texture = atlas.findRegion("simpleWeapon");
        this.firePeriod = 0.2f;
        this.damage = 1;
        this.radius = 300.0f;
        this.projectileSpeed = 320.0f;
        this.projectileLifetime = this.radius / this.projectileSpeed;
    }
}
