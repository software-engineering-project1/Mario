package mario.entity;

import java.awt.Graphics;

import mario.tile.Tile;
import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Fireball extends Entity {

	public Fireball(int x, int y, int width, int height, Id id, Handler handler, int facing) {
		super(x, y, width, height, id, handler);
		
		switch(facing) {
		case 0:
			setVelX(-8);
			break;
		case 1:
			setVelX(8);
			break;
		}
		}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.fireball.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		
		for(int i=0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			if(t.isSolid()) {
				if(getBoundsLeft().intersects(t.getBounds())||getBoundsRight().intersects(t.getBounds())) die();
				
				if(getBoundsBottom().intersects(t.getBounds())) {
					jumping=true;
					falling=false;
					gravity=4.0;
				}else if(!falling&&!jumping) {
					falling=true;
					gravity=1.0;
				}else {
					jumping=false;
					falling=true;
				}
			}
		}
		
		for(int i=0;i<handler.entity.size();i++) {
			Entity e = handler.entity.get(i);
			
			if(e.getId()==Id.goomba||e.getId()==Id.plant||e.getId()==Id.koopa) {
				if(getBounds().intersects(e.getBounds())) {
					die();
					e.die();
				}
			}
		}
		
		if(jumping) {
			gravity-=0.31;
			setVelY((int) -gravity);// if gravity is 5.0 then the VelY is -5.0
			if(gravity<= 0.5) {
				jumping = false;
				falling = true;
			}
		}
		if ( falling) {
			gravity+=0.31;
			setVelY((int)gravity);
		}
	}

}
