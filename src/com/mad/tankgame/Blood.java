package com.mad.tankgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {
	private int[][] dirs;
	private int x, y, w, h;
	private int step;
	private boolean alive;
//	private TankClient tc;
	
	public Blood( ) {
		this.dirs = new int[][] {
				{350, 300},{ 360, 300},{375, 275},
				{ 400, 200},{ 360, 270},{365, 290},
				{ 340, 280},{345, 340}, { 370, 320}
		};
		this.step = 0;
		this.x = dirs[0][0];
		this.y = dirs[0][1];
		this.w = 20;
		this.h = 20;
		this.alive = true;
//		this.tc = tc;
	}
	
	public boolean isALive() {
		return alive;
	}

	public void setALive(boolean aLive) {
		this.alive = aLive;
	}

	public void draw(Graphics g) {
		if(!alive) return;
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		move();
	} 
	
	public void move(){
		step++;
		
		if( step == dirs.length ){
			step = 0;
		}
		x = dirs[step][0];
		y = dirs[step][1];
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x, y, w, h);
	}
	
}
