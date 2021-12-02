package com.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SmartEnemy extends GameObject {

	private Handler handler;
	private GameObject player;
	
	
	public SmartEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		for (int i=0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getId() == ID.Player) player = handler.object.get(i);
		}
		
		velX = 5;
		velY = 5;
	}
	

	/*
	 * Setting the boundaries of the Smart enemy
	 */

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		float diffX = x - player.getX() - 8;
		float diffY = x - player.getY() - 8;
		float distance = (float) Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));
		
		//velX = (float) ((-1.0/distance) * diffX);
		//velY = (float) ((-1.0/distance) * diffY);
		velX = Game.clamp(velX, -1, 1);
		velY = Game.clamp(velY, -1, 1);

		
		if(Float.isNaN(velX)){
            velX = 0;
            velY = 0;
        }
		handler.addObject(new Trail(x,y, ID.Trail, Color.green, 16,16, 0.02f, handler));

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int)x, (int)y, 16, 16);

	}

}
