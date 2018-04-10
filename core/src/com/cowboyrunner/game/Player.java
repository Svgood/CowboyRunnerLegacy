package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by Svgood on 21.02.2017.
 */
public class Player {
    Vector2 pos = new Vector2((float)200, (float)200);;
    static Texture img, head, legs, torso;
    boolean flying;
    boolean falling;
    double vspeed;
    double hspeed;
    double wait;
    float restart_pos;

    static double reload;
    public static float firerate;
    float ammoreload;
    public static float ammoreloadtime;
    static int ammo;

    int hp;
    int start_hp;

    static float total_m;
    float m_counter;
    public static int coins;

    public static Rectangle rect;

    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public String getTotal_m() {
        return Integer.toString((int)total_m);
    }

    public String getAmmo() {
        if (ammo != 0) return Integer.toString(ammo);
        else return "Reloading";
    }

    public Player() {
        restart_pos = 1.2f;
        img = new Texture("player.png");
        head = new Texture("head128.png");
        torso = new Texture("torso128.png");
        legs = new Texture("legs128.png");
        pos = new Vector2((float)(img.getWidth()*restart_pos), (float)Config.GROUND_Y);
        flying = false;
        falling = false;
        m_counter = 0;
        vspeed = 0;
        hspeed = 5;
        //starting firerate 0.6
        firerate = 0.6f;
        //starting ammoreloadtime 3
        ammoreloadtime = 3;
        total_m = 0f;
        ammo = 6;
        rect = new Rectangle(pos.x,pos.y,img.getWidth()/3.5f, img.getHeight());
        coins = 0;
        hp = 1;
        start_hp = 1;
    }

    void update(SpriteBatch batch) {
        draw(batch);
        jump();
        if (!MyGdxGame.pause) {
            getColisionEnemy();
            if(wait > 0.5)
                fire();
            reload();
            bulletsupdate();
            coinCreator();
            total_m += (50 + (50/5*SaveFile.speed_k)) * Gdx.graphics.getDeltaTime();
            m_counter += 50 * Gdx.graphics.getDeltaTime();
            wait += Gdx.graphics.getDeltaTime(); //Wait till first jump or attack before start
            rect.y = pos.y;
        }
    }

    void jump() {
        MyGdxGame.touchcontrol();
        pos.y += vspeed;
        if (Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth()/2 && !flying && wait > 0.5 && !MyGdxGame.pause){
            flying = true;
            falling = false;
            pos.y += 0.001;
            vspeed = 24;
        }
        if (flying && !MyGdxGame.touch && !falling) {
            if (vspeed > 0)
                vspeed -= 0.6;
        }

        if (falling) vspeed -= 1.0f;
        if (vspeed <= 0) falling = true;

        if (MyGdxGame.touch) {
            falling = true;
        }

        if (pos.y + vspeed <= Config.GROUND_Y) {
            flying = false;
            falling = false;
            vspeed = 0;
            pos.y = Config.GROUND_Y;
        }
    }

    void fire(){
        if (Gdx.input.isTouched() && reload >= firerate && ammo > 0 && Gdx.input.getX() > Gdx.graphics.getWidth()/2) {
            bullets.add(new Bullet(pos.x+40, pos.y+36));
            reload = 0;
            ammo -= 1;
            ammoreload = 0;
        }
        if (ammo == 0){
            ammoreload += Gdx.graphics.getDeltaTime();
            if (ammoreload > ammoreloadtime) ammo = 6;
        }


    }

    void reload(){
        if (reload < firerate)
            reload += Gdx.graphics.getDeltaTime();
    }

    void bulletsupdate() {
        for(int i = 0; i < bullets.size(); i++)
        {
            if (bullets.get(i).active) bullets.get(i).update();
            else  bullets.remove(i);
        }

    }

    void draw(SpriteBatch batch) {
        batch.draw(head, pos.x, pos.y);
        batch.draw(torso, pos.x, pos.y);
        batch.draw(legs, pos.x, pos.y);
        for (int i = 0; i < bullets.size(); i++)
            bullets.get(i).draw(batch);
    }

    void getColisionEnemy () {
        for (int i = 0; i < MyGdxGame.NPC.size; i++)
            if (rect.overlaps(MyGdxGame.NPC.get(i).getRect()) && MyGdxGame.NPC.get(i).getActive()) {
                MyGdxGame.death = true;
            }
        for (int i = 0; i < MyGdxGame.projectiles.size; i++)
            if (rect.overlaps(MyGdxGame.projectiles.get(i).getRect()))
                MyGdxGame.death = true;
    }
    void death() {
        SaveFile.UpdateFile(total_m, coins);
        pos.x = (float) (img.getWidth() * restart_pos);
        total_m = 0;
        m_counter = 0;
        ammo = 6;
        coins = 0;
        wait = 0;
        Towers.restart();
        Coin.restart();
        MyGdxGame.npcRestart();
    }

    void coinCreator() {
        if (m_counter > 200){
            Coin.create();
            m_counter = 0;
        }
    }

    void dispose() {
        img.dispose();
        head.dispose();
        legs.dispose();
        torso.dispose();
    }
}
