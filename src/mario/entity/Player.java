package mario.entity;

import java.awt.Color;


import java.awt.Graphics;

import java.awt.Graphics;

import mario.entity.Entity;
import mario.state.PlayerState;
import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Player extends Entity {

	private int frame = 0;
	private int frameDelay = 0;
	private boolean animate = false;
	
	private PlayerState state;
	private int pixelsTravelled = 0;
	
	public Player(int x,int y,int width,int height,Id id ,Handler handler) {
		super(x, y, width, height, id, handler);
		
		state = PlayerState.SMALL;
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
	//	if (y+height>=900) y=900-height;
	//	if(velX!=0 ) animate= true;
	//	else animate = false;
		for(int i=0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			if(!t.solid && !goingDownPipe) break;					
				if ( getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
					if(jumping && !goingDownPipe) {
						jumping = false;
						gravity = 0.8;//make it falling without delay
						falling = true;//This allows the player who hits the brick to come down naturally
						
					}
					if (t.getId()== Id.powerUp) {
						if (getBoundsTop().intersects(t.getBounds())) {
							t.activated=true;
						}
					}
				}
				if(getBoundsBottom().intersects(t.getBounds())) {//error:forget the (t.)getBounds() make the brick can't move up and down
					setVelY(0);
					if(falling) falling=false;
				}else if(!falling&&!jumping) {
						gravity = 0.8;
						falling = true;//this make the player can fall from the wall 
					}
			
				if(getBoundsLeft().intersects(t.getBounds())){
					setVelX(0);
					x = t.getX()+ t.width;//won't paste on the wall
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(0);
					x = t.getX()- t.width;
				}
//				if (getBounds().intersects(t.getBounds())&&t.getId()==Id.coin) {
//					Game.coins++;
//					t.die();
//					
				
					
//					t.die();
//				}
			}
	
				
		
			for(int i=0;i<handler.entity.size();i++) {
				Entity e = handler.entity.get(i);//scan the entity licked list ,whatever the entity it scans ,it will create an entity object
				if (e.getId()==Id.mushroom) {
					if(getBounds().intersects(e.getBounds())) {
						int tpX = getX();
						int tpY = getY();
						width += (width/3);
						height += (height/3);
						setX(tpX-width);
						setY(tpY-height);
						
						if(state == PlayerState.SMALL) state = PlayerState.BIG;
						e.die();//let mario grow up but the mushroom won't die
						
					}
				}else if (e.getId()== Id.goomba) {
					if (getBoundsBottom().intersects(e.getBoundsTop())) {
						
						e.die();
					}else if (getBounds().intersects(e.getBounds())) {
						if(state == PlayerState.BIG) {
							state = PlayerState.SMALL;
							width /= 3;
							height /= 3;
							x += width;
							y += height;
						}
						else if(state == PlayerState.SMALL) {
							state = PlayerState.BIG;
							die();
						}	
					}
				}else if(e.id == Id.coin){
					if (getBounds().intersects(e.getBounds())&&e.getId()==Id.coin) {
						Game.coins++;
						e.die();
//						
					}
				}
			}

		if(jumping && !goingDownPipe) {
			gravity-=0.17;
			setVelY((int) -gravity);// if gravity is 5.0 then the VelY is -5.0
			if(gravity<= 0.5) {
				jumping = false;
				falling = true;
			}
		}
		if ( falling && !goingDownPipe) {
			gravity+=0.17;
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
		if(goingDownPipe) {
			for(int i=0; i<Game.handler.tile.size(); i++) {
				Tile t = Game.handler.tile.get(i);
				if(t.getId() == Id.pipe) {
					if(getBoundsBottom().intersects(t.getBounds())) {
						switch(t.facing) {
						case 0:
							setVelY(-5);
							setVelX(0);
							pixelsTravelled += -velY;
							break;
						case 2:
							setVelY(5);
							setVelX(0);
							pixelsTravelled += velY;
							break;
						}
						if(pixelsTravelled>t.height+height) {
							goingDownPipe = false;
							pixelsTravelled = 0;
							
						}
					}
				}
			}
			
		}
		

	}
	
}