package com.cowboyrunner.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.ConeShapeBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Svgood on 01.03.2017.
 */
public class Indian implements NPC{
    private Vector2 pos;
    static float speed;
    static float normal_speed;


    Texture img;
    Sprite sprite;
    Rectangle rect;
    boolean active;
    final static int INDIAN_COUNT = 0;


    //death
    float vspeed;
    float rotationspeed;
    Boolean dynamic;
    Boolean toremove;
    float rotation;

    String type;

    public Indian(){
        pos = new Vector2((float)Math.random()*MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width, (float)Config.GROUND_Y);
        speed = 15f;
        normal_speed = 15f;
        vspeed = 0;
        rotation = 0;
        rotationspeed = 5;
        img = new Texture("indianSpear.png");
        sprite = new Sprite(img);
        rect = new Rectangle(pos.x, pos.y, img.getWidth()/2, img.getHeight());
        active = true;
        type = "Indian";
        dynamic = false;
        toremove = false;
    }

    public Indian(boolean dyn){
        pos = new Vector2((float)Math.random()*MyGdxGame.camera_width*Config.RANDOM_SPAWN_K + MyGdxGame.camera_width, (float)Config.GROUND_Y);
        speed = 15f;
        normal_speed = 15f;
        vspeed = 0;
        rotation = 0;
        rotationspeed = 5;
        img = new Texture("indianSpear.png");
        sprite = new Sprite(img);
        rect = new Rectangle(pos.x, pos.y, img.getWidth()/2, img.getHeight());
        active = true;
        type = "Indian";
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
        for (int i = 0; i < INDIAN_COUNT; i++) {
            MyGdxGame.NPC.add(new Indian());
            //MyGdxGame.objects.add(indians.get(i));
        }
    }

    public static void add() {
        MyGdxGame.NPC.add(new Indian());
    }


    public void draw(SpriteBatch batch) {
        //batch.draw(img, pos.x, pos.y);
        sprite.setPosition(pos.x-img.getWidth()/4, pos.y);
        sprite.draw(batch);
    }

    public void update(SpriteBatch batch) {
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
                vspeed = MathUtils.random(10,14);
                rotationspeed = MathUtils.random(4,6);
                Player.bullets.remove(i);
            }
    }

    public void death() {
        rotation += rotationspeed;
        sprite.setRotation(rotation);
        vspeed -= 0.6;
        if (pos.y > Config.GROUND_Y - img.getHeight()*2) pos.x += speed*1.5;
        else soloRestart();
    }

    public void soloRestart() {
        if (!dynamic) {
            active = true;
            sprite.setRotation(0);
            pos.x = (float) Math.random() * MyGdxGame.camera_width * Config.RANDOM_SPAWN_K + MyGdxGame.camera_width;
            pos.y = Config.GROUND_Y;
            vspeed = 0;
            rotation = 0;
        }
        else toremove = true;
    }
}
