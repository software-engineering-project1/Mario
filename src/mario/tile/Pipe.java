package mario.tile;

import java.awt.Color;
import java.awt.Graphics;

import mario.entity.mob.Plant;
import marioTest.Handler;
import marioTest.Id;

public class Pipe extends Tile{

	public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler,int facing, boolean plant) {
		super(x, y, width, height, solid, id, handler);
		this.facing = facing;
		if(plant) handler.addEntity(new Plant(getX(), getY()-64, getWidth(), 64, Id.plant, handler));
	}


	public void render(Graphics g) {
		g.setColor(new Color(128,128,128));
		g.fillRect(x, y, width, height);
	}


	public void tick() {
	
		
	}

}
