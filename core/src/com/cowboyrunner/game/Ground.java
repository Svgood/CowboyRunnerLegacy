package com.cowboyrunner.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Svgood on 24.03.2017.
 */
public class Ground {
    Texture img;
    Vector2 vec;
    public static float speed;

    Ground(int x) {
        img = new Texture("ground.png");
        vec = new Vector2(x,0);
        speed = 11f;
    }

    void update(SpriteBatch batch) {
        vec.x -= speed;
        if (vec.x <= -1280) vec.x = 1280;
        batch.draw(img, vec.x, vec.y);
    }
}
