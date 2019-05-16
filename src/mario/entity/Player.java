package mario.entity;

import java.awt.Graphics;
import java.util.Random;

import mario.entity.Entity;
import mario.state.BossState;
import mario.state.KoopaState;
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
	
	private  int pixelsTravelled = 0;
	
	private Random random;
	
	public Player(int x,int y,int width,int height,Id id ,Handler handler) {
		super(x, y, width, height, id, handler);
		
		state = PlayerState.SMALL;
		
		random = new Random();
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
//		if (goingDownPipe) {
//			pixelsTravelled+=velX;
//		}
	//if (x<=0) x = 0;
////		if (y<= 0)y = 0;
//		if (x+width>=2160) x =2160-width;
	//	if (y+height>=900) y=900-height;
	//	if(velX!=0 ) animate= true;
	//	else animate = false;
		for(int i=0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			if (t.isSolid()&&goingDownPipe==false) {
				if ( getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
					if(jumping&&!goingDownPipe) {
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
//						System.out.println("here");
					}
			
				if(getBoundsLeft().intersects(t.getBounds())){
					setVelX(0);
					x = t.getX()+ t.width;
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(0);
					x = t.getX()- t.width;
				}
				//actually, put your if statement above everything
				if(getBounds().intersects(t.getBounds())) {
					if(t.getId()==Id.flag) Game.switchLevel();
				}
		
			}
				
				
		}
			for(int i=0;i<handler.entity.size();i++) {
				Entity e = handler.entity.get(i);//scan the entity licked list ,whatever the entity it scans ,it will create an entity object
				
				if (e.getId()==Id.mushroom) {
					switch (e.getType()) {
					case 0:
						if(getBounds().intersects(e.getBounds())) {
							int tpX = getX();
							int tpY = getY();
							width+=(width/3);
							height+=(height/3);
							setX(tpX-width);
							setY(tpY-height);
							if (state == PlayerState.SMALL) state = PlayerState.BIG;
							e.die();
						}
						break;

					case 1:
						if(getBounds().intersects(e.getBounds())) {
							Game.lives++;
							e.die();
						}
						break;
					}
					
				}else if (e.getId()== Id.goomba||e.getId()==Id.towerBoss) {
					if (getBoundsLeft().intersects(e.getBoundsTop())) {
						if(e.getId()!=Id.towerBoss)e.die();
						else if(e.attackable) {
							e.hp--;
							e.falling=true;
							e.gravity=3.0;
							e.bossState = BossState.RECOVERING;
							e.attackable=false;
							e.phaseTime=0;
							
							
							jumping=true;
							falling=false;
							gravity=3.5;
						}
					}else if (getBounds().intersects(e.getBounds())) {
						if (state==PlayerState.BIG) {
							state = PlayerState.SMALL;
							width/=3;
							height/=3;
							x+=width;
							y+=height;
						}else if (state==PlayerState.SMALL) {
							die();	
						}
						
					}
				}else if(e.getId()==Id.coin) {
					if(getBounds().intersects(e.getBounds())&&e.getId()==Id.coin) {
						Game.coins++;
						e.die();
					}
				}else if(e.getId()==Id.koopa) {
					if(e.koopaState==KoopaState.WALKING) {
						if(getBoundsBottom().intersects(e.getBoundsTop())) {
							e.koopaState=KoopaState.SHELL;
							
							jumping=true;
							falling=false;
							gravity=3.5;
						}else if(getBounds().intersects(e.getBounds())) die();
					}else if(e.koopaState==KoopaState.SHELL) {
						if(getBoundsBottom().intersects(e.getBoundsTop())) {
							e.koopaState=KoopaState.SPINNING;
							
							int dir = random.nextInt(2);
							
							switch(dir) {
							case 0:
								e.setVelX(-10);
								facing = 0;
								break;
							case 1:
								e.setVelX(10);
								facing = 1;

								break;
							}
							
							jumping=true;
							falling=false;
							gravity=3.5;
						}
						
						if(getBoundsLeft().intersects(e.getBoundsRight())) {
							e.setVelX(-10);
							e.koopaState=KoopaState.SPINNING;
						}
						if(getBoundsRight().intersects(e.getBoundsLeft())) {
							e.setVelX(10);
							e.koopaState=KoopaState.SPINNING;
						}
						
					}else if(e.koopaState==KoopaState.SPINNING) {
						if(getBoundsBottom().intersects(e.getBoundsTop())) {
							e.koopaState=KoopaState.SHELL;
							
							jumping=true;
							falling=false;
							gravity=3.5;
						}else if(getBounds().intersects(e.getBounds())) die();
					}
				}
			}

		if(jumping&&!goingDownPipe) {
			gravity-=0.17;
			setVelY((int) -gravity);// if gravity is 5.0 then the VelY is -5.0
			if(gravity<= 0.5) {
				jumping = false;
				falling = true;
			}
		}
		if ( falling &&!goingDownPipe) {
			gravity+=0.17;
			setVelY((int)gravity);
		}
//		if(animate) {
		if(velX!=0) {
			frameDelay++;
			if(frameDelay>=10) {
				frame++;
				if(frame>3) {
					frame=0;
					
				}
				frameDelay=0;
			}
		}
		if (goingDownPipe) {
			for(int i = 0;i<Game.handler.tile.size();i++) {
				Tile t = Game.handler.tile.get(i);
				if (t.getId()==Id.pipe) {
					if(getBounds().intersects(t.getBounds())) {
						switch (t.facing) {
						case 0:
							setVelY(5);//if face is 0 the velY will be -5
							setVelX(0);
							pixelsTravelled-=velY;
							break;
						case 2:
							setVelY(-5);
							setVelX(0);
//							setY(273);
//							setX(273);
//							System.out.println(x);
//							System.out.println(y);
							pixelsTravelled+=velY;
							break;
						}
						if (pixelsTravelled>=t.height*2+height) {
							goingDownPipe = false;
							pixelsTravelled = 0;
						}
					}
					}else if(t.getId()==Id.wall) {
						if(getBoundsBottom().intersects(t.getBounds())) {
							setVelY(0);
							if(falling) falling=false;
							goingDownPipe=false;
						}
//						if(getBoundsLeft().intersects(t.getBounds())){
//							setVelX(0);
//							x = t.getX()+ t.width;
//							if(falling) falling=false;
//							goingDownPipe=false;
//						}
//						if(getBoundsRight().intersects(t.getBounds())){
//							setVelX(0);
//							x = t.getX()- t.width;
//							if(falling) falling=false;
//							goingDownPipe=false;
//						}
					}
			
				}
			
			}
		

	}
	
}