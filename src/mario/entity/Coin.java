package mario.entity;

import java.awt.Graphics;

import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class coin extends Entity{



	
	public coin(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
	}


	public void render(Graphics g) {
		g.drawImage(Game.coin.getBufferedImage(), x, y, null );
	}

	
	public void tick() {
		
	}

}
