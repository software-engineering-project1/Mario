package mariogfx.gui;

import java.awt.Color;
import java.awt.Graphics;

import marioTest.Game;

public class Luancher {
	public  Button[]buttons ;
	public  Luancher() {
		buttons = new Button[2];
		buttons[0] = new Button(100,100,100,100,"Start the game");
		buttons[1] = new Button(200,200,100,100,"Exit the game");

	}
	//make a background
	public void render (Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(0, 0, Game.getFrameWidth(), Game.getFrameHeight());
	for (int i = 0; i < buttons.length; i++) {
		buttons[i].render(g);;
		
	}
	}
}
