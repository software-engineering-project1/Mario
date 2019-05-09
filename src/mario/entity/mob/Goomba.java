package mario.entity.mob;

import java.awt.Graphics;

import java.util.Random;

import mario.entity.Entity;
import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Goomba extends Entity{

	private Random random = new Random();
	public int frameDelay = 0;

	private int frame = 0;
	public Goomba(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		
		int dir = random.nextInt(2);
		switch(dir) {
		case 0:
			setVelX(-2);
			facing = 0;
			break;
		case 1:
			setVelX(2);
			facing = 1;

			break;
		}
	}

	public  void render(Graphics g) {
		if(facing==0) {
			g.drawImage(Game.goomba[frame+5].getBufferedImage(), x, y,width,height, null );

		}else if(facing==1){
			g.drawImage(Game.goomba[frame].getBufferedImage(), x, y,width,height, null );
		}
	}
	public  void tick() {
			x+=velX;
			y+=velY;
			
			for(int i = 0;i<handler.tile.size();i++) {
				Tile t = handler.tile.get(i);
				if (t.isSolid()) {
					if(getBoundsBottom().intersects(t.getBounds())) {//error:forget the (t.)getBounds() make the brick can't move up and down
						setVelY(0);
						if(falling) falling=false;
					}else if(!falling){
						falling = true;
						gravity = 0.8;
					}
						
					if(getBoundsLeft().intersects(t.getBounds())){
						setVelX(2);
						facing = 1;
					}
					if(getBoundsRight().intersects(t.getBounds())){
						setVelX(-2);
						facing = 0;
					}
				}
			}
			if ( falling ) {
				gravity+=0.1;
				setVelY((int)gravity);
			}
			
			if(velX!=0) {
				frameDelay++;
				if(frameDelay>=10) {
					frame++;
					if(frame>=5) {
						frame=0;
						
					}
					frameDelay=0;
				}
			}

		}
	}


