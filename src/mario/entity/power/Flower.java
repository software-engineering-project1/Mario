package mario.entity.power;

import java.awt.Graphics;

import mario.entity.Entity;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Flower extends Entity{

	public Flower(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.flower.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public void tick() {
				
	}

}
