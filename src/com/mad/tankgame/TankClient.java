package com.mad.tankgame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame{
	int x = 50, y = 50;
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		
		y += 5;
		x += 5;
	}
	
	public void launchFrame(){
		this.setLocation(400, 300);
		this.setSize(800 , 600);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		setVisible(true);
		new Thread(new TankThread()).start();
	}
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
		
	}
	
	private class TankThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		}
		
	}

}
