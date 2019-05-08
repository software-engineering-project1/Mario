package mario.entity.mob;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics;

import mario.entity.Entity;
import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Player extends Entity {

//	private int frame = 0;
//	private int frameDelay = 0;
//	private boolean animate = false;
	
	public Player(int x,int y,int width,int height,Id id ,Handler handler) {
		super(x, y, width, height, id, handler);
	}


	public void render(Graphics g) {
		if(facing==0) {
			g.drawImage(Game.player[frame+5].getBufferedImage(), x, y,width,height, null );

		}else if(facing==1){
			g.drawImage(Game.player[frame].getBufferedImage(), x, y,width,height, null );
		}
	}

	public void tick() {
		x+=velX;
		y+=velY;
	//if (x<=0) x = 0;
////		if (y<= 0)y = 0;
//		if (x+width>=2160) x =2160-width;
//		if (y+height>=900) y=900-height;
//		if(velX!=0 ) animate= true;
//		else animate = false;
		
		for(Tile t: handler.tile) {
			if (!t.solid) break;
			if(t.getId() == Id.wall) {
				if ( getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
					if(jumping) {
						jumping = false;
						gravity = 0.8;//make it falling without delay
						falling = true;//This allows the player who hits the brick to come down naturally
						
					}
					if(t.getId() == Id.powerUp) {
						if(getBoundsTop().intersects(t.getBounds())) t.activated = true;
					}
				}
				if(getBoundsBottom().intersects(t.getBounds())) {//error:forget the (t.)getBounds() make the brick can't move up and down
					setVelY(0);
					if(falling) falling=false;
				}else {
					if(!falling&&!jumping) {
						gravity = 0.8;
						falling = true;//this make the player can fall from the wall 
					}
				}
				if(getBoundsLeft().intersects(t.getBounds())){
					setVelX(0);
					//x = t.getX()+ t.width;
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(0);
					//x = t.getX()- t.width;
				}
			}
		}
		for(int i=0;i<handler.entity.size();i++) {
			Entity e = handler.entity.get(i);//scan the entity licked list ,whatever the entity it scans ,it will create an entity object
				
			if (e.getId()==Id.mushroom) {
				if(getBounds().intersects(e.getBounds())) {
					int tpX = getX();
					int tpY = getY();
					width*=1.5;
					height*=1.5;
					setX(tpX-width);
					setY(tpY-height);
					e.die();
				}
			}else if (e.getId()== Id.goomba) {
				if(getBounds().intersects(getBoundsTop())) {
					e.die();
				}
				else if (getBounds().intersects(e.getBoundsTop())) {
						
					e.die();
				}
			}
		}
		

		if(jumping) {
			gravity-=0.1;
			
			setVelY((int) -gravity);// if gravity is 5.0 then the VelY is -5.0
			if(gravity<= 0.8) {
				jumping = false;
				falling = true;
			}
		}
		if ( falling ) {
			gravity+=0.1;
			setVelY((int)gravity);
		}
//		if(animate) {
		if(velX!=0) {
			frameDelay++;
			if(frameDelay>=10) {
				frame++;
				if(frame>=3) {
					frame=0;
					
				}
				frameDelay=0;
			}
		}

	}
	
}