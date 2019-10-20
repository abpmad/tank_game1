package com.mad.tankgame;

import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Missile {
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private int x, y;
	private boolean aLive;
	private TankClient tc;
	private Tank.Direction dir;
	private boolean good;
	
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.aLive = true;
		tc = null;
		this.dir = dir;
	}
	public Missile(int x, int y, Tank.Direction dir, boolean good, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}
	
	public boolean isAlive() {
		return aLive;
	}

	public void setAlive(boolean aLive) {
		this.aLive = aLive;
	}
	public void draw(Graphics g) {
		if(!aLive){
			tc.getMissiles().remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, 10, 10);
		g.setColor(c);
		move();
	} 
	
	public void move(){
		switch(dir){
			case L:
				x -= XSPEED;
				break;
			case LU:
				x -= XSPEED;
				y -= YSPEED;
				break;
			case U:
				y -= YSPEED;
				break;
			case RU:
				y -= YSPEED;
				x += XSPEED;
				break;
			case R:
				x += XSPEED;
				break;
			case RD:
				x += XSPEED;
				y += YSPEED;
				break;
			case D:
				y += YSPEED;
				break;
			case LD:
				x -= XSPEED;
				y += YSPEED;
				break;
		default:
			break;
		}
		
		if( x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT )
			aLive = false;
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		if( this.isAlive() && t.isAlive() && 
		   this.getRectangle().intersects(t.getRectangle()) && this.good != t.isGood()){
			t.setAlive(false);
			this.setAlive(false);
			tc.addExplode(new Explode(x, y, tc));
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for( int i = 0; i < tanks.size(); i++){
			if( hitTank(tanks.get(i)))return true;
		}
		return false;
	}
}
