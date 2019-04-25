package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mario.entity.Entity;
import marioTest.Game;

public class KeyInput implements KeyListener{

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for(Entity en:Game.handler.entity) {//handler in Game should be public 
			switch(key) {
			case KeyEvent.VK_W:
				if(!en.jumping) {
					en.jumping = true;
					en.gravity=10.0;
				}
				break;
//			case KeyEvent.VK_S:
//				en.setVelY(5);
//				break;
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

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(Entity en:Game.handler.entity) {//handler in Game should be public 
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

	@Override
	public void keyTyped(KeyEvent arg0) {
		//not using
		
	}
}
