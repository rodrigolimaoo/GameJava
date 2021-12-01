package com.main;

import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;

public class Player extends GameObject {

	Random r = new Random();

	public Player(int x, int y, ID id) {
		super(x, y, id);

	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
	}

	@Override
	public void render(Graphics g) {
		if (id == ID.Player) {
			g.setColor(Color.white);
		} else {
			g.setColor(Color.blue);
		}
		g.fillRect(x, y, 32, 32);

	}

}
