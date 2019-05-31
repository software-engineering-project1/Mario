package mario.tile;

import java.awt.Color;
import java.awt.Graphics;

import mario.entity.mob.Plant;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Pipe extends Tile{

	public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler,int facing, boolean plant) {
		super(x, y, width, height, solid, id, handler);
		this.facing = facing;
		if(plant) handler.addEntity(new Plant(getX(), getY()-64, getWidth(), 64, Id.plant, handler));
	}


	public void render(Graphics g) {
		g.drawImage(Game.pipe[0].getBufferedImage(), x, y, width, 64, null);
		
		g.drawImage(Game.pipe[1].getBufferedImage(), x, y+64, width, 64, null);
		g.drawImage(Game.pipe[1].getBufferedImage(), x, y+128, width, 64, null);
		g.drawImage(Game.pipe[1].getBufferedImage(), x, y+192, width, 64, null);
		g.drawImage(Game.pipe[1].getBufferedImage(), x, y+256, width, 64, null);

		g.drawImage(Game.pipe[1].getBufferedImage(), x, y+height-64, width, 64,null);
		
	}


	public void tick() {
	
		
	}

}
