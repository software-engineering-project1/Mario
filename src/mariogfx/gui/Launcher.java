package mariogfx.gui;

import java.awt.Color;
import java.awt.Graphics;

import marioTest.Game;

public class Launcher {
	
	public Button[] buttons;
	
	public Launcher() {
		buttons = new Button[2];
		
		buttons[0] = new Button(100, Game.getFrameHeight()/2-100, Game.getFrameWidth()-200, 100, "Start Game");
		buttons[1] = new Button(100, Game.getFrameHeight()/2+100, Game.getFrameWidth()-200, 100, "Exit Game");
	}
	
	
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Game.getFrameWidth(), Game.getFrameHeight());
		
		for(int i=0;i<buttons.length;i++) {
			buttons[i].render(g);
		}
	}

}
