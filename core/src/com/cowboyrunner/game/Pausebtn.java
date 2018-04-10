package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Svgood on 08.03.2017.
 */
public class Pausebtn {
    Texture img;
    Rectangle rect;

    Pausebtn (int x, int y) {
        img = new Texture("pause.png");
        rect = new Rectangle(x, y,
                img.getWidth(),
                img.getHeight());
    }

    void update(SpriteBatch batch) {
        if (Gdx.input.isTouched() && rect.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
        {
            MyGdxGame.makepause();
            MyGdxGame.gameStance = "Menu";
        }
        draw(batch);
    }

    void draw(SpriteBatch batch) {
        batch.draw(img, rect.x, rect.y);
    }
}
