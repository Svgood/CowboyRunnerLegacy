package com.cowboyrunner.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Svgood on 18.03.2017.
 */
public class Axe implements Projectiles {

    Vector2 pos;
    float speed;
    float rotation;
    Texture img;
    Sprite sprite;
    Boolean active;
    Rectangle rect;

    public Axe(float x, float y) {
        pos = new Vector2(x,y);
        speed = 20;
        rotation = 0;
        img = new Texture("axe.png");
        sprite = new Sprite(img);
        rect = new Rectangle(pos.x, pos.y, img.getWidth(), img.getHeight());
        active = true;
    }

    public boolean getActive(){
        return active;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void update(SpriteBatch batch) {
        if (active) {
            pos.x -= speed;
            rect.x = pos.x;
            if (pos.x >= MyGdxGame.camera_width)
                active = false;
        }
        else {
            pos.x = -400;
            pos.y = -400;
        }
        draw(batch);
    }

    public void draw(SpriteBatch batch)
    {
        sprite.setPosition(rect.x, rect.y);
        sprite.setRotation(rotation);
        sprite.draw(batch);
        rotation += 4;
    }
}
