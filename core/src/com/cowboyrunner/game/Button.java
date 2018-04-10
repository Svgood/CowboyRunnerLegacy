package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Svgood on 22.02.2017.
 */
public class Button {
    Texture start_img, upgr_img, mag_img;
    Rectangle bt1,bt2,bt3;
    TextButton play_btn;
    Skin skin;
    Vector2 vec1,vec2,vec3;

    public Button() {
        start_img = new Texture("bt1.png");
        upgr_img = new Texture("bt2.png");
        mag_img = new Texture("bt3.png");

        vec1 = new Vector2(
                MyGdxGame.camera_width/2 - start_img.getWidth()-25,
                MyGdxGame.camera_height/2 - start_img.getHeight()/2);

        vec2 = new Vector2(
                MyGdxGame.camera_width/2 + start_img.getWidth()/6-25,
                MyGdxGame.camera_height/2 - start_img.getHeight()/2);

        bt1 = new Rectangle(
                vec1.x,
                vec1.y,
                start_img.getWidth(),
                start_img.getHeight());

        bt2 = new Rectangle(
                vec2.x,
                vec2.y,
                start_img.getWidth(),
                start_img.getHeight());

    }

    public void update(SpriteBatch batch){
        if (Gdx.input.isTouched())
        {
            if (bt1.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K)) {
                MyGdxGame.gameStance = "Game";
                MyGdxGame.unpause();
            }
            if (bt2.contains(Gdx.input.getX()*MyGdxGame.H_K, (Gdx.graphics.getHeight() - Gdx.input.getY())*MyGdxGame.V_K))
                MyGdxGame.gameStance = "Upgrade";
        }
        draw(batch);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(start_img, vec1.x, vec1.y);
        batch.draw(upgr_img, vec2.x, vec2.y);
    }
}
