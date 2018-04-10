package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Svgood on 21.02.2017.
 */
public class Bg {
    private Vector2 pos;
    float speed;
    Texture img;

    public Bg(float x){
        pos = new Vector2(x, 0);
        speed = 4.0f;
        img = new Texture("bg1.jpg");
    }
    public void draw(SpriteBatch batch) {
        batch.draw(img, pos.x, pos.y);
    }
    public void update(SpriteBatch batch) {
        pos.x -= speed;
        if (pos.x <= - 1280) {
            pos.x = 1280;
        }
        draw(batch);
    }
}
