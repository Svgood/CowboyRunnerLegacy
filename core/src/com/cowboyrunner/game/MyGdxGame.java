package com.cowboyrunner.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.prism.impl.paint.PaintUtil;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	BitmapFont statusfont;
	//

	Bg [] bg;
	Ground [] ground;
	Player player;
	Barrel barrel;

	OrthographicCamera camera;
	Viewport viewport;

	Button bt;
	double logtime;

	public static String gameStance = "Menu";
	public static int camera_width = 1280;
	public static int camera_height = 720;

	public static float V_K;
	public static float H_K;


	public final int BG_COUNT = 2;
	public final int GROUND_COUNT = 3;
	public static Array<NPC> NPC = new Array<NPC>();
	public static Array<Projectiles> projectiles = new Array<Projectiles>();

	//pause
	public static boolean pause;
	static double wait;
	static boolean death;
	boolean first_time;
	//topbar in game
	Pausebtn pausebtn;
	Texture star;
	Sprite sstar;
	Texture m;
	Sprite sm;
	Nameplace nameplace;

	//UpgradeScreen
	UpgradeScreen upgradeScreen;
	//to switch screens;
	static boolean touch;
	float gameovertime;
	//difficulty
	static float difficulty;
	float counter;
	float dynamic_counter;
	float rand;
	@Override
	public void create () {

		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();

		touch = true;
		difficulty = 0;
		counter = 0;
		//FOnts
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		statusfont = new BitmapFont(Gdx.files.internal("statusfont.fnt"));
		font.getData().setScale(1);

		V_K = camera_height/h;
		H_K = camera_width/w;

		//init game comps
		int pos = 0;
		//CAMERA
		camera = new OrthographicCamera();
		camera.setToOrtho(false, camera_width, camera_height);
		viewport = new FitViewport(camera_width, camera_height, camera);
		//
		batch = new SpriteBatch();
		player = new Player();
		bg = new Bg[BG_COUNT];
		ground = new Ground[GROUND_COUNT];
		//Create NPC objects
		SaveFile.init();
		Cactus.create();
		Towers.create();
		Towers.createVigvam();
		Indian.create();
		Jumper.create();
		Axethrower.create();

		for (int i = 0; i < BG_COUNT; i++) {
			bg[i] = new Bg(pos);
			pos += 1280;
		}
		pos = 0;
		for (int i = 0; i < GROUND_COUNT ; i++){
			ground[i] = new Ground(pos);
			pos += 1260;
		}
		//init menu
		bt = new Button();
		//upgradeScreen
		upgradeScreen = new UpgradeScreen();
		//for logging
		logtime = 0;
		//pause
		pause = false;
		wait = 0;
		death = false;
		gameovertime = 0;
		first_time = false;

		//topbar
		nameplace = new Nameplace(430, camera_height);
		star = new Texture("coin.png");
		m = new Texture("m.png");
		sm = new Sprite(m);
		sstar = new Sprite(star);
		sstar.setScale(1.2f);
		sm.setScale(1.2f);
		pausebtn = new Pausebtn(75, camera_height - 110);
		barrel = new Barrel(1100, camera_height - 150);

		rand = 0;
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		if (gameStance.equals("Menu"))
			updateMenu();
		if (gameStance.equals("Upgrade"))
			updateUpgrade();
		touchcontrol();
		if (gameStance.equals("Game"))
			updateGame();

		//font.draw(batch, "X: " + Gdx.input.getX() + "Y: " + Gdx.input.getY(),70, 600 );
		batch.end();
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
			SaveFile.coins += 1000;
	}
	


	public void updateGame() {
		objectsUpdating();
		gameOver();
		fontsDraw();
		//logging();
		difficultcontrol();
		//pause();

	}

	public void updateMenu() {
		for (int i = 0; i < BG_COUNT; i++)
			bg[i].update(batch);
		bt.update(batch);
		font.draw(batch, "Max Distance: " + (int)SaveFile.max_m + "   " + "Coins: " + SaveFile.coins, 70, 130);
		logging();
	}

	public void updateUpgrade() {
		for (int i = 0; i < BG_COUNT; i++)
			bg[i].update(batch);
		upgradeScreen.update(batch);
		logging();
	}

	public void objectsUpdating() {
		for (int i = 0; i < BG_COUNT; i++)
			bg[i].update(batch);
		for (int i = 0; i < GROUND_COUNT; i++)
			ground[i].update(batch);
		for (int i = 0; i < Towers.towers.size; i++)
			Towers.towers.get(i).update(batch);
		for (int i = 0; i < Coin.coins.size; i++)
			Coin.coins.get(i).update(batch);
		for (int i = 0; i < NPC.size; i++) {
			NPC.get(i).update(batch);
			if (NPC.get(i).getToRemove())
				NPC.removeIndex(i);
		}
		for (int i = 0; i < projectiles.size; i++)
			projectiles.get(i).update(batch);

		player.update(batch);
		pausebtn.update(batch);
		barrel.update(batch);
		nameplace.update(batch);
	}

	public void logging() {
		if (logtime > 3) {
			Gdx.app.log("Java heap", Long.toString(Gdx.app.getJavaHeap() / (1024 * 1024)));
			Gdx.app.log("Native heap", Long.toString(Gdx.app.getNativeHeap() / (1024 * 1024)));
			Gdx.app.log("Game stance", gameStance);
			Gdx.app.log("Koef", Float.toString(V_K));
			Gdx.app.log("IsTouched", Boolean.toString(touch));
			logtime = 0;
		}
		logtime += Gdx.graphics.getDeltaTime();
	}

	public void fontsDraw() {
		sstar.setPosition(200, camera_height-110);
		statusfont.draw(batch, ": " + player.coins, 280, camera_height-42);
		sstar.draw(batch);
		sm.setPosition(810, camera_height-110);
		sm.draw(batch);
		statusfont.draw(batch, ": " + player.getTotal_m(), 885, camera_height-42);
		//statusfont.draw(batch, "Ammo: " + player.getAmmo(), 900, camera_height-50);
	}

	public static void makepause(){
		wait += Gdx.graphics.getDeltaTime();
		if (wait > 1)
			if (!pause) {
				Coin.speed = 0f;
				Towers.speed = 0f;
				Cactus.speed = 0f;
				Indian.speed = 0f;
				Jumper.speed = 0f;
				Axethrower.speed = 0f;
				Ground.speed = 0f;
				Axethrower.cooldown = 1000;
				pause = true;
				wait = 0;
		} else
			unpause();

	}

	public void gameOver() {
		if (death) {
			font.draw(batch, "GAME OVER", camera_width / 2 - 210, camera_height / 2 + 60);
			font.draw(batch, "Tap anywhere to continue", camera_width / 2 - 360, camera_height / 2 - 40);
			Coin.speed = 0f;
			Towers.speed = 0f;
			Cactus.speed = 0f;
			Indian.speed = 0f;
			Jumper.speed = 0f;
			Axethrower.speed = 0f;
			Ground.speed = 0f;
			Axethrower.cooldown = 1000;
			//diff
			difficulty = 0;
			counter = 0;
			dynamic_counter = 0;
			//
			if (!pause) gameovertime = 0;
			pause = true;
			gameovertime += Gdx.graphics.getDeltaTime();
			if (Gdx.input.isTouched() && gameovertime > 0.5) {
				NPC.clear();
				Towers.towers.clear();
				Towers.createVigvam();
				Towers.create();
				Cactus.create();
				player.death();
				Nameplace.nameplacecontrol = 1;
				unpause();
				death = false;
			}
		}
	}

	public static void unpause() {
		Coin.speed = 12f;
		Towers.speed = 6f;
		Cactus.speed = 12f;
		Indian.speed = 15f;
		Jumper.speed = 15f;
		Axethrower.speed = 15f;
		Ground.speed = 12f;
		Axethrower.cooldown = 2;
		wait = 0;
		pause = false;
	}

	public static void npcRestart() {
		for (int i =0; i < NPC.size; i++)
			NPC.get(i).soloRestart();
	}

	public static void touchcontrol (){
		if (!Gdx.input.isTouched())
			touch = true;
		else touch = false;
	}

	public void difficultcontrol() {
		counter += 50*Gdx.graphics.getDeltaTime();
		dynamicEncounter();
		if (counter > 1000 && difficulty == 0) {
			Indian.add();
			difficulty += 1;
			Towers.createVigvam();
		}
		if (counter > 2000 && difficulty == 1) {
			Cactus.add();
			difficulty += 1;
			Towers.createVigvam();
		}
		if (counter > 4000 && difficulty == 2) {
			Indian.add();
			difficulty += 1;
			Towers.createVigvam();
		}
		if (counter > 7000 && difficulty == 3) {
			Cactus.add();
			difficulty += 1;
			Towers.createVigvam();
		}
		if (counter > 10000 && difficulty == 4) {
			Axethrower.add();
			difficulty += 1;
			Towers.createVigvam();
		}
		if (counter > 12000 && difficulty == 5) {
			Cactus.add();
			difficulty += 1;
			Towers.createVigvam();
		}
	}

	public void dynamicEncounter(){
		dynamic_counter += 50*Gdx.graphics.getDeltaTime();
		if (dynamic_counter > 450) {
			if (difficulty >= 3)
				rand = (float)Math.floor(MathUtils.random(3,8));
			else if (difficulty == 1)
				rand = (float)Math.floor(MathUtils.random(0,5));
			else rand = (float)Math.floor(MathUtils.random(0,3));
			dynamic_counter = 0;
			if (rand == 0) {
				NPC.add(new Indian(true));
				Gdx.app.log("Added", "1 Indian");
			}
			if (rand == 1) {
				NPC.add(new Jumper(true));
				Gdx.app.log("Added", "1 Jumper");
			}
			if (rand == 2) {
				NPC.add(new Axethrower(true));
				Gdx.app.log("Added", "1 Axe");
			}
			if (rand == 3) {
				NPC.add(new Indian(true));
				NPC.add(new Indian(true));
				Gdx.app.log("Added", "2 Indian");
			}
			if (rand == 6) {
				NPC.add(new Jumper(true));
				NPC.add(new Jumper(true));
				Gdx.app.log("Added", "2 Jumper");
			}
			if (rand == 4) {
				NPC.add(new Axethrower(true));
				NPC.add(new Indian(true));
				Gdx.app.log("Added", "1 Ind 1 Axe");
			}
			if (rand == 7) {
				NPC.add(new Axethrower(true));
				NPC.add(new Jumper(true));
				Gdx.app.log("Added", "1 Jump 1 Axe");
			}
			if (rand == 5) {
				NPC.add(new Indian(true));
				NPC.add(new Jumper(true));
				Gdx.app.log("Added", "1 Ind 1 Jump");
			}
			if (rand == 8) {
				NPC.add(new Indian(true));
				NPC.add(new Indian(true));
				NPC.add(new Indian(true));
				Gdx.app.log("Added", "3 Indian");
			}
			Gdx.app.log("Rand", Float.toString(rand));
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
		NPC.clear();
		Player.bullets.clear();
	}

	public void resize(){
		viewport.update(camera_width,camera_height);
	}

}
