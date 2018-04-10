package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Svgood on 08.03.2017.
 */
public class UpgradeScreen {

    Texture img_bg, back_bt, buy_bt, next_bt, head, torso, legs, upgradebtn, img_bg1, img_bg2, coin, img_bg3, img_bg4;
    Sprite shead, storso, slegs, sbg, scoin, scoin1;
    Rectangle rect_bg, rect_backbt, rect_buybt, rect_nextbt;
    BitmapFont font, upgrfont;
    int currwindow, subwindow;
    int lvl1, lvl2, lvl3, lvl4;

    //For Charachter Edit
    Rectangle arrow1, arrow2, arrow3, arrow4, arrow5, arrow6, wind1_arrow1, wind1_arrow2, upgradebtn_rect;
    Texture arrow_img, rarrow_img;
    //Shmot
    String [] head_texts = {"head128.png","head128_1.png", "head128_2.png", "head128_3.png", "head128_4.png", "head128_5.png", "head128_6.png"};
    String [] tors_texts = {"torso128.png", "torso128_1.png", "torso128_2.png", "torso128_3.png", "torso128_4.png", "torso128_5.png", "torso128_6.png"};
    String [] legs_texts = {"legs128.png", "legs128_1.png", "legs128_2.png", "legs128_3.png", "legs128_4.png", "legs128_5.png", "legs128_6.png"};
    int hpointer;
    int tpointer;
    int lpointer;
    //for subwindows
    Texture barrel, barrel1, barrel2, barrel3, barrel4, barrel5, barrel6;
    Sprite brl;
    float rotation;

    int upgr_cost1;
    int upgr_cost2;
    int upgr_cost3;
    int upgr_cost4;


    public static String curhead = "";
    public static String curtorso = "";
    public static String curlegs = "";

    float wait;

    UpgradeScreen () {


        lvl1 = 1 + (int)((0.6f - Math.floor(Player.firerate*100)/100)/0.02);
        lvl2 = 1 + (int)((3f - Math.floor(Player.ammoreloadtime*10)/10)/0.1);
        lvl3 = Coin.koef;
        lvl4 = SaveFile.speed_k+1;

        upgr_cost1 = 150;
        upgr_cost2 = 150;
        upgr_cost3 = 1000;
        upgr_cost4 = 500;
        //Texture
        rotation = 0;
        barrel = new Texture("barrel.png");
        barrel1 = new Texture("barrel1.png");
        barrel2 = new Texture("barrel2.png");
        barrel3 = new Texture("barrel3.png");
        barrel4 = new Texture("barrel4.png");
        barrel5 = new Texture("barrel5.png");
        barrel6 = new Texture("barrel6.png");
        brl = new Sprite(barrel);

        hpointer = 0;
        tpointer = 0;
        lpointer = 0;

        for (int i = 0; i < head_texts.length; i++)
            if (SaveFile.prefs.getString("current_head").equals(head_texts[i])) hpointer = i;
        for (int i = 0; i < tors_texts.length; i++)
            if (SaveFile.prefs.getString("current_torso").equals(tors_texts[i])) tpointer = i;
        for (int i = 0; i < legs_texts.length; i++)
            if (SaveFile.prefs.getString("current_legs").equals(legs_texts[i])) lpointer = i;

        curhead = head_texts[hpointer];
        curtorso = tors_texts[tpointer];
        curlegs = legs_texts[lpointer];


        wait = 0;
        currwindow = 1;
        subwindow = 1;

        coin = new Texture("coin.png");
        scoin = new Sprite(coin);
        scoin1 = new Sprite(coin);
        scoin.setScale(0.8f);
        scoin1.setScale(0.8f);

        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.getData().setScale(0.9f);
        upgrfont = new BitmapFont(Gdx.files.internal("font.fnt"));
        upgrfont.getData().setScale(0.5f);

        head = new Texture(head_texts[hpointer]);
        torso = new Texture(tors_texts[tpointer]);
        legs = new Texture(legs_texts[lpointer]);
        arrow_img = new Texture("arrow.png");
        rarrow_img = new Texture("rarrow.png");

        shead = new Sprite(head);
        storso = new Sprite(torso);
        slegs = new Sprite(legs);
        shead.setScale(3.5f);
        storso.setScale(3.5f);
        slegs.setScale(3.5f);

        img_bg = new Texture("upgradebg.png");
        img_bg1 = new Texture("upgradebg1.png");
        img_bg2 = new Texture("upgradebg2.png");
        img_bg3 = new Texture("upgradebg3.png");
        img_bg4 = new Texture("upgradebg4.png");
        sbg = new Sprite(img_bg1);


        buy_bt = new Texture("buybttest.png");
        upgradebtn = new Texture("upgradebtn.png");
        //buy_bt.
        back_bt = new Texture("backbt.png");
        next_bt = new Texture("nextbt.png");

        rect_bg = new Rectangle(250, 200,
                img_bg.getWidth(),
                img_bg.getHeight());

        rect_backbt = new Rectangle(250, 50,
                back_bt.getWidth(),
                back_bt.getHeight());

        rect_nextbt = new Rectangle(815, 50,
                back_bt.getWidth(),
                back_bt.getHeight());
        rect_buybt = new Rectangle(875, 250,
                buy_bt.getWidth(),
                buy_bt.getHeight());
        upgradebtn_rect = new Rectangle(530, 50,
                upgradebtn.getWidth(),
                upgradebtn.getHeight());
        sbg.setPosition(250, 200);

        //arrows
        arrow1 = new Rectangle(250, 215,
                arrow_img.getWidth(),
                arrow_img.getHeight());

        arrow2 = new Rectangle(250, 340,
                arrow_img.getWidth(),
                arrow_img.getHeight());

        arrow3 = new Rectangle(250, 465,
                arrow_img.getWidth(),
                arrow_img.getHeight());

        arrow4 = new Rectangle(960, 215,
                arrow_img.getWidth(),
                arrow_img.getHeight());
        arrow5 = new Rectangle(960, 340,
                arrow_img.getWidth(),
                arrow_img.getHeight());
        arrow6 = new Rectangle(960, 465,
                arrow_img.getWidth(),
                arrow_img.getHeight());

        wind1_arrow1 = new Rectangle(120, 390,
                arrow_img.getWidth(),
                arrow_img.getHeight());

        wind1_arrow2 = new Rectangle(1100, 390,
                arrow_img.getWidth(),
                arrow_img.getHeight());


    }

    void update(SpriteBatch batch) {

        wait += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isTouched() && MyGdxGame.touch && rect_backbt.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
        {
            SaveFile.UpdateFile(0,0);
            if (currwindow > 1) currwindow -= 1;
            else MyGdxGame.gameStance = "Menu";
        }
        if (Gdx.input.isTouched() && MyGdxGame.touch && rect_nextbt.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
        {
            if (currwindow + 1 < 3) currwindow += 1;
        }

        //Different windows
        if (currwindow == 1) {
            //Subwindow changes
            if (Gdx.input.isTouched() && MyGdxGame.touch && wind1_arrow1.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K)) {
                if (subwindow > 1){
                    subwindow -= 1;
                    brl.setRotation(0);
                }
            }
            if (Gdx.input.isTouched() && MyGdxGame.touch && wind1_arrow2.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K)){
                if (subwindow < 4){
                    subwindow += 1;
                    brl.setRotation(0);
                }
            }
            //Upgrades
            if (Gdx.input.isTouched() && MyGdxGame.touch && upgradebtn_rect.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K)){
                if (subwindow == 1 && SaveFile.coins - upgr_cost1*lvl1 > 0 && lvl1 < 11){
                    Player.firerate -= 0.02;
                    SaveFile.UpdateFile(0, -upgr_cost1*lvl1);
                    lvl1 += 1;
                }
                if (subwindow == 2 && SaveFile.coins - upgr_cost2*lvl2 > 0 && lvl2 < 11){
                    Player.ammoreloadtime -= 0.1;
                    SaveFile.UpdateFile(0, -upgr_cost2*lvl2);
                    lvl2 += 1;
                }
                if (subwindow == 3 && SaveFile.coins - upgr_cost3*lvl3 > 0 && lvl3 < 5){
                    Coin.koef += 1;
                    SaveFile.UpdateFile(0, -upgr_cost3*lvl3);
                    lvl3 += 1;
                }
                if (subwindow == 4 && SaveFile.coins - upgr_cost4*lvl4 > 0 && lvl4 < 6){
                    SaveFile.speed_k += 1;
                    SaveFile.UpdateFile(0, -upgr_cost4*lvl4);
                    lvl4 += 1;
                }
            }

            if (subwindow == 1) {
                sbg.setTexture(img_bg1);
            }
            if (subwindow == 2){
                sbg.setTexture(img_bg2);
            }
            if (subwindow == 3){
                sbg.setTexture(img_bg3);
            }
            if (subwindow == 4){
                sbg.setTexture(img_bg4);
            }
        }

        if (currwindow == 2) {
            if (Gdx.input.isTouched() && MyGdxGame.touch && arrow6.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
                if (hpointer + 1 < head_texts.length) {
                    hpointer += 1;
                    shead.setTexture(new Texture(head_texts[hpointer]));
                    Player.head = new Texture(head_texts[hpointer]);
                    curhead = head_texts[hpointer];
                }
            if (Gdx.input.isTouched() && MyGdxGame.touch && arrow3.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
                if (hpointer - 1 >= 0) {
                    hpointer -= 1;
                    shead.setTexture(new Texture(head_texts[hpointer]));
                    Player.head = new Texture(head_texts[hpointer]);
                    curhead = head_texts[hpointer];
                }
            if (Gdx.input.isTouched() && MyGdxGame.touch && arrow4.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
                if (lpointer + 1 < legs_texts.length) {
                    lpointer += 1;
                    slegs.setTexture(new Texture(legs_texts[lpointer]));
                    Player.legs = new Texture(legs_texts[lpointer]);
                    curlegs = legs_texts[lpointer];
                }
            if (Gdx.input.isTouched() && MyGdxGame.touch && arrow1.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
                if (lpointer - 1 >= 0) {
                    lpointer -= 1;
                    slegs.setTexture(new Texture(legs_texts[lpointer]));
                    Player.legs = new Texture(legs_texts[lpointer]);
                    curlegs = legs_texts[lpointer];
                }
            if (Gdx.input.isTouched() && MyGdxGame.touch && arrow2.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
                if (tpointer - 1 >= 0) {
                    tpointer -= 1;
                    storso.setTexture(new Texture(tors_texts[tpointer]));
                    Player.torso = new Texture(tors_texts[tpointer]);
                    curtorso = tors_texts[tpointer];
                }
            if (Gdx.input.isTouched() && MyGdxGame.touch && arrow5.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
                if (tpointer + 1 < tors_texts.length) {
                    tpointer += 1;
                    storso.setTexture(new Texture(tors_texts[tpointer]));
                    Player.torso = new Texture(tors_texts[tpointer]);
                    curtorso = tors_texts[tpointer];
                }

        }
        draw(batch);
    }

    void draw(SpriteBatch batch) {


        if (currwindow != 2) batch.draw(next_bt, rect_nextbt.x, rect_nextbt.y);
        batch.draw(back_bt, rect_backbt.x, rect_backbt.y);

        if (currwindow == 1) {
           // batch.draw(img_bg, rect_bg.x, rect_bg.y);
            sbg.draw(batch);
            batch.draw(upgradebtn, upgradebtn_rect.x, upgradebtn_rect.y);
            scoin.setPosition(560, 60);
            scoin.draw(batch);
            scoin1.setPosition(700, 60);
            scoin1.draw(batch);

            if (subwindow != 1) batch.draw(arrow_img, wind1_arrow1.x, wind1_arrow1.y);
            if (subwindow != 4) batch.draw(rarrow_img, wind1_arrow2.x, wind1_arrow2.y);
            if (subwindow == 1) {
                font.draw(batch, "Time: " + Math.floor(Player.firerate*100)/100 + "s", 300, 335);
                font.draw(batch, "Level: " + lvl1, 670, 335);
                if (lvl1 < 11)
                    upgrfont.draw(batch, "" + upgr_cost1*lvl1, 625, 110);
                else upgrfont.draw(batch, "MAX", 625, 110);
                if (wait < 0.6) {
                    rotation += 1.05 / Player.firerate;
                    brl.setRotation(rotation);
                }
                if (wait > 1.2) wait = 0;
                brl.setPosition(420, 420);
                brl.draw(batch);
            }
            if (subwindow == 2) {
                font.draw(batch, "Time: " + Math.floor(Player.ammoreloadtime*100)/100 + "s", 300, 335);
                font.draw(batch, "Level: " + lvl2, 670, 335);
                if (lvl2 < 11)
                    upgrfont.draw(batch, "" + upgr_cost2*lvl2, 625, 110);
                else upgrfont.draw(batch, "MAX", 625, 110);
                rotation += 8;
                brl.setRotation(rotation);
                brl.draw(batch);
            }
            if (subwindow == 3) {
                font.draw(batch, "Multiply: x" + lvl3, 300, 335);
                font.draw(batch, "Level: " + lvl3, 670, 335);
                if (lvl3 < 5)
                    upgrfont.draw(batch, "" +upgr_cost3*lvl3, 625, 110);
                else upgrfont.draw(batch, "MAX", 625, 110);
            }
            if (subwindow == 4) {
                font.draw(batch, "Speed: " + (50 + (50/5*SaveFile.speed_k)) + "m/s", 300, 335);
                font.draw(batch, "Level: " + lvl4, 720, 335);
                if (lvl4 < 6)
                    upgrfont.draw(batch, "" +upgr_cost4*lvl4, 625, 110);
                else upgrfont.draw(batch, "MAX", 625, 110);
            }


        }
        if (currwindow == 2) {
            shead.setPosition(675, 375);
            storso.setPosition(675, 375);
            slegs.setPosition(675, 375);
            shead.draw(batch);
            storso.draw(batch);
            slegs.draw(batch);
            //arrows
            if (lpointer != 0) batch.draw(arrow_img, arrow1.x, arrow1.y);
            if (tpointer != 0) batch.draw(arrow_img, arrow2.x, arrow2.y);
            if (hpointer != 0) batch.draw(arrow_img, arrow3.x, arrow3.y);
            if (lpointer != legs_texts.length-1) batch.draw(rarrow_img, arrow4.x, arrow4.y);
            if (tpointer != tors_texts.length-1) batch.draw(rarrow_img, arrow5.x, arrow5.y);
            if (hpointer != head_texts.length-1) batch.draw(rarrow_img, arrow6.x, arrow6.y);
        }
    }
}
