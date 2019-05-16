package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import marioTest.Game;
import mariogfx.gui.Button;

public class MouseInput implements MouseListener, MouseMotionListener {
	
	public int x,y;

	@Override
	public void mouseDragged(MouseEvent e) {
				
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
				
	}

	@Override
	public void mouseClicked(MouseEvent e) {
				
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(int i=0;i<Game.launcher.buttons.length;i++) {
			Button button = Game.launcher.buttons[i];
			
			if(x>=button.getX()&&y>=button.getY()&&x<=button.getX()+button.getWidth()&&y<=button.getY()+button.getHeight()) button.triggerEvent();
		}
				
	}

	@Override
	public void mouseReleased(MouseEvent e) {
				
	}

	@Override
	public void mouseEntered(MouseEvent e) {
				
	}

	@Override
	public void mouseExited(MouseEvent e) {
				
	}

}
