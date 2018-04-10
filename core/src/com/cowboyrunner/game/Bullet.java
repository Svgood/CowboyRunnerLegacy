package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Svgood on 21.02.2017.
 */
public class Bullet {
    Vector2 pos;
    double speed;
    Texture img;
    Boolean active;
    Rectangle rect;

    public Bullet(float x, float y) {
        pos = new Vector2(x,y);
        speed = 40;
        img = new Texture("bullet.jpg");
        rect = new Rectangle(pos.x, pos.y, img.getWidth(), img.getHeight());
        active = true;
    }

    public boolean getActive(){
        return active;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void update() {
        if (active) {
            pos.x += speed;
            rect.x = pos.x;
            if (pos.x >= MyGdxGame.camera_width)
                active = false;
        }
        else {
            pos.x = -400;
            pos.y = -400;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(img, pos.x, pos.y);
    }
}
