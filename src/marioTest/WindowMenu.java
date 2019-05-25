package marioTest;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
 
import javax.swing.*;
import javax.swing.event.MenuEvent;
 
 
public class WindowMenu extends JFrame {
	JMenuBar menubar;
	JMenu menu,subMenu;
	JMenuItem item1,item2,item3;
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
		subMenu = new JMenu("Rank");
		item1 = new JMenuItem("About",new ImageIcon("1.jpg"));
		item2 = new JMenuItem("Exit",new ImageIcon("2.jpg"));
		item1.setAccelerator(KeyStroke.getKeyStroke('A'));
		item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
		menu.add(item1);
//		item1.addActionListener(c);
		menu.addSeparator();
		menu.add(item2);
		menu.add(subMenu);
		item3 = new JMenuItem("score Rank",new ImageIcon("3.jpg"));
		subMenu.add(item3);
		menubar.add(menu);
		setJMenuBar(menubar);
	}
	public void menuSelected(MenuEvent e){
		 if(e.getSource()== item1)
		 operationForMenu1(); 
		 else if(e.getSource()== item2)
		 operationForMenu2(); 
		}
	private void operationForMenu2() {
		// TODO Auto-generated method stub
		System.exit(0);
	}
	private void operationForMenu1() {
		// TODO Auto-generated method stub
		
	} 
}
