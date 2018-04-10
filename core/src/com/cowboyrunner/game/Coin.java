package com.cowboyrunner.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Svgood on 23.02.2017.
 */
public class Coin {
    static float speed;
    public static Texture img;
    public Rectangle rect;
    public static Array<Coin> coins = new Array<Coin>();
    public static int size = 64;
    public static int koef = 1;

    public Coin(float x, float y){
        speed = 12.0f;
        img = new Texture("coin.png");
        rect = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    public static void create() {
        double r = Math.floor(MathUtils.random(0,6));

        //create upper row
        if (r == 1)
            for(int i = 0; i < Math.floor(MathUtils.random(4 + MyGdxGame.difficulty*2,12 + MyGdxGame.difficulty*2)); i++)
                coins.add(new Coin(MyGdxGame.camera_width*2 + size*i, MyGdxGame.camera_height/1.5f));
        //create down row
        if (r == 2)
            for(int i = 0; i < Math.floor(MathUtils.random(4 + MyGdxGame.difficulty*2,12 + MyGdxGame.difficulty*2)); i++)
                coins.add(new Coin(MyGdxGame.camera_width*2 + size*i, MyGdxGame.camera_height/7));
        //create middle row
        if (r == 3)
            for(int i = 0; i < Math.floor(MathUtils.random(4 + MyGdxGame.difficulty*2,12 + MyGdxGame.difficulty*2)); i++)
                coins.add(new Coin(MyGdxGame.camera_width*2 + size*i, MyGdxGame.camera_height/3));
        //create square
        if (r == 0){
            for(int i = 0; i < 6 + MyGdxGame.difficulty; i++)
                for(int k = 0; k < 6; k++)
                    coins.add(new Coin(MyGdxGame.camera_width*2 + size*i, 100 + k*size));
        }
        //create diagonal up
        if (r == 4) {
            for (int i = 0; i < 7; i++)
                coins.add(new Coin(MyGdxGame.camera_width*2 + size*i, MyGdxGame.camera_height/6 + size*i));
        }
        if (r == 5) {
            for (int i = 0; i < 7; i++)
                coins.add(new Coin(MyGdxGame.camera_width*2 + size*i, MyGdxGame.camera_height/1.5f - size*i));
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(img, rect.x, rect.y);
    }

    public void update(SpriteBatch batch) {
        rect.x -= speed;
        getCollision();
        draw(batch);
    }

    public void getCollision () {
        if (rect.overlaps(Player.rect)) {
            rect.x = - 100;
            rect.y = - 100;
            Player.coins += 1*koef;
        }
    }

    public static void restart () {
            coins.clear();
    }
}
