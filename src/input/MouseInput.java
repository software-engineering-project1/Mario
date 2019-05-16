package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import marioTest.Game;
import mariogfx.gui.Button;

public class MouseInput implements MouseListener,MouseMotionListener{

	public int x,y;
	public void mouseDragged(MouseEvent e) {
		
		
	}

	
	public void mouseMoved(MouseEvent e) {
		x=e.getX();
		y=e.getY();
		
	}

	
	public void mouseClicked(MouseEvent e) {
		
		
	}

	
	public void mouseEntered(MouseEvent e) {
		
		
	}

	
	public void mouseExited(MouseEvent e) {
				
	}

	
	public void mousePressed(MouseEvent e) {
		for(int i=0;i<Game.launcher.buttons.length;i++) {
			Button button = Game.launcher.buttons[i];
			if(x>=button.getX()&&y>=button.getY()&&x<=button.getX()+button.getWidth()&&y<=button.getY()+button.getHeight()) button.triggerEvent();
			
		}
	}

	
	public void mouseReleased(MouseEvent e) {
		
		
	}
	
}
