package com.main;

import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;
import java.awt.Rectangle;

public class Player extends GameObject {

	Random r = new Random();
	Handler handler;

	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler=handler;
	}
	
	/*
	 * Setting the boundaries of the Player
	 */

	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,32,32);
	}
	
	@Override
	public void tick() {
		x += velX;
		y += velY;

		x = Game.clamp(x, 0, Game.WIDTH - 38);
		y = Game.clamp(y, 0, Game.HEIGHT - 60);
		
		handler.addObject(new Trail(x,y, ID.Trail, Color.white, 32,32, 0.02f, handler));
		
		collision();
		

	}
	/*
	 * Collision method to decrease health
	 */
	
	private void collision() {
		for(int i =0; i < handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					//collision code
					HUD.HEALTH -=2;
					
				}
			}
			
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, 32, 32);

	}

}
