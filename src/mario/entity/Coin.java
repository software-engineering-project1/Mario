package mario.entity;

import java.awt.Graphics;

import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Coin extends Tile{

	public Coin(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	public void render(Graphics g) {
		g.drawImage(Game.coin.getBufferedImage(), x, y, width, height, null);
	}

	public void tick() {
		
	}

	
}
