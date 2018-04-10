package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Svgood on 21.02.2017.
 */
public class Cactus implements NPC {
    private Vector2 pos;
    static float speed;
    static float normal_speed;

    Texture img;
    Sprite sprite;
    Rectangle rect;
    boolean active;
    final static int CACTUSES_COUNT = 3;


    //death
    float vspeed;
    float rotationspeed;
    float rotation;

    String type;

    boolean dynamic;
    boolean toremove;
    boolean overlaping;

    public Cactus(){
        pos = new Vector2((float)Math.random()*MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width, (float)Config.GROUND_Y);
        speed = 12.0f;
        normal_speed = 12.0f;
        vspeed = 0;
        rotation = 0;
        rotationspeed = 4;
        img = new Texture("cactus.png");
        sprite = new Sprite(img);
        rect = new Rectangle(pos.x, pos.y, img.getWidth()/2, img.getHeight());
        active = true;
        type = "Cactus";
        dynamic = false;
        toremove = false;
        overlaping = true;
    }

    public Rectangle getRect(){
        return rect;
    }

    public boolean getActive(){
        return active;
    }

    public boolean getToRemove(){ return toremove;}

    public String getType() {
        return type;
    }

    public static void create() {
        for (int i = 0; i < CACTUSES_COUNT; i++) {
            MyGdxGame.NPC.add(new Cactus());
            //MyGdxGame.objects.add(cactuses.get(i));
        }
    }

    public static void add() {
        MyGdxGame.NPC.add(new Cactus());
    }



    public void draw(SpriteBatch batch) {
        //batch.draw(img, pos.x, pos.y);
        sprite.setPosition(pos.x-5, pos.y);
        sprite.draw(batch);
    }

    public void update(SpriteBatch batch) {
        if (overlaping) {
            overlaping = false;
            for (int i = 0; i < MyGdxGame.NPC.size; i++) {
                if (MyGdxGame.NPC.get(i).getRect().overlaps(rect) && MyGdxGame.NPC.get(i).getType().equals(type) && MyGdxGame.NPC.get(i).getRect() != rect) {
                    pos.x = (float) Math.random() * MyGdxGame.camera_width * Config.RANDOM_SPAWN_K + MyGdxGame.camera_width;
                    overlaping = true;
                }
            }
        }

        pos.x -= speed;
        pos.y += vspeed;
        rect.x = pos.x;
        if (!active) death();
        if (pos.x <= - img.getWidth()) soloRestart();
        getCollision();
        draw(batch);
    }

    public void getCollision () {
        for(int i = 0; i < Player.bullets.size(); i++)
            if (Player.bullets.get(i).pos.x + Player.bullets.get(i).img.getWidth() >= pos.x
                    && Player.bullets.get(i).pos.y <= pos.y + img.getHeight()
                    && Player.bullets.get(i).pos.x <= pos.x + img.getWidth() && active) {
                active = false;
                vspeed = MathUtils.random(8,12);
                Player.bullets.remove(i);
            }
    }

    public void death() {
        rotation += rotationspeed;
        sprite.setRotation(rotation);
        vspeed -= 0.5;
        if (pos.y > Config.GROUND_Y - img.getHeight()*2) pos.x += speed*1.5;
        else soloRestart();
    }

    public void soloRestart() {
        active = true;
        sprite.setRotation(0);
        pos.x = (float)Math.random()*MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width;
        pos.y = Config.GROUND_Y;
        vspeed = 0;
        overlaping = true;
        rotation = 0;
    }
}
