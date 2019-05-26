package marioTest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import input.KeyInput;
import input.MouseInput;
import mario.entity.Entity;
import mario.tile.Tile;
import mariogfx.SpriteSheet;
import mariogfx.gui.Launcher;
import mariogfx.Sprite;

public class Game extends Canvas implements Runnable{
	 //Databses information

	private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "Liujinyi0328";
	
	
	public static int i =0;
public static int y = 100;
	public static final int WIDTH=320;	
	public static final int HEIGHT=180;
	public static final int SCALE = 4;
	public static final String TITLE = "Mario";
	public static JFrame frame =new JFrame (TITLE);
	private static Thread thread;
	private static boolean running= false;
//	private BufferedImage level1 ;
//	private BufferedImage level2 ;
	private static BufferedImage[] levels;
	private static BufferedImage background;
	
	private static int level=0;
	
	public static int coins = 0;
	public static int lives = 1;
	public static int deathScreenTime = 0;
	public static int deathY = 0;
	public static int score = 0;
	
	public static boolean showDeathScreen = true;
	public static boolean gameOver = false;
	public static boolean playing = false;
	
	public static Handler handler;
	
	public static int playerX,playerY;
	
	public static SpriteSheet sheet;
	
	public static Camera cam;
	
	public static Launcher launcher;
	
	public static MouseInput mouse;
	
	public static Sprite grass;
	public static Sprite mushroom;
	public static Sprite lifeMushroom;
	public static Sprite powerUp;
	public static Sprite coin;
	public static Sprite star;
	public static Sprite fireball;
	public static Sprite flower;

	public static Sprite usedPowerUp;

	public static Sprite[] player ;
	public static Sprite[] goomba;
	public static Sprite[] flag;
	public static Sprite[] particle;
	public static Sprite[] firePlayer;
	public static Sprite[] koopa;
	public static Sprite[] pipe;

	public static Sound jump;
	public static Sound goombastomp;
	public static Sound levelcomplete;
	public static Sound losealife;
	public static Sound themesong;
	public static Sound damage;
	

	public Game() {
		Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}
	
	private void init() {
		 handler = new Handler();
		 cam = new Camera();
		 launcher = new Launcher();
		 mouse = new MouseInput();
		 sheet = new SpriteSheet("/SpriteSheet.png");
		 
		 addKeyListener(new KeyInput());
		 addMouseListener(mouse);
		 addMouseMotionListener(mouse);
		 
		 grass = new Sprite(sheet, 1, 1);
		 mushroom = new Sprite(sheet,2,1);
		 lifeMushroom = new Sprite(sheet, 6, 1);
		 coin = new Sprite(sheet, 5, 1);
		 powerUp = new Sprite(sheet,3,1);
		 usedPowerUp = new Sprite(sheet,4,1);
		 star = new Sprite(sheet, 7, 1);
		 fireball = new Sprite(sheet, 9, 1);
		 flower = new Sprite(sheet, 8, 1);

		 player = new Sprite[8];
		 goomba = new Sprite[10];
		 flag = new Sprite[3];
		 pipe = new Sprite[2];
		 particle = new Sprite[6];
		 firePlayer = new Sprite[8];//10?
		 koopa = new Sprite[8];

		 levels = new BufferedImage[2];
		 
		 for(int i=0; i<player.length;i++) {
			 player [i] = new Sprite(sheet, i+1, 16);
		 }
		 
		 for(int i=0; i<goomba.length;i++) {
			 goomba [i] = new Sprite(sheet, i+1, 15);
		 }
		 for(int i=0; i<koopa.length;i++) {
			 koopa [i] = new Sprite(sheet, i+1, 12);
		 }
		 for(int i=0; i<flag.length;i++) {
			 flag[i] = new Sprite(sheet, i+1, 2);
		 }
		 for(int i=0; i<pipe.length;i++) {
			 pipe[i] = new Sprite(sheet, i+4, 2);
		 }
		 for(int i=0; i<particle.length;i++) {
			 particle[i] = new Sprite(sheet, i+1, 14);
		 }
		 
		 for(int i=0;i<firePlayer.length;i++) {
			 firePlayer[i] = new Sprite(sheet, i+9, 15);
		 }
		 
		 try {
			levels[0] = ImageIO.read(getClass().getResource("/level.png"));
			levels[1] = ImageIO.read(getClass().getResource("/level2.png"));
			background = ImageIO.read(getClass().getResource("/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 jump = new Sound("/audio/jump.wav");
		 goombastomp = new Sound("/audio/goombastomp.wav");
		 levelcomplete = new Sound("/audio/levelcomplete.wav");
		 losealife = new Sound("/audio/losealife.wav");
		 themesong = new Sound("/audio/themesong.wav");
		 damage = new Sound("/audio/damage.wav");
	}
	private synchronized void start() {
		if(running) return ;//if running is true you get out of this method
		running = true;
		thread=new Thread (this,"Thread");
		thread.start();
	}
	private synchronized static void stop() {
		if(!running) return ;//if running is true you get out of this method
		else {
		running = false;
		}
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		init();
		requestFocus();//so we can type request focus the frame will always into focus
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		double ns = 1000000000.0/60.0;
		int frames=0;
		int ticks=0;
		
		while(running) {
			long now = System.nanoTime();
			delta+=(now - lastTime)/ns;
			lastTime = now;
			while(delta>=1) {
				tick();
				ticks++;
				delta--;
			}
				try {
					render();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			frames++;
			if(System.currentTimeMillis()-timer>1000) {
				timer+=1000;
				System.out.println(frames + "Frames Per Second " + ticks + "Update per second");
				frames=0;
				ticks=0;//so it doesm't carry on from what it was second ago
			}
		}
		stop();
		
	}
	public void render() throws SQLException{
		BufferStrategy bs= getBufferStrategy();
		if ( bs==null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();//linking the graphic strategy to the buffered strategy
		

		
		if(!showDeathScreen) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		
		}
		if(showDeathScreen) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());//can not forget it
			if(!gameOver) {
			g.drawImage(player[0].getBufferedImage(), 500, 300, 100, 100,null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Courier",Font.BOLD,50));
			g.drawString("x"+lives, 610, 400);
			}else {   
				Game.score();				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Courier",Font.BOLD,50));
				g.drawString("Game Over :(", 300, 400);
				}

			}
		
		if(playing) g.translate(cam.getX(), cam.getY());//make it move
		if(!showDeathScreen&&playing)handler.render(g);
		else if(!playing) launcher.render(g);
		
		g.dispose();//dispose what we have created
		bs.show();
	}
	public void tick() {//update
		if(playing) handler.tick();
		
		for(int i= 0 ; i<handler.entity.size();i++) {//uses this entity for the camera
			Entity e=handler.entity.get(i);
			if(e.getId() == Id.player) {
				if(!e.goingDownPipe)cam.tick(e);
			}
		}
		if(showDeathScreen&&!gameOver&&playing) deathScreenTime++;
		if(deathScreenTime>=180) {
			if(!gameOver) {
			showDeathScreen = false;
			deathScreenTime=0;
			handler.clearLevel();
			handler.createLevel(levels[level]);
			}else if(gameOver) {
				
				showDeathScreen = false;
				deathScreenTime=0;
				playing=false;
				
				gameOver=false;
			}
			themesong.play();
		}
	}
	
	public static int getFrameWidth() {
		return WIDTH*SCALE;
	}
	
	public static int getFrameHeight() {
		return HEIGHT*SCALE;
	}
	
	public static void switchLevel() {
		Game.level++;
		Game.score+=30;
		
		handler.clearLevel();
		handler.createLevel(levels[level]);
		
		Game.themesong.close();
		Game.levelcomplete.play();
	}
	
	public static Rectangle getVisibleArea() {
		for(int i=0;i<handler.entity.size();i++) {
			Entity e = handler.entity.get(i);
			if(e.getId()==Id.player) {
				if(!e.goingDownPipe) {
				playerX=e.getX();
				playerY=e.getY();
				return new Rectangle(playerX-(getFrameWidth()/2-5), playerY-(getFrameHeight()/2-5),getFrameWidth()+360,getFrameHeight()+360);
				}
			}
		}
		return new Rectangle(playerX-(getFrameWidth()/2-5), playerY-(getFrameHeight()/2-5),getFrameWidth()+360,getFrameHeight()+360);
	}
	
	public static int getDeathY() {
		LinkedList<Tile> tempList = handler.tile;
		
		Comparator<Tile> tileSorter = new Comparator<Tile>() {

			@Override
			public int compare(Tile t1, Tile t2) {
				if(t1.getY()>t2.getY()) return -1;
				if(t1.getY()<t2.getY()) return 1;
				return 0;
			}
			
		};
		
		Collections.sort(tempList,tileSorter);
		return tempList.getFirst().getY()+tempList.getFirst().getHeight();
	}
	public static void score() throws SQLException {
		if(gameOver&&Game.i==0) {
				score+=lives;
//			
//				frame.setVisible(false);
				
			

				int y=100;

				
				
			    JFrame jf = new JFrame();
			    jf.setBounds(160, 250, 500, 600);
			    jf.setTitle("RANKING TABLE");
			    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    jf.getContentPane().setBackground(Color.BLACK);
			    jf.setVisible(true);
			    Graphics g = jf.getContentPane().getGraphics();
			    g.setColor(Color.WHITE);
//			    g.drawOval(100, 100, 50, 50);
			    g.drawString("First Name", 100, 50);
				g.drawString("Last Name", 200, 50);
				g.drawString("Score", 300, 50);
			  //input of user   
			    String firstName = JOptionPane.showInputDialog("Please enter your first name");
				String lastName = JOptionPane.showInputDialog("Please enter your last name");
			  //insert data into database
				try {
					Connection conn = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
					System.out.println("Connected");
					PreparedStatement pst = conn.prepareStatement("INSERT INTO user VALUES (?,?,?);");
					pst.setString(1, firstName);
					pst.setString(2, lastName);
					pst.setInt(3, score);
					int affected = pst.executeUpdate();
					pst.close();
					System.out.println(affected + " row(s) changed.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
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
//				    g.drawString(fname, 100, y);
//				    g.drawString(lname, 200, y);
//				    g.drawString(String.valueOf(score), 300, y);
//				    y+=15;
					System.out.println(fname + "\t" + lname + 	"\t" + score );
				}
				rs.close();
				pstm.close();
				conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			for(int i=0;i<arr1.length;i++) {
				g.drawString(String.valueOf(i+1), 50, y);
				g.drawString(arr1[i], 100, y);
				g.drawString(arr2[i], 200, y);
				g.drawString(arr3[i], 300, y);
				y+=15;
			}
			Game.i++;
		}
	}

	
	public static void main(String[] args) {
//		
		Game game =new Game();
////		JFrame frame =new JFrame (TITLE);
//		
//		frame.add(game);
//		frame.pack();//pack the game
//		frame.setResizable(false);//avoid resize the frame
//		frame.setLocationRelativeTo(null);//set the frame in the middle
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//		game.start();
		WindowMenu win = new WindowMenu("MARIO",20,30,WIDTH*SCALE,HEIGHT*SCALE);
		win.add(game);
		win.pack();
		win.setResizable(false);
//		win.setLocation(null);
//		win.setDefaultCloseOperation(EXIT_ON_CLOSE);
		win.setVisible(true);
		game.start();
	}



}
