package marioTest;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
 
 
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
		setTitle(s);
		menubar = new JMenuBar();
		menu = new JMenu("Menu");
		subMenu = new JMenu("Rank");
		item1 = new JMenuItem("About",new ImageIcon("1.jpg"));
		item2 = new JMenuItem("Exit",new ImageIcon("2.jpg"));
		item1.setAccelerator(KeyStroke.getKeyStroke('A'));
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Super Mario by Group 14");
			}
		});
		item2.setAccelerator(KeyStroke.getKeyStroke('E'));
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(item1);
		menu.addSeparator();
		menu.add(item2);
		menu.add(subMenu);
		item3 = new JMenuItem("Score Rank",new ImageIcon("3.jpg"));
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFrame jf = new MyJFrame();
			        jf.setBounds(200, 250, 500, 600);
			        jf.setTitle("Ranking list");
			        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        jf.setVisible(true);
			        Graphics g = jf.getRootPane().getGraphics();
			        Timer t = new Timer(1000, new ActionListener() {
			 
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                Graphics g = jf.getRootPane().getGraphics();
			            }
			        });
			        t.start();
			}
		});
		subMenu.add(item3);
		menubar.add(menu);
		setJMenuBar(menubar);
	
	}
}

class MyJFrame extends JFrame {
	public static final String DB_URL = "jdbc:mysql://localhost:3306/mario";
	public static final String USER_NAME = "root";
	public static final String PASSWORD = "Liujinyi0328";
	int y =100;
	String arr1[] = new String[100];
	String arr2[] = new String[100];
	String arr3[] = new String[100];
	
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
//        g.fillRect(100, 100, 50, 50);
    	g.drawString("First Name", 100, 50);
		g.drawString("Last Name", 200, 50);
		g.drawString("Score", 300, 50);
//		g.draw3DRect(300, 300, 50, 50, false);
		getData(arr1, arr2, arr3);
//		for(int i=0;arr1[i]!=null;i++) {
//		g.drawString(String.valueOf(i+1), 50, y);
//		}
		for(int i=0;arr1[i]!=null;i++) {
			g.setColor(Color.black);
			g.drawString(String.valueOf(i+1), 50, y);
			g.drawString(arr1[i], 100, y);
			g.drawString(arr2[i], 200, y);
			g.drawString(arr3[i], 300, y);
			System.out.println("$$"+arr1[i] + "\t" + arr2[i] + 	"\t" + arr3[i] );
			y+=15;
		}
    }
    public void getData(String arr1[],String arr2[],String arr3[]) {
    	int j =0;
    	try {
    		
			Connection conn3 = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
			System.out.println("Connected!");
			
			PreparedStatement pstm = conn3.prepareStatement("SELECT userFirstName, userLastName, score FROM users ORDER BY score DESC");
			 
			ResultSet rs = pstm.executeQuery();
		while (rs.next()){
			String fname = rs.getString("userFirstName");
			String lname = rs.getString("userLastName");
			int score = rs.getInt("score");
			arr1[j]=fname;
			arr2[j]=lname;
			arr3[j]=String.valueOf(score);
			System.out.println(arr1[j] + "\t" + arr2[j] + 	"\t" + arr3[j] );
			j++;
		}
		rs.close();
		pstm.close();
		conn3.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
    }
 
}

