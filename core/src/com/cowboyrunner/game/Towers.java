package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Svgood on 22.02.2017.
 */
public class Towers {
    private Vector2 pos;
    static float speed;
    Texture img;
    Rectangle rect;
    public static Array<Towers> towers = new Array<Towers>();

    public Towers(String file, float spd){
        pos = new Vector2((float)Math.random()* MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width, (float)Config.GROUND_Y*2);
        speed = spd;
        img = new Texture(file);
        rect = new Rectangle(pos.x, pos.y, img.getWidth(), img.getHeight()/4);
    }

    public static void create() {
        towers.add(new Towers("tower.png", 6f));
    }

    public static void createVigvam() {
        towers.add(new Towers("Vigvam.png", 8.0f));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(img, pos.x, pos.y);
    }

    public void update(SpriteBatch batch) {
        pos.x -= speed;
        rect.x = pos.x;
        if (pos.x <= - img.getWidth()) {
            pos.x = (float)Math.random()*MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width;
        }
        //getCollision();
        draw(batch);
    }

    public void getCollision () {
        for(int i = 0; i < Player.bullets.size(); i++)
            if (Player.bullets.get(i).pos.x + Player.bullets.get(i).img.getWidth() >= pos.x
                    && Player.bullets.get(i).pos.y <= pos.y + img.getHeight()
                    && Player.bullets.get(i).pos.x <= pos.x + img.getWidth()) {
                Player.bullets.remove(i);
            }
    }

    public static void restart () {
        for (int i = 0; i < towers.size; i++){
            towers.get(i).pos.x = (float)Math.random()*MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width;
        }
    }
}
