package mario.entity;

import java.awt.Color;
import java.awt.Graphics;

import Tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;



public class Player extends Entity {

	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}


	public void render(Graphics g) {

		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}


	public void tick() {

		x += velX;
		y += velY;
		if( x<=0 ) x = 0;
		if( y<=0 ) y = 0;
		if( x+width >= 1080) x = 1080-width;
		if( y+height >= 771) y = 771-height;
		for(Tile t:handler.tile) {
			if(!t.solid)break;
			if(t.getId()==Id.wall) {
				if(getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
					if(jumping) {
						jumping = false;
						gravity = 0.0;
						falling= true;
					}
				}
				if(getBoundsBottom().intersects(t.getBounds())) {
					setVelY(0);
					if(falling)falling=false;
				}else {
					if(!falling &&!jumping) {
						gravity = 0.0;
						falling = true;
					}
				}
				if(getBoundsLeft().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX()+t.width;
				}
				if(getBoundsRight().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX()-t.width;
				}
			}
		}
		if(jumping) {
			gravity-=0.1;
			setVelY((int)-gravity);
			if(gravity<=0.0) {
				jumping = false;
				falling = true;
			}
		}
		if(falling) {
			gravity+=0.1;
			setVelY((int)gravity);
		}
	}
}
