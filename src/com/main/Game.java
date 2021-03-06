package com.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -14500600434232432L;

	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

	private Thread thread;
	private boolean running = false;
	private HUD hud;
	private Spawn spawner;

	private Random r;
	private Handler handler;

	public Game() {
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));

		new Window(WIDTH, HEIGHT, "Let's Build a Game!", this);

		hud = new HUD();
		spawner = new Spawn(handler, hud);

		r = new Random();

		handler.addObject(new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler));
		handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH -50), r.nextInt(Game.HEIGHT -50), ID.BasicEnemy, handler));

	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.requestFocus(); // auto-click and run of the game to use keyinput
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}

			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}

		}
		stop();
	}

	private void tick() {
		handler.tick();
		hud.tick();
		spawner.tick();

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black); // background color
		g.fillRect(0, 0, WIDTH, HEIGHT); // size of screen

		handler.render(g);

		hud.render(g); // render health display

		g.dispose();
		bs.show();

	}
	/*
	 * Method to set boundaries for objects
	 */

	public static float clamp(float var, float min, float max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else
			return var;
	}

	public static void main(String[] args) {
		new Game();
	}
}
