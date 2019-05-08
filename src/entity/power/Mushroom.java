package mario.entity.power;

import java.awt.Graphics;
import java.util.Random;

import mario.entity.Entity;
import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Mushroom extends Entity{

	private Random random = new Random();
	
	
	public Mushroom(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height,  id, handler);
		
		int dir = random.nextInt(2);
		
		switch(dir) {
		case 0:
			setVelX(-2);
			break;
		case 1:
			setVelX(2);
			break;
		}
	}

	@Override
	public  void render(Graphics g) {
		g.drawImage(Game.mushroom.getBufferedImage(), x, y, width, height, null);
	}
	public  void tick() {
		x+=velX;
		y+=velY;

		for(int i=0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			if(t.solid) {
				
				if(getBoundsBottom().intersects(t.getBounds())) {//error:forget the (t.)getBounds() make the brick can't move up and down
					setVelY(0);
					if(falling) falling=false;
				}else if(!falling){
						gravity = 0.8;
						falling = true;//this make the player can fall from the wall 
					
				}
				
				if(getBoundsLeft().intersects(t.getBounds())){
					setVelX(1);
					//x = t.getX()+t.width;
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(-1);
					//x = t.getX()-t.width;
				}
			}
		}
		if ( falling ) {
			gravity+=0.1;
			setVelY((int)gravity);
		}

	}	
}
