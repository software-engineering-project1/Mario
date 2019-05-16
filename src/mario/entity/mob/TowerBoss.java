package mario.entity.mob;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import mario.entity.BossState;
import mario.entity.Entity;
import mario.tile.Tile;
import marioTest.Handler;
import marioTest.Id;

public class TowerBoss extends Entity{

	public int jumpTime = 0;
	public boolean addJumpTime = false;
	private Random random;
	public TowerBoss(int x, int y, int width, int height, Id id, Handler handler, int healthpoint) {
		super(x, y, width, height, id, handler);
		this.healthpoint = healthpoint;
		bossState = BossState.IDLE;
		random = new Random();
	}

	
	public void render(Graphics g) {
		if (bossState==BossState.IDLE||bossState==BossState.SPINNING) g.setColor(Color.GRAY);
		else if (bossState==BossState.RECOVERING)  g.setColor(Color.RED);

		else g.setColor(Color.ORANGE);
		g.fillRect(x, y, width, height);
		
	}

	
	public void tick() {
		x+=velX;
		y+=velY;
		if (healthpoint<=0) die();
		phaseTime++;
		if ((phaseTime>=180&&(bossState==BossState.IDLE||bossState==BossState.RECOVERING))||(phaseTime>=600&&bossState!=BossState.SPINNING)) {
			chooseState();
		}
		if (bossState==BossState.IDLE||bossState==BossState.RECOVERING) {
			setVelX(0);
			setVelY(0);
		}
		if (bossState==BossState.JUMPING||bossState==BossState.RUNNING) {
			attackble = true;
		}else {
			attackble = false;
		}
		if (bossState != BossState.JUMPING) {
			addJumpTime =false;
			jumpTime = 0;
		}
		if (addJumpTime) {
			jumpTime++;
			if (jumpTime>=30) {
				addJumpTime = false;
				jumpTime = 0;
			}
			
			if (!jumping&&!falling) {
				jumping = true;
				gravity = 8.0;
			}
		}
		for(int i=0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			if (t.isSolid()&&!goingDownPipe) {
					
				if ( getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
					if(jumping ) {
						jumping = false;
						gravity = 0.8;//make it falling without delay
						falling = true;//This allows the player who hits the brick to come down naturally
						
					}
					 
				}
				if(getBoundsBottom().intersects(t.getBounds())) {//error:forget the (t.)getBounds() make the brick can't move up and down
					setVelY(0);
					if(falling) { 
						falling=false;
						addJumpTime = true;
					}
				} 
				if(getBoundsLeft().intersects(t.getBounds())){
					setVelX(0);
					if (bossState==BossState.RUNNING) setVelX(4);
					x = t.getX()+ t.width;//won't paste on the wall
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(0);
					if (bossState==BossState.RUNNING) setVelX(-4);

					x = t.getX()- t.width;
				}
		 
			}
	
				
		}
		for (int i = 0; i < handler.entity.size(); i++) {
			Entity e = handler.entity.get(i);
			if (e.getId()==id.player) {
				if (bossState==BossState.JUMPING) {
					if (jumping||falling) {
						if (getX()>=e.getX()-4&&getX()<=e.getX()+4) {
							setVelX(0);
						}else if (e.getX()<getX()) {
							setVelX(-3);	
							
						}else if(e.getX()>getX()){
							setVelX(3);
						}
					}else {
						setVelX(0);
					}
				}else if (bossState == BossState.SPINNING) {
					 if (e.getX()<getX()) {
						setVelX(-3);	
						
					}else if(e.getX()>getX()){
						setVelX(3);
					}
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

	}
	public void chooseState() {
		int nextPhase = random.nextInt(2);
		if (nextPhase==0) {
			bossState = BossState.RUNNING;
			int dir = random.nextInt(2);
			if (dir==0) {
				setVelX(-4);
			}else {			
				setVelY(4);
			}
		}else if (nextPhase==1) {
			bossState = BossState.JUMPING;
			jumping = true;
			gravity = 8.0;
		}
		phaseTime=0;
	}

}
