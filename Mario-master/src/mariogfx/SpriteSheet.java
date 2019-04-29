package mariogfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SpriteSheet {
	private BufferedImage sheet;
	public SpriteSheet(String path) {
		try {
			sheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public BufferedImage getSprite(int x, int y) {
		return sheet.getSubimage(x*64-64, y*64-64, 64, 64);
		
	}


}
