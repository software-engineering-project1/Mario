package mario.entity;


import java.awt.Graphics;
import java.awt.Rectangle;

import mario.state.BossState;
import mario.state.KoopaState;
import mario.state.PlayerState;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public abstract class Entity {

	public int x,y;//every entity has the position
	public int width , height; 
	public int facing = 0;//0-left,1 - right
	public int velX ,velY;
	public int hp;
	public int phaseTime;
	public int type;

	public boolean jumping = false;
	public boolean falling = false;
	public boolean goingDownPipe = false;
	public boolean attackable = false;
	

	public Id id ;
	public BossState bossState;
	public KoopaState koopaState;
	public PlayerState state;
	
	public double gravity = 0.0;
	public Handler handler;
	public Entity (int x,int y ,int width,int height,Id id,Handler handler) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.id=id;
		this.handler=handler;
	}
	public void die() {
		handler.removeEntity(this);
		if(getId()==Id.player) {
		Game.lives--;
		Game.showDeathScreen=true;
		if(Game.lives<=0) Game.gameOver=true;
		
		Game.losealife.play();
		}
	}
	public abstract  void render(Graphics g) ;//why we use graphics instead of buffered strategy is we need to create many buffered strategies
	public abstract  void tick();
	

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Id getId() {
		return id;
	}
	public int getType() {
		return type;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}
	public void setVelY(int celY) {
		this.velY = celY;
	}
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),width,height);		
	}
	public Rectangle getBoundsTop() {
		return new Rectangle(getX()+5,getY(),width-10,5);

/*		return new Rectangle(getX()+10,getY(),width-20,5);
*/	}
	public Rectangle getBoundsBottom() {
		return new Rectangle(getX()+10,getY()+height-5,width-20,5);
	}
	public Rectangle getBoundsLeft() {
		return new Rectangle(getX(),getY()+10,5,height-20);
	}
	public Rectangle getBoundsRight() {
		return new Rectangle(getX()+width-5,getY()+10,5,height-20);
	}
	
}





