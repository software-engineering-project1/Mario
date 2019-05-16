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

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import input.KeyInput;
import input.MouseInput;
import mario.entity.Entity;
import mario.entity.Player;
import mario.tile.Wall;
import mariogfx.SpriteSheet;
import mariogfx.gui.Launcher;
import mariogfx.Sprite;

public class Game extends Canvas implements Runnable{
	public static final int WIDTH=320;	
	public static final int HEIGHT=180;
	public static final int SCALE = 4;
	public static final String TITLE = "Mario";
	
	private Thread thread;
	private boolean running= false;
	private BufferedImage level1 ;
	private BufferedImage level2 ;
	private static BufferedImage[] levels;
	
	private static int playerX,playerY;
	private static int level = 0;
	
	public static int coins = 0;
	public static int lives = 5;
	public static int deathScreenTime = 0;
	
	public static boolean showDeathScreen = true;
	public static boolean gameOver = false;
	public static boolean playing = false;
	
	public static Handler handler;
	
	public static SpriteSheet sheet;
	
	public static Camera cam;
	
	public static Launcher launcher;
	
	public static MouseInput mouse;
	
	public static Sprite grass;
	public static Sprite mushroom;
	public static Sprite lifeMushroom;
	public static Sprite powerUp;
	public static Sprite coin;

	public static Sprite usedPowerUp;

	public static Sprite [] player ;
	public static Sprite [] goomba;
	public static Sprite [] flag;

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

		 player = new Sprite[10];
		 goomba = new Sprite[10];
		 flag = new Sprite[3];
		 levels = new BufferedImage[2];
		 
		 for(int i=0; i<player.length;i++) {
			 player [i] = new Sprite(sheet, i+1, 16);
		 }
		 for(int i=0; i<goomba.length;i++) {
			 goomba [i] = new Sprite(sheet, i+1, 15);
		 }
		 for (int i = 0; i < flag.length; i++) {
			flag[i] = new Sprite(sheet, 2, i+1);
		}
		 
		 try {
			levels[0] = ImageIO.read(getClass().getResource("/level.png"));
//			levels[0] = ImageIO.read(getClass().getResource("/level2.png"));
			//don't have level2 picture right now
		} catch (IOException e) {
			e.printStackTrace();
		}
//		 handler.createLevel(image);
//		 handler.addEntiy(new Player(300, 512, 32, 32, true, Id.player, handler));
//		 handler.addTile(new Wall(200, 200, 64, 64, true, Id.wall,handler));
	}
	private synchronized void start() {
		if(running) return ;//if running is true you get out of this method
		running = true;
		thread=new Thread (this,"Thread");
		thread.start();
	}
	private synchronized void stop() {
		if(!running) return ;//if running is true you get out of this method
		running = false;
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
			render();
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
	public void render() {
		BufferStrategy bs= getBufferStrategy();
		if ( bs==null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();//linking the graphic strategy to the buffered strategy
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());//can not forget it
		if(!showDeathScreen) {
		g.drawImage(coin.getBufferedImage(), 20, 20, 75, 75,null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier",Font.BOLD,20));
		g.drawString("x"+coins, 100, 95);
		}
		if(showDeathScreen) {
			if(!gameOver) {
			g.drawImage(player[0].getBufferedImage(), 500, 300, 100, 100,null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Courier",Font.BOLD,50));
			g.drawString("x"+lives, 610, 400);
			}else {
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
		
		handler.clearLevel();
		handler.createLevel(levels[level]);
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
	
	public static void main(String[] args) {
		Game game =new Game();
		JFrame frame =new JFrame (TITLE);
		
		frame.add(game);
		frame.pack();//pack the game
		frame.setResizable(false);//avoid resize the frame
		frame.setLocationRelativeTo(null);//set the frame in the middle
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.start();
	}



}
