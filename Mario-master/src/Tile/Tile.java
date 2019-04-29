package Tile;


import java.awt.Graphics;
import java.awt.Rectangle;

import marioTest.Handler;
import marioTest.Id;



public abstract class Tile {

	public int x, y;
	public int width, height;
	
	public boolean solid;
	public Id id;
	
	public int velX, velY;
	
	public Handler handler;
	
	public Tile(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}
	
	public void die() {
		handler.removeTile(this);
	}
	public abstract void render(Graphics g);
	public abstract void tick();

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

	public boolean isSolid() {
		return solid;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),width,height); 
	}
}
