package marioTest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import input.KeyInput;
import mario.entity.Entity;
import mario.entity.Player;
import mario.tile.Wall;
import mariogfx.SpriteSheet;
import mariogfx.Sprite;

public class Game extends Canvas implements Runnable{
	public static final int WIDTH=320;	
	public static final int HEIGHT=180;
	public static final int SCALE = 4;
	public static final String TITLE = "Mario";
	private Thread thread;
	private boolean running= false;
	private BufferedImage image ;
	public static Handler handler;
	public static SpriteSheet sheet;
	public static Camera cam;
	public static Sprite grass;
	public static Sprite mushroom;
	public static Sprite powerUp;

	public static Sprite usedPowerUp;

	public static Sprite [] player ;
	public static Sprite [] goomba;

	public Game() {
		Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}
	
	private void init() {
		 handler = new Handler();
		 cam = new Camera();
		 sheet = new SpriteSheet("/SpriteSheet.png");
		 addKeyListener(new KeyInput());
		 grass = new Sprite(sheet, 1, 1);
		 mushroom =new Sprite(sheet,2,1);
		 powerUp =new Sprite(sheet,3,1);
		 usedPowerUp =new Sprite(sheet,4,1);

		 player = new Sprite[10];
		 goomba = new Sprite[10];
		 for(int i=0; i<player.length;i++) {
			 player [i] = new Sprite(sheet, i+1, 16);
		 }
		 for(int i=0; i<goomba.length;i++) {
			 goomba [i] = new Sprite(sheet, i+1, 15);
		 }
		 try {
			image = ImageIO.read(getClass().getResource("/level.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		 handler.createLevel(image);
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
		g.translate(cam.getX(), cam.getY());//make it move
		handler.render(g);
		
		g.dispose();//dispose what we have created
		bs.show();
	}
	public void tick() {//update
		handler.tick();
		
		for(int i= 0 ; i<handler.entity.size();i++) {//uses this entity for the camera
			Entity e=handler.entity.get(i);
			if(e.getId() == Id.player) {
				if(!e.goingDownPipe)cam.tick(e);
			}
		}
	}
	public int getFrameWidth() {
		return WIDTH*SCALE;
	}
	public int getFrameHeight() {
		return HEIGHT*SCALE;
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
