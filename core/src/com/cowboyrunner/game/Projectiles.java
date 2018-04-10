package com.cowboyrunner.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Svgood on 18.03.2017.
 */
public interface Projectiles {
    public void update(SpriteBatch batch);

    public boolean getActive();

    public Rectangle getRect();
}
