package mario.entity.mob;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import mario.entity.Entity;
import mario.state.KoopaState;
import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Koopa extends Entity {
	
	private Random random;
	
	private int shellCount;
	private int frame = 0;
	private int frameDelay=0;

	public Koopa(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		
		random = new Random();
		
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
		koopaState = KoopaState.WALKING;
	}

	@Override
	public void render(Graphics g) {
		if(koopaState==KoopaState.WALKING) {
			if(facing==0) {//0--left  1--right
				g.drawImage(Game.koopa[frame+2].getBufferedImage(), x, y,width,height, null );
			
			}else if(facing==1){
				g.drawImage(Game.koopa[frame].getBufferedImage(), x, y,width,height, null );
			}

		}else {
			if(facing==0) {
				g.drawImage(Game.koopa[frame+5].getBufferedImage(), x, y,width,height, null );
			
			}else if(facing==1){
				g.drawImage(Game.koopa[frame+7].getBufferedImage(), x, y,width,height, null );
			}
		}
				
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		frameDelay++;
		
		if(frameDelay>=8) {
			frame++;
			if(frame>1) {
				frame=0;
				
			}
			frameDelay=0;
		}
		
		if(koopaState==KoopaState.SHELL) {
			setVelX(0);
			
			shellCount++;
			
			if(shellCount>=300) {
				shellCount=0;
				
				koopaState=KoopaState.WALKING;
			}
			if(koopaState==KoopaState.WALKING||koopaState==KoopaState.SPINNING) {
				shellCount=0;
				
				if(velX==0) {
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
			}
		}
	
		
		for(int i = 0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			
				if(getBoundsBottom().intersects(t.getBounds())) {//error:forget the (t.)getBounds() make the brick can't move up and down
					setVelY(0);
					if(falling) falling=false;
				}else if(!falling){
					falling = true;
					gravity = 0.8;
				}
					
				if(getBoundsLeft().intersects(t.getBounds())){
					if(koopaState==KoopaState.SPINNING) setVelX(10);
					else setVelX(2);
					facing = 1;
				}
				if(getBoundsRight().intersects(t.getBounds())){
					if(koopaState==KoopaState.SPINNING) setVelX(-10);
					else setVelX(-2);
					facing = 0;
				}
			}

		if ( falling ) {
			gravity+=0.1;
			setVelY((int)gravity);
		}
				
	}

}
