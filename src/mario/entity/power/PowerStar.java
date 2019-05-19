package mario.entity.power;

import java.awt.Graphics;
import java.util.Random;

import mario.entity.Entity;
import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class PowerStar  extends Entity{
	
	private Random random;
	

	public PowerStar(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		random = new Random();
		int dir = random.nextInt(2);
		switch(dir) {
		case 0:
			setVelX(-4);
			break;
		case 1:
			setVelX(4);
			break;
		}
		falling = true;
		gravity = 0.17;
	}

	
	public void render(Graphics g) {
		g.drawImage(Game.star.getBufferedImage(),getX(),getY(),getWidth(),getHeight(),null );
		
		
	}

	
	public void tick() {
		for(int i=0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			
			if(t.isSolid()){
				if(getBoundsBottom().intersects(t.getBounds())) {
					jumping = true;
					gravity = 0.1;
				}
				if(getBoundsLeft().intersects(t.getBounds())) setVelX(4);
				if(getBoundsRight().intersects(t.getBounds())) setVelX(-4);
			}
		}
		if(jumping) {
			gravity-=0.17;
			setVelY((int) -gravity);// if gravity is 5.0 then the VelY is -5.0
			if(gravity<= 0.5) {
				jumping = false;
				falling = true;
			}
		}
		if ( falling ) {
			gravity+=0.17;
			setVelY((int)gravity);
		}
		
	}
	

}
