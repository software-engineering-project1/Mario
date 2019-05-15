package mario.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import mario.entity.Entity;
import mario.state.BossState;
import mario.tile.Tile;
import marioTest.Handler;
import marioTest.Id;

public class TowerBoss extends Entity{

	public int jumpTime = 0;
	
	public boolean addJumpTime = false;
	
	private Random random;
	
	public TowerBoss(int x, int y, int width, int height, Id id, Handler handler, int hp) {
		super(x, y, width, height, id, handler);
		this.hp = hp;
		
		bossState = BossState.IDLE;
		random = new Random();
	}

	@Override
	public void render(Graphics g) {
		if(bossState==BossState.IDLE || bossState == BossState.SPINNING) g.setColor(Color.GRAY);
		else if(bossState == BossState.RECOVERING) g.setColor(Color.red);
		else g.setColor(Color.ORANGE);
		
		g.fillRect(x, y, width, height);
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		
		if(hp<=0) die();
		phaseTime++;
		for(int i=0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			if(!t.solid && !goingDownPipe) {					
				if ( getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
					if(jumping) {
						jumping = false;
						gravity = 0.8;//make it falling without delay
						falling = true;//This allows the player who hits the brick to come down naturally
						
					}
				}
				if(getBoundsBottom().intersects(t.getBounds())) {//error:forget the (t.)getBounds() make the brick can't move up and down
					setVelY(0);
					if(falling) falling=false;
				}
				if(getBoundsLeft().intersects(t.getBounds())){
					setVelX(0);
					if(bossState==BossState.RUNNING) setVelX(4);
					x = t.getX()+ t.width;//won't paste on the wall
				}
				if(getBoundsRight().intersects(t.getBounds())){
					setVelX(0);
					if(bossState==BossState.RUNNING) setVelX(-4);
					x = t.getX()- t.width;
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
	}
	public void chooseState() {
		int nextPhase = random.nextInt(2);
		if(nextPhase==0) {
			bossState = BossState.RUNNING;
			int dir = random.nextInt(2);
			if(dir==0) setVelX(-4);
			else setVelX(4);
		}
	}
	
}
