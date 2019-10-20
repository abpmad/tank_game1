package com.mad.tankgame;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.List;


public class Tank {
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	private static final int XSPEED = 5;
	private static final int YSPEED = 5;
	private int x, y, oldX, oldY;
	private boolean good, aLive;
	
	private boolean bL, bU, bR, bD;
	private TankClient tc;
	
	public enum Direction{ L, LU, U, RU, R, RD, D, LD, STOP };
	private Direction direction, gunDir;
	private static Random rand;
	private int step;
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
		aLive = true;
		direction = Direction.STOP;
		gunDir = Direction.D;
		bL = false;
		bU = false;
		bR = false;
		bD = false; 
		tc = null;
		rand = new Random();
		step = rand.nextInt(12) + 3;
	}
	
	public Tank( int x, int y, boolean good, Direction dir, TankClient tc ){
		this(x, y, good);
		this.direction = dir;
		this.tc = tc;
	}
	
	public boolean isAlive() {
		return aLive;
	}

	public void setAlive(boolean aLive) {
		this.aLive = aLive;
	}
	
	public void move(){
		oldX = x;
		oldY = y;
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
		if( direction != Direction.STOP)
			gunDir = direction;
		if( x < 0 ) x = 0;
		if( y < 20 ) y = 20;
		if( x + WIDTH > TankClient.GAME_WIDTH ) x = TankClient.GAME_WIDTH - WIDTH;
		if( y + HEIGHT > TankClient.GAME_HEIGHT ) y = TankClient.GAME_HEIGHT - HEIGHT;
		
		if( !good ){
			Direction[] dirs = Direction.values();
			if(step == 0 ){
				step = rand.nextInt(12) + 3;
				direction = dirs[rand.nextInt(dirs.length)];
			}
			if( rand.nextInt(40) > 37 ) fire();
			step--;
		}
	}
	
	public void draw(Graphics g) {
		if(!aLive){
			if(!good)
				tc.getEnemyTanks().remove(this);
			return;
		}
		Color c = g.getColor();
		if( good ) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		switch(gunDir){
			case L:
				g.drawLine( x + WIDTH/2, y+HEIGHT/2, x, y + HEIGHT/2);
				break;
			case LU:
				g.drawLine( x + WIDTH/2, y+HEIGHT/2, x, y );
				break;
			case U:
				g.drawLine( x + WIDTH/2, y+HEIGHT/2, x + WIDTH/2, y);
				break;
			case RU:
				g.drawLine( x + WIDTH/2, y+HEIGHT/2, x + WIDTH, y );
				break;
			case R:
				g.drawLine( x + WIDTH/2, y+HEIGHT/2, x + WIDTH, y + HEIGHT/2);
				break;
			case RD:
				g.drawLine( x + WIDTH/2, y+HEIGHT/2, x + WIDTH, y + HEIGHT);
				break;
			case D:
				g.drawLine( x + WIDTH/2, y+HEIGHT/2, x + WIDTH/2, y + HEIGHT);
				break;
			case LD:
				g.drawLine( x + WIDTH/2, y+HEIGHT/2, x, y + HEIGHT);
				break;
			default:
				break;
			
		}
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
			case KeyEvent.VK_SPACE:
				fire();
				break;
			case KeyEvent.VK_X:
				superFire();
				break;
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
	
	public void fire(){
		if( !aLive ) return;
		int mX = x + WIDTH / 2 - Missile.WIDTH / 2;
		int mY = y + HEIGHT / 2 - Missile.HEIGHT / 2;
		tc.addMissile(new Missile(mX,mY,gunDir, good, tc));
	}
	
	public void fire(Direction d){
		if( !aLive ) return;
		int mX = x + WIDTH / 2 - Missile.WIDTH / 2;
		int mY = y + HEIGHT / 2 - Missile.HEIGHT / 2;
		tc.addMissile(new Missile(mX,mY,d, good, tc));
	}
	
	public void superFire(){
		Direction[] dirs = Direction.values();
		
		for( int i = 0; i < dirs.length - 1; i++ ){
			fire(dirs[i]);
		}
	}
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public Rectangle getRectangle(){
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	public void stay(){
		x = oldX;
		y = oldY;
	}
	public boolean colldieWithWall(Wall wall ){
		if( this.isAlive() && this.getRectangle().intersects(wall.getRectangle())){
			stay();
			return true;
		}
		return false;
	}
	
	public boolean colldieWithTanks( List<Tank> tanks ){
		for( int i = 0; i < tanks.size(); i++ ){
			Tank t = tanks.get(i);
			if( this != t && this.isAlive() && t.isAlive() && this.getRectangle().intersects(t.getRectangle())){
				this.stay();
				t.stay();
				return true;
			}
		}
		return false;
	}
}
