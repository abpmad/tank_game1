package com.mad.tankgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.mad.tankgame.Tank.Direction;


public class Missile {
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private int x, y;
	private boolean isLive;
	private TankClient tc;
	private Tank.Direction dir;
//	private boolean bL, bU, bR, bD;
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.isLive = true;
		tc = null;
		this.dir = dir;
//		bL = false;
//		bU = false;
//		bR = false;
//		bD = false; 
	}
	public Missile(int x, int y, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.tc = tc;
	}
	public void draw(Graphics g) {
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
		
		if( x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT ){
			isLive = false;
			tc.getMissiles().remove(this);
		}
	}
}
