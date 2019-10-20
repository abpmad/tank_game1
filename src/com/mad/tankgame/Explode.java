package com.mad.tankgame;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	private int x, y;
	private boolean aLive;
	private TankClient tc;
	
	private int[] diameter;
	private int step;
	
	public Explode(int x, int y, TankClient tc ){
		this.x = x;
		this.y = y;
		this.aLive = true;
		this.tc = tc;
		
		diameter = new int[]{4,7,12,18,26,32,49,30,14,6};
		step = 0;
	}
	
	public void draw(Graphics g) {
		if( !aLive ){
//			tc.getExplode().remove(this);
			return;
		}
		if( step == diameter.length ){
			aLive = false;
			step = 0;
			tc.getExplodes().remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step++;
	}

	public boolean isaLive() {
		return aLive;
	}

	public void setaLive(boolean aLive) {
		this.aLive = aLive;
	}
	
}
