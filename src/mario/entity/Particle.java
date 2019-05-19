package mario.entity;

import java.awt.Graphics;

import marioTest.Game;
import marioTest.Handler;
import marioTest.Id;

public class Particle extends Entity{
	private int frame =0;
	private int frameDelay = 0;
	private boolean fading = false;

	public Particle(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		
	}

	
	public void render(Graphics g) {
		if(!fading) g.drawImage(Game.particle[frame].getBufferedImage(),getX(),getY(),getWidth(),getHeight(),null);
		else g.drawImage(Game.particle[Game.particle.length-(frame - Game.particle.length-2)].getBufferedImage(),getX(),getY(),getWidth(),getHeight(),null);
	}

	
	public void tick() {
		frameDelay++;
		
		if(frameDelay>=3) {
			frame++;
			frameDelay=0;
			
			
		}
		if(frame>=Game.particle.length-1) fading = true;
		if(frame>=13) die();
		
	}
	
}
