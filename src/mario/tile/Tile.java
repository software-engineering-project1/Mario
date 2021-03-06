package mario.tile;

import java.awt.Graphics;
import java.awt.Rectangle;

import marioTest.Handler;
import marioTest.Id;


public abstract class Tile {
	public int x,y;//every entity has the position
	public int width , height; 
	public int velX ,velY;
	public boolean solid=false;
	public boolean activated=false;

	public int facing;
	public Handler handler;
	public Id id;
	
	public Tile (int x,int y ,int width,int height,boolean solid,Id id,Handler handler) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.solid = solid;
		this.handler=handler;
		this.id=id;
	}

	public void die() {
		handler.removeTile(this);

	}
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
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public boolean isSolid() {
		return solid;
	}
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public void setVelY(int celY) {
		this.velY = celY;
	}
	public abstract void render(Graphics g) ;
		//why we use graphics instead of buffered strategy is we need to create many buffered strategies
		
	

	public abstract void tick();/* {
		x+=velX;
		y+=velY;
	}*/
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),width,height);		
	}

	public Id getId() {
		return id;
	}
}
