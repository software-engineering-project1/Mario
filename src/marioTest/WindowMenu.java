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
	public static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	public static final String USER_NAME = "root";
	public static final String PASSWORD = "Liujinyi0328";
	int y =100;
	String arr1[] = new String[100];
	String arr2[] = new String[100];
	String arr3[] = new String[100];
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
			public void actionPerformed(ActionEvent event) {
//				 JFrame jf = new JFrame();
//				 Panel p = new Panel();
//				 jf.setBounds(160, 250, 500, 600);
//				    jf.setTitle("RANKING TABLE");
//				    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				    jf.getContentPane().setBackground(Color.BLACK);
//				    jf.setVisible(true);
//				    Graphics g = p.getGraphics();
//					g.setColor(Color.red);
//					g.drawOval(50, 50, 50, 50);
//					g.drawString("First Name", 100, 50);
//					g.drawString("Last Name", 200, 50);
//					g.drawString("Score", 300, 50);
////			        jf.setBounds(200, 250, 500, 600);
////			        jf.setTitle("Ranking list");
////			        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////			        jf.setVisible(true);
////			        Graphics g = jf.getRootPane().getGraphics();
////					g.setColor(Color.black);
////					g.drawString("First Name", 100, 50);
////					g.drawString("Last Name", 200, 50);
////					g.drawString("Score", 300, 50);
////					for(int i=0;arr1[i]!=null;i++) {
////						getData(arr1, arr2, arr3);
////						g.drawString(String.valueOf(i+1), 50, y);
////						g.drawString(arr1[i], 100, y);
////						g.drawString(arr2[i], 200, y);
////						g.drawString(arr3[i], 300, y);
////						System.out.println("$$"+arr1[i] + "\t" + arr2[i] + 	"\t" + arr3[i] );
////						y+=15;
////					}
//				 try {
//						Connection conn = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
//						System.out.println("Connected(menu)");
//					Statement st = conn.createStatement();
//					ResultSet rs = st.executeQuery("SELECT * FROM user order by score desc;");
//				
//					int i=1;
//					while (rs.next()){
//						String fname = rs.getString("fName");
//						String lname = rs.getString("lName");
//						int score = rs.getInt("score");
//						g.drawString(String.valueOf(i), 50, y);
//					    g.drawString("fname", 100, y);
//					    g.drawString("lname", 200, y);
//					    g.drawString("String.valueOf(score)", 300, y);
//					    i++;
//					    y+=15;
//						System.out.println(fname + "\t" + lname + 	"\t" + score );
//					}
//					rs.close();
//					st.close();
//					conn.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}

				

				int y=100;

				
				
			    JFrame frame = new JFrame();
			    frame.setBounds(160, 250, 500, 600);
			    frame.setTitle("RANKING TABLE");
			    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    frame.getContentPane().setBackground(Color.BLACK);
			    frame.setVisible(true);
			    Graphics g = frame.getContentPane().getGraphics();
			    g.setColor(Color.WHITE);
			    g.drawOval(100, 100, 50, 50);
			    g.drawString("First Name", 100, 50);
				g.drawString("Last Name", 200, 50);
				g.drawString("Score", 300, 50);
				String arr1[] = new String[100];
				String arr2[] = new String[100];
				String arr3[] = new String[100];
				
				//display database
				
				try {
					int j=0;
					Connection conn = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
					System.out.println("Connected");
//				Statement st = conn.createStatement();
//				ResultSet rs = st.executeQuery("SELECT * FROM user");
				PreparedStatement pstm = conn.prepareStatement("SELECT * FROM user ORDER BY score DESC");
				ResultSet rs = pstm.executeQuery();
				g.setColor(Color.white);
				g.drawString("First Name", 100, 50);
				g.drawString("Last Name", 200, 50);
				g.drawString("Score", 300, 50);
				
				while (rs.next()){
					String fname = rs.getString("fName");
					String lname = rs.getString("lName");
					int score = rs.getInt("score");
					arr1[j]= fname;
					arr2[j]=lname;
					arr3[j]=String.valueOf(score);
					j++;
				    g.drawString(fname, 100, y);
				    g.drawString(lname, 200, y);
				    g.drawString(String.valueOf(score), 300, y);
				    y+=15;
					System.out.println(fname + "\t" + lname + 	"\t" + score );
				}
				rs.close();
				pstm.close();
				conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			for(int i=0;arr1[i]!=null;i++) {
				g.drawString(String.valueOf(i+1), 50, y);
				g.drawString(arr1[i], 100, y);
				g.drawString(arr2[i], 200, y);
				g.drawString(arr3[i], 300, y);
				y+=15;
			}
			}
		});
		subMenu.add(item3);
		menubar.add(menu);
		setJMenuBar(menubar);
	
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
//
//class MyJFrame extends JFrame {
//	
//	
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        g.setColor(Color.black);
////        g.fillRect(100, 100, 50, 50);
//    	
////		
//		
////		for(int i=0;arr1[i]!=null;i++) {
////		g.drawString(String.valueOf(i+1), 50, y);
////		}
//		g.draw3DRect(300, 300, 50, 50, false);
//
//    }
//
// 
//}

