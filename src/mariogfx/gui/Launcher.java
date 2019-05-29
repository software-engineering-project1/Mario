package mariogfx.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import marioTest.Game;

public class Launcher {
	
	public Button[] buttons;
	private static BufferedImage background;
	
	
	public Launcher() {
		buttons = new Button[2];
		
		buttons[0] = new Button(100, Game.getFrameHeight()/2-100, Game.getFrameWidth()-200, 100, "Start Game");
		buttons[1] = new Button(100, Game.getFrameHeight()/2+100, Game.getFrameWidth()-200, 100, "Exit Game");
	}
	
	
	public void render(Graphics g) {
		try {
			background = ImageIO.read(getClass().getResource("/f.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		g.setColor(Color.GRAY);
		g.drawImage(background, 0, 0, Game.getFrameWidth(),Game.getFrameHeight(), null);
//		g.fillRect(0, 0, Game.getFrameWidth(), Game.getFrameHeight());
		
		for(int i=0;i<buttons.length;i++) {
			buttons[i].render(g);
		}
	}

}
