package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mario.entity.Entity;
import mario.tile.Tile;
import marioTest.Game;
import marioTest.Id;

public class KeyInput implements KeyListener{

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i=0;i<Game.handler.entity.size();i++) {
			Entity en=Game.handler.entity.get(i);
		//handler in Game should be public 
			if(en.getId()==Id.player) {
				if (en.goingDownPipe) return;//get out this entire method
				switch(key) {
				case KeyEvent.VK_W:
					for(int q=0;q<Game.handler.tile.size();q++) {
						Tile t = Game.handler.tile.get(q);
						if(t.getId()==Id.pipe) {
							if (en.getBoundsTop().intersects(t.getBounds())) {
								if (!en.goingDownPipe) en.goingDownPipe = true;
									
								
							}
						}
					}
					if (!en.jumping) {
						en.jumping = true;
						en.gravity = 8.0;
					}
					break;
				case KeyEvent.VK_S:
					for(int q=0;q<Game.handler.tile.size();q++) {
						Tile t = Game.handler.tile.get(q);
						if(t.getId()==Id.pipe) {
							if (en.getBoundsBottom().intersects(t.getBounds())) {
								if (!en.goingDownPipe) en.goingDownPipe = true;
									
								
							}
						}
					}
					
				
					break;
				
				case KeyEvent.VK_A:
					en.setVelX(-5);
					en.facing = 0;
					break;
				case KeyEvent.VK_D:
					en.setVelX(5);
					en.facing = 1;
					break;
				}
			}

		}

		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(Entity en:Game.handler.entity) {//handler in Game should be public 
			
			if (en.getId()==Id.player) {
				switch(key) {
				case KeyEvent.VK_W:
					en.setVelY(0);
					break;
				case KeyEvent.VK_S:
					en.setVelY(0);
					break;
				case KeyEvent.VK_A:
					en.setVelX(0);
					break;
				case KeyEvent.VK_D:
					en.setVelX(0);
					break;
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//not using
		
	}
}
