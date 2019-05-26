package marioTest;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
 
import javax.swing.*;
 
 
public class WindowMenu extends JFrame {
	JMenuBar menubar;
	JMenu menu,subMenu;
	JMenuItem item1,item2;
	public WindowMenu(){}
	public WindowMenu(String s,int x,int y,int w,int h){
		init(s);
		setLocation(x,y);
		setSize(w,h);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	void init(String s) {
		// TODO Auto-generated method stub
		setTitle(s);
		menubar = new JMenuBar();
		menu = new JMenu("Menu");
		subMenu = new JMenu("SubMenu");
		item1 = new JMenuItem("Item1",new ImageIcon("1.jpg"));
		item2 = new JMenuItem("Item2",new ImageIcon("2.jpg"));
		item1.setAccelerator(KeyStroke.getKeyStroke('A'));
		item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
		menu.add(item1);
		menu.addSeparator();
		menu.add(item2);
		menu.add(subMenu);
		subMenu.add(new JMenuItem("submenu",new ImageIcon("3.jpg")));
		menubar.add(menu);
		setJMenuBar(menubar);
	}
}
