package com.cowboyrunner.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Svgood on 14.03.2017.
 */
public class Jumper implements NPC {
    private Vector2 pos;
    static float speed;
    static float normal_speed;

    Texture img, img1;
    Sprite sprite;
    Rectangle rect;
    boolean active;
    final static int JUMPER_COUNT = 0;


    //death
    float vspeed;
    float rotationspeed;
    float rotation;
    float jump_dist;
    //action
    Boolean triggered;
    Boolean dynamic;
    Boolean toremove;
    String type;


    public Jumper(){
        pos = new Vector2((float)Math.random()*MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width, (float)Config.GROUND_Y);
        speed = 15f;
        normal_speed = 15f;
        vspeed = 0;
        rotation = 0;
        rotationspeed = 5;
        img = new Texture("Jumper1.png");
        img1 = new Texture("Jumper2.png");
        sprite = new Sprite(img);
        rect = new Rectangle(pos.x, pos.y, img.getWidth()/2, img.getHeight());
        active = true;
        triggered = false;
        jump_dist = MathUtils.random(600, 800);
        type = "Jumper";
        dynamic = false;
        toremove = false;
    }

    public Jumper(boolean dyn){
        pos = new Vector2((float)Math.random()*MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width, (float)Config.GROUND_Y);
        speed = 15f;
        normal_speed = 15f;
        vspeed = 0;
        rotation = 0;
        rotationspeed = 5;
        img = new Texture("Jumper1.png");
        img1 = new Texture("Jumper2.png");
        sprite = new Sprite(img);
        rect = new Rectangle(pos.x, pos.y, img.getWidth()/2, img.getHeight());
        active = true;
        triggered = false;
        jump_dist = MathUtils.random(600, 800);
        type = "Jumper";
        dynamic = dyn;
        toremove = false;
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
        for (int i = 0; i < JUMPER_COUNT; i++) {
            MyGdxGame.NPC.add(new Jumper());
            //MyGdxGame.objects.add(jumpers.get(i));
        }
    }

    public static void add() {
        MyGdxGame.NPC.add(new Jumper());
    }


    public void draw(SpriteBatch batch) {
        //batch.draw(img, rect.x, rect.y);
        if (vspeed < 0) sprite.setTexture(img1);
        else sprite.setTexture(img);
        sprite.setPosition(rect.x-img.getWidth()/4, rect.y);
        sprite.draw(batch);
    }

    public void update(SpriteBatch batch) {
        rect.x -= speed;
        rect.y += vspeed;
        if (!active) death();
        if (rect.x <= - img.getWidth()) {
            soloRestart();
        }
        getCollision();
        action();
        draw(batch);
    }

    public void getCollision () {
        for(int i = 0; i < Player.bullets.size(); i++)
            if (rect.overlaps(Player.bullets.get(i).rect) && active) {
                active = false;
                vspeed = MathUtils.random(10,14);
                rotationspeed = MathUtils.random(4,6);
                Player.bullets.remove(i);
            }
    }

    public void death() {
        rotation += rotationspeed;
        sprite.setRotation(rotation);
        vspeed -= 0.6;
        if (rect.y > Config.GROUND_Y - img.getHeight()*3) rect.x += speed*1.5;
        else soloRestart();
    }

    public void action() {
        if (rect.x < Player.rect.x + jump_dist && active) {
            if (!triggered) {
                vspeed = MathUtils.random(16,19);
                triggered = true;
            }
            if (rect.y + vspeed <= Config.GROUND_Y) {
                rect.y = Config.GROUND_Y;
                vspeed = 0;
            }
            else vspeed -= 0.6;
        }
    }

    public void soloRestart() {
        if (!dynamic) {
            sprite.setRotation(0);
            jump_dist = MathUtils.random(500, 800);
            rect.x = (float) Math.random() * MyGdxGame.camera_width * Config.RANDOM_SPAWN_K + MyGdxGame.camera_width;
            rect.y = Config.GROUND_Y;
            vspeed = 0;
            rotation = 0;
            triggered = false;
            active = true;
        }
        else toremove = true;
    }
}
