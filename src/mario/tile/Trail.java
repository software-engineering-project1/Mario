package mario.tile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import marioTest.Handler;
import marioTest.Id;

public class Trail extends Tile {
	
	private float alpha = 1.0f;
	
	private BufferedImage image;

	public Trail(int x, int y, int width, int height, boolean solid, Id id, Handler handler,BufferedImage image) {
		super(x, y, width, height, solid, id, handler);
		
		this.image=image;
		}

	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
			
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public void tick() {
		alpha-=0.05;
		
		if(alpha<0.05) die();
		
	}

}
