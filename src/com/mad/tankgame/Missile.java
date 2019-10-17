package com.mad.tankgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.mad.tankgame.Tank.Direction;


public class Missile {
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	private int x, y;
	private Tank.Direction dir;
	private boolean bL, bU, bR, bD;
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		bL = false;
		bU = false;
		bR = false;
		bD = false; 
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
	}
	public void keyPressed(KeyEvent e, Tank.Direction dir) {
		int key = e.getKeyCode();
		switch (key){
			case KeyEvent.VK_CONTROL:
				this.dir = dir;
				break;
//			case KeyEvent.VK_DOWN:
//				bD = true;
//				break;
//			case KeyEvent.VK_LEFT:
//				bL = true;
//				break;
//			case KeyEvent.VK_UP:
//				bU = true;
//				break;
			default:
				break;
		}
//		locateDirection();
	}
//	public void locateDirection(){
//		if( bR && !bD && !bL && !bU ) dir = Direction.R;
//		else if( bR && bD && !bL && !bU ) dir = Direction.RD;
//		else if( bR && !bD && !bL && bU ) dir = Direction.RU;
//		else if( !bR && bD && !bL && !bU ) dir = Direction.D;
//		else if( !bR && bD && bL && !bU ) dir = Direction.LD;
//		else if( !bR && !bD && bL && !bU ) dir = Direction.L;
//		else if( !bR && !bD && bL && bU ) dir = Direction.LU;
//		else if( !bR && !bD && !bL && bU ) dir = Direction.U;
//		else if( !bR && !bD && !bL && !bU ) dir = Direction.STOP;
//	}
}
