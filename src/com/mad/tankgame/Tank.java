package com.mad.tankgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	private static final int XSPEED = 5;
	private static final int YSPEED = 5;
	private int x, y;
	private boolean bL, bU, bR, bD;
	private TankClient tc;
	
	public enum Direction{ L, LU, U, RU, R, RD, D, LD, STOP };
	private Direction direction;
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
		direction = Direction.STOP;
		bL = false;
		bU = false;
		bR = false;
		bD = false; 
		tc = null;
	}
	
	public Tank( int x, int y, TankClient tc ){
		this(x, y);
		this.tc = tc;
	}
	public void move(){
		switch(direction){
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
			case STOP:
				break;
			default:
				break;
			
		}
	}
	
	public Direction getDirection(){
		return direction;
	}
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	} 

	public void locateDirection(){
		if( bR && !bD && !bL && !bU ) direction = Direction.R;
		else if( bR && bD && !bL && !bU ) direction = Direction.RD;
		else if( bR && !bD && !bL && bU ) direction = Direction.RU;
		else if( !bR && bD && !bL && !bU ) direction = Direction.D;
		else if( !bR && bD && bL && !bU ) direction = Direction.LD;
		else if( !bR && !bD && bL && !bU ) direction = Direction.L;
		else if( !bR && !bD && bL && bU ) direction = Direction.LU;
		else if( !bR && !bD && !bL && bU ) direction = Direction.U;
		else if( !bR && !bD && !bL && !bU ) direction = Direction.STOP;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key){
			case KeyEvent.VK_SPACE:
				tc.myMissle = fire();
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
		}
		locateDirection();
		
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key){
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
		}
		locateDirection();
	}
	
	public Missile fire(){
		int mX = x + WIDTH / 2 - Missile.WIDTH / 2;
		int mY = y + HEIGHT / 2 - Missile.HEIGHT / 2;
		return new Missile(mX,mY,direction);
	}
}
