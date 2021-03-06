package com.mad.tankgame;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private Image offScreenImage;
	private Tank myTank;
	private List<Tank> enemyTanks;
	private List<Missile> missiles;
	private List<Explode> explodes;
	private Wall wall;
	private Blood blood;
	
	public TankClient(){
		offScreenImage = null;
		myTank = new Tank(50,50,true, Tank.Direction.STOP, this);
		enemyTanks = new ArrayList<>();
		missiles = new ArrayList<>();
		explodes = new ArrayList<>();
		
		for( int i = 0; i < 10; i++ ){
			enemyTanks.add(new Tank(50,100 + 40 * i, false, Tank.Direction.STOP, this));
		}
		wall = new Wall(300, 200, 20, 150, this);
		blood = new Blood();
	}
	
	public void addMissile(Missile m){
		missiles.add(m);
	}
	
	public List<Missile> getMissiles(){
		return missiles;
	}
	
	public void addExplode(Explode e){
		explodes.add(e);
	}
	
	public List<Explode> getExplodes(){
		return explodes;
	}
	
	public List<Tank> getEnemyTanks(){
		return enemyTanks;
	}
	@Override
	public void paint(Graphics g) {
		g.drawString("missiles count = " + missiles.size(), 10, 50);
		g.drawString("explodes count = " + explodes.size(), 10, 70);
		g.drawString("enemyTanks count = " + enemyTanks.size(), 10, 90);
		g.drawString("Life = " + myTank.getLife(), 10, 120);
		
		if( enemyTanks.isEmpty() ){
			for( int i = 0; i < 5; i++ ){
				enemyTanks.add(new Tank(50,100 + 40 * i, false, Tank.Direction.STOP, this));
			}
		}
		
		for( int i = 0; i < missiles.size(); i++ ){
			Missile curMissile = missiles.get(i);
			curMissile.hitTanks(enemyTanks);
			curMissile.hitTank(myTank);
			curMissile.hitWall(wall);
			curMissile.draw(g);
		}
		
		for( int i = 0; i < explodes.size(); i++ ){
			Explode curExplode = explodes.get(i);
			curExplode.draw(g);
		}
		
		for( int i = 0; i < enemyTanks.size(); i++) {
			Tank enemyTank = enemyTanks.get(i);
			enemyTank.draw(g);
			enemyTank.colldieWithWall(wall);
			enemyTank.colldieWithTanks(enemyTanks);
		}
		wall.draw(g);
		myTank.draw(g);
		blood.draw(g);
		myTank.gainBlood(blood);
		
	}
	
	@Override
	public void update(Graphics g) {
		if( offScreenImage == null ){
			offScreenImage = createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}

	public void launchFrame(){
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH , GAME_HEIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		setVisible(true);
		new Thread(new TankThread()).start();
		addKeyListener(new TankKeyListener());
	}
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
		
	}
	
	private class TankThread implements Runnable{
		@Override
		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		}
		
	}
	private class TankKeyListener extends KeyAdapter{
		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		
	}
	
}
