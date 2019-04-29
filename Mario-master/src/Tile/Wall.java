package Tile;

import java.awt.Color;
import java.awt.Graphics;

import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Wall extends Tile{

	public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		
	}

	
	public void render(Graphics g) {
		g.drawImage(Game.player.getBufferedImage(), x, y, width, height, null);

		
	}

	
	public void tick() {
		
		
	}

}
