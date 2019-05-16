package mario.entity;

import java.awt.Graphics;

import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Coin extends Entity{

	public Coin(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height,  id, handler);
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.coin.getBufferedImage(),x,y,width,height,null);
		
	}

	@Override
	public void tick() {
		
		
	}

}
