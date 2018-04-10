package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Svgood on 17.04.2017.
 */
public class Nameplace {
    static int nameplacecontrol;
    Vector2 pos;
    Texture np1,np2,np3,np4,np5,np6,np7,np8,np9;
    Sprite snp;
    boolean moveout, movein;
    float counter;

    public Nameplace(int x, int y){
        nameplacecontrol = 1;
        pos = new Vector2(x,y);
        np1 = new Texture("nameplace1.png");
        np2 = new Texture("nameplace2.png");
        np3 = new Texture("nameplace3.png");
        np4 = new Texture("nameplace4.png");
        np5 = new Texture("nameplace5.png");
        np6 = new Texture("nameplace6.png");
        np7 = new Texture("nameplace7.png");
        np8 = new Texture("nameplace8.png");
        np9 = new Texture("nameplace9.png");
        snp = new Sprite(np1);
        snp.setPosition(pos.x, pos.y);
        moveout = false;
        movein = false;
        counter = 0;
    }

    public void update(SpriteBatch batch) {
        snp.draw(batch);
        snp.setPosition(pos.x, pos.y);
        nameplaceController();
        if (moveout) moveOut();
        if (movein) moveIn();
    }


    public void nameplaceController() {
        if (Player.total_m > 1000 && nameplacecontrol == 1){
            Player.coins += 25;
            snp.setTexture(np1);
            nameplacecontrol += 1;
            moveout = true;
            counter = 0;
        }
        if (Player.total_m > 2000 && nameplacecontrol == 2){
            Player.coins += 75;
            snp.setTexture(np2);
            nameplacecontrol += 1;
            moveout = true;
            counter = 0;
        }
        if (Player.total_m > 3000 && nameplacecontrol == 3){
            Player.coins += 200;
            snp.setTexture(np3);
            nameplacecontrol += 1;
            moveout = true;
            counter = 0;
        }
        if (Player.total_m > 4000 && nameplacecontrol == 4){
            Player.coins += 250;
            snp.setTexture(np4);
            nameplacecontrol += 1;
            moveout = true;
            counter = 0;
        }
        if (Player.total_m > 5000 && nameplacecontrol == 5){
            Player.coins += 300;
            snp.setTexture(np5);
            nameplacecontrol += 1;
            moveout = true;
            counter = 0;
        }
        if (Player.total_m > 6000 && nameplacecontrol == 6){
            Player.coins += 350;
            snp.setTexture(np6);
            nameplacecontrol += 1;
            moveout = true;
            counter = 0;
        }
        if (Player.total_m > 7000 && nameplacecontrol == 7){
            Player.coins += 400;
            snp.setTexture(np7);
            nameplacecontrol += 1;
            moveout = true;
            counter = 0;
        }
        if (Player.total_m > 8000 && nameplacecontrol == 8){
            Player.coins += 450;
            snp.setTexture(np8);
            nameplacecontrol += 1;
            moveout = true;
            counter = 0;
        }
        if (Player.total_m > 9000 && nameplacecontrol == 9){
            Player.coins += 500;
            snp.setTexture(np9);
            nameplacecontrol += 1;
            moveout = true;
            counter = 0;
        }
    }

    public void moveOut() {
        if (pos.y > 600) pos.y -= 15;
        counter += Gdx.graphics.getDeltaTime();
        if (counter > 3.5) {
            moveout = false;
            movein = true;
        }
    }

    public void moveIn() {
        pos.y += 15;
        if (pos.y > MyGdxGame.camera_height) movein = false;
    }
}
