package pl.edu.utp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PanelFunctions extends JPanel{   
	
	static String nameStation = "...";
	
	
	
	public String getNameStation() {
		return nameStation;
	}

	public static void setNameStation(String nameStation) {
		PanelFunctions.nameStation = nameStation;
	}

	static int x = 0;
	static int y = 100;
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		Font font = new Font("Tahoma",Font.BOLD+Font.PLAIN,40);
		g2.setFont(font);
		g2.setColor(Color.blue);
		g2.drawString(nameStation, x, y);
		
		try {
			Thread.sleep(100);
		}catch(Exception e) {
			e.printStackTrace();
		}
		x+=10;
		if(x>this.getWidth()) {
			x=0;
		}
		repaint();
		
		
	}
	
	
	

	
	

}
             