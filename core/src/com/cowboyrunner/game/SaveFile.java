package com.cowboyrunner.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;


/**
 * Created by Svgood on 03.03.2017.
 */
public class SaveFile {
    public static int coins;
    public static float max_m;
    public static float firerate;
    public static float ammoreloadtime;
    public static Preferences prefs;
    public static int speed_k;
    static JsonValue json;

    public SaveFile(){

    }

    public static void init() {
        prefs = Gdx.app.getPreferences("Savedata");
        if (prefs.contains("coins")) {
            coins = prefs.getInteger("coins");
            max_m = prefs.getFloat("max_m");
            Player.firerate = prefs.getFloat("firerate");
            Player.ammoreloadtime = prefs.getFloat("ammoreloadtime");
            Player.head = new Texture(prefs.getString("current_head"));
            Player.torso = new Texture(prefs.getString("current_torso"));
            Player.legs = new Texture(prefs.getString("current_legs"));
            speed_k = prefs.getInteger("speed_k");
            Coin.koef = prefs.getInteger("koef");
        }
        //else create file
        else {
            prefs.putInteger("coins", 0);
            prefs.putFloat("max_m", 0);
            prefs.putFloat("firerate", 0.6f);
            prefs.putFloat("ammoreloadtime", 3f);
            prefs.putString("current_head", "head128.png");
            prefs.putString("current_torso", "torso128.png");
            prefs.putString("current_legs", "legs128.png");
            prefs.putInteger("koef", 1);
            prefs.putInteger("speed_k", 0);
            prefs.flush();
            coins = prefs.getInteger("coins");
            max_m = prefs.getFloat("max_m");
            firerate = prefs.getFloat("firerate");
            ammoreloadtime = prefs.getFloat("ammoreloadtime");
            Player.head = new Texture(prefs.getString("current_head"));
            Player.torso = new Texture(prefs.getString("current_torso"));
            Player.legs = new Texture(prefs.getString("current_legs"));
        }
    }

    public static void UpdateFile(float m, int coin){
        if (m > max_m) max_m = MathUtils.ceil(m);
        coins += coin;
        prefs.putInteger("coins", coins);
        prefs.putFloat("max_m", max_m);
        prefs.putFloat("firerate", Player.firerate);
        prefs.putFloat("ammoreloadtime", Player.ammoreloadtime);
        prefs.putString("current_head", UpgradeScreen.curhead);
        prefs.putString("current_torso", UpgradeScreen.curtorso);
        prefs.putString("current_legs", UpgradeScreen.curlegs);
        prefs.putInteger("koef", Coin.koef);
        prefs.putInteger("speed_k", speed_k);
        prefs.flush();
    }
    /*
    public static void init () {
        FileHandle file = Gdx.files.local("info.txt");
        String text = file.readString();
        json = new JsonReader().parse(text);
        Gdx.app.log("json", json.toString());
        coins = json.getInt("coins");
        max_m = json.getFloat("max_m");
        firerate = json.getDouble("firerate");
        ammoreloadtime = json.getDouble("ammoreloadtime");
    }

    public static String jsonify(){
        String text = "{\n" +
                "coins : " + coins + ",\n" +
                "max_m : " + max_m + ",\n" +
                "firerate : " + firerate + ",\n" +
                "ammoreloadtime : " + ammoreloadtime
                + "\n}";

        return text;
    }

    public static void UpdateFile(float m, int coin) {
        if (m > max_m) max_m = MathUtils.ceil(m);
        coins += coin;
        FileHandle towrite = Gdx.files.local("info.txt");
        towrite.writeString(jsonify() , false);
    }
    */

}
