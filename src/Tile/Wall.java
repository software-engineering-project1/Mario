package Tile;

import java.awt.Color;
import java.awt.Graphics;

import marioTest.Handler;
import marioTest.Id;

public class Wall extends Tile{

	public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		
	}

	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		
	}

	
	public void tick() {
		
		
	}

}
