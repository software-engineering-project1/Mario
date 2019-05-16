package mariogfx.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import marioTest.Game;

public class Button {

	public int x,y;
	public int width, height;
	public String label;
	public Button(int x, int y, int width, int height, String label) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.label = label;
	}
	public void render(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Centry Gathic",Font.BOLD,50));	
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		FontMetrics fm = g.getFontMetrics();
		int StringX = (getWidth()- fm.stringWidth(getLabel()))/2;
		int StringY = (fm.getAscent() + (getHeight()-(fm.getAscent()+ fm.getAscent() + fm.getDescent()))/2);

		g.drawString(getLabel(), getX() + StringX, getY() + StringY);
	
	
	}
	public void triggerEvent() {
		//whenever the button is clicked the all the code here will be excuted
		if (getLabel().toLowerCase().contains("start")) { 
			Game.playing = true;
		
		}else if (getLabel().toLowerCase().contains("exit")) { 
			System.exit(0);
		
		}
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
