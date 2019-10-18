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
	private List<Missile> missiles;
	
	public TankClient(){
		offScreenImage = null;
		myTank = new Tank(50,50, this);
		missiles = new ArrayList<>();
	}
	
	public void addMissile(Missile m){
		missiles.add(m);
	}
	
	public List<Missile> getMissiles(){
		return missiles;
	}
	@Override
	public void paint(Graphics g) {
		g.drawString("missile count = " + missiles.size(), 10, 50);
		myTank.draw(g);
		for( int i = 0; i < missiles.size(); i++ )
			missiles.get(i).draw(g);
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
