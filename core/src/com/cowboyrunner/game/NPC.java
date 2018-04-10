package com.cowboyrunner.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Svgood on 14.03.2017.
 */
public interface NPC {

    public void update(SpriteBatch batch);

    public Rectangle getRect();

    public boolean getActive();

    public void soloRestart();

    public boolean getToRemove();

    public String getType();
}
