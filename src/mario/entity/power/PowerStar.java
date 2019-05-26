package mario.entity.power;

import java.awt.Graphics;
import java.util.Random;

import mario.entity.Entity;
import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class PowerStar extends Entity {

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
		falling=true;
		gravity=0.17;
		}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.star.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		for(int i=0; i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			
			if(t.isSolid()) {
				if(getBoundsBottom().intersects(t.getBounds())) {
					jumping=true;
					gravity=8.0;
					if(falling) falling=false;
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
		if (falling) {
			gravity+=0.17;
			setVelY((int)gravity);
		}
		
		//
/*		for(int i = 0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			
				if(getBoundsBottom().intersects(t.getBounds())) {//error:forget the (t.)getBounds() make the brick can't move up and down
					setVelY(0);
					if(falling) falling=false;
				}else if(!falling){
					falling = true;
					gravity = 0.8;
				}
					
				if(getBoundsLeft().intersects(t.getBounds())){
					setVelX(2);
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(-2);
				}
			}*/

		
	}

}
