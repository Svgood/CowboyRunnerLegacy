package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Svgood on 21.03.2017.
 */
public class Barrel {
    Texture img,img1,img2,img3,img4,img5,img6;
    Sprite sprite;
    Rectangle rect;
    float rotation;
    static boolean start;

    Barrel (int x, int y) {
        img = new Texture("barrel.png");
        img1 = new Texture("barrel1.png");
        img2 = new Texture("barrel2.png");
        img3 = new Texture("barrel3.png");
        img4 = new Texture("barrel4.png");
        img5 = new Texture("barrel5.png");
        img6 = new Texture("barrel6.png");
        sprite = new Sprite(img);
        rect = new Rectangle(x, y,
                img.getWidth(),
                img.getHeight());
        rotation = 0;
        start = false;
    }

    void update(SpriteBatch batch) {
        checkAmmo();
        checkClick();
        if (Gdx.input.isTouched() && rect.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
        {
            //MyGdxGame.makepause();
            //MyGdxGame.gameStance = "Menu";
        }
        draw(batch);
        if (Player.reload < Player.firerate) rotate();
        if (Player.ammo == 0) reloading();
        if (Player.ammo == 6) {
            rotation = 0;
            sprite.setRotation(rotation);
        }
    }

    void draw(SpriteBatch batch) {
        sprite.draw(batch);
        sprite.setPosition(rect.x, rect.y);
    }

    void rotate() {
        rotation += 1.05/Player.firerate;
        sprite.setRotation(rotation);
    }

    void reloading() {
        rotation += 8;
        sprite.setRotation(rotation);
    }
    void checkAmmo() {
        if (Player.ammo == 6) sprite.setTexture(img);
        if (Player.ammo == 5) sprite.setTexture(img1);
        if (Player.ammo == 4) sprite.setTexture(img2);
        if (Player.ammo == 3) sprite.setTexture(img3);
        if (Player.ammo == 2) sprite.setTexture(img4);
        if (Player.ammo == 1) sprite.setTexture(img5);
        if (Player.ammo == 0) sprite.setTexture(img6);
    }
    void checkClick() {
        if (Gdx.input.isTouched())
            if (rect.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
                Player.ammo = 0;
    }
}
