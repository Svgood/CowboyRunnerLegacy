package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Svgood on 18.03.2017.
 */
public class Axethrower implements NPC {
    private Vector2 pos;
    static float speed;
    static float normal_speed;

    Texture img;
    Sprite sprite;
    Rectangle rect;
    boolean active;
    final static int AXETHROWER_COUNT = 0;


    //death
    float vspeed;
    float rotationspeed;
    float rotation;
    //action
    Boolean triggered;
    Boolean dynamic;
    Boolean toremove;
    String type;
    //cooldown
    static float cooldown;
    float wait;

    public Axethrower(){
        pos = new Vector2((float)Math.random()*MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width, (float)Config.GROUND_Y);
        speed = 15f;
        normal_speed = 15f;
        vspeed = 0;
        rotation = 0;
        rotationspeed = 5;
        cooldown = 2;
        wait = 0;
        img = new Texture("Axethrower.png");
        sprite = new Sprite(img);
        rect = new Rectangle(pos.x, pos.y, img.getWidth()/2, img.getHeight());
        active = true;
        triggered = false;
        type = "Axethrower";
        dynamic = false;
        toremove = false;
    }

    public Axethrower(boolean dyn){
        pos = new Vector2((float)Math.random()*MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width, (float)Config.GROUND_Y);
        speed = 15f;
        normal_speed = 15f;
        vspeed = 0;
        rotation = 0;
        rotationspeed = 5;
        cooldown = 2;
        wait = 0;
        img = new Texture("Axethrower.png");
        sprite = new Sprite(img);
        rect = new Rectangle(pos.x, pos.y, img.getWidth()/2, img.getHeight());
        active = true;
        triggered = false;
        type = "Axethrower";
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
        for (int i = 0; i < AXETHROWER_COUNT; i++) {
            MyGdxGame.NPC.add(new Axethrower());
        }
    }

    public static void add() {
        MyGdxGame.NPC.add(new Axethrower());
    }


    public void draw(SpriteBatch batch) {
        //batch.draw(img, rect.x, rect.y);
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
        if (rect.y > Config.GROUND_Y - img.getHeight()*10) rect.x += speed*1.5;
        else soloRestart();
    }

    public void action() {
        if (rect.x < MyGdxGame.camera_width - 250 && active) {
            speed = 0;
            if (!triggered) wait = 0;
            triggered = true;
        }
        if (triggered && active)
            if (wait > cooldown)
            {
                wait = 0;
                MyGdxGame.projectiles.add(new Axe(rect.x + 2, rect.y + 70));
            }
        if (wait < cooldown)
            wait += Gdx.graphics.getDeltaTime();

    }

    public void soloRestart() {
        if (!dynamic) {
            sprite.setRotation(0);
            rect.x = (float) Math.random() * MyGdxGame.camera_width * Config.RANDOM_SPAWN_K + MyGdxGame.camera_width;
            rect.y = Config.GROUND_Y;
            vspeed = 0;
            rotation = 0;
            speed = 15f;
            wait = 0;
            triggered = false;
            active = true;
        }
        else  toremove = true;
    }
}
