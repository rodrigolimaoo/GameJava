package com.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Trail extends GameObject{
	
	private float alpha = 1;
	private float life;

	private Handler handler;
	private Color color;
	
	private int width, height;
	
	public Trail(float x, float y, ID id, Color color, int width, int height, float life, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.color=color;
		this.width=width;
		this.height=height;
		this.life=life;	
	}

	@Override
	public void tick() {
		if(alpha > life) {
			alpha -= (life - 0.0001f);
		} else handler.removeObject(this);
		
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		
		g.setColor(color);
		g.fillRect((int)x,(int)y,width, height);
		
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type,alpha));
		
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
