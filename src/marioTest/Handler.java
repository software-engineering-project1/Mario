package marioTest;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

import mario.entity.Entity;
import mario.entity.Player;
import mario.entity.mob.Goomba;
import mario.entity.power.Mushroom;
import mario.tile.Pipe;
import mario.tile.PowerUpBlock;
import mario.tile.Tile;
import mario.tile.Wall;

public class Handler {//a LickedList to add entities

	public LinkedList <Entity> entity =new LinkedList <Entity>();
	public LinkedList <Tile> tile =new LinkedList <Tile>();

	public void render(Graphics g) {//because we have had graphic in the Game
		for(Entity en: entity) {
			en.render(g);
		}
		for(Tile ti: tile) {
			ti.render(g);
		}
	}
	
		
	public void tick() {
		for(Entity en: entity) {
			en.tick();
		}
		for(Tile ti:tile) {
			ti.tick();
		}
	}
	public void addEntity(Entity en) {
		entity.add(en);
	}	
	public void removeEntity(Entity en) {//when i use public this method can not be used as handler.removeEntity,why ,it is strange
		entity.remove(en);
	}
	public void addTile(Tile ti) {
		tile.add(ti);
	}	
	public void removeTile(Tile ti) {
		tile.remove(ti);
	}
	public void createLevel(BufferedImage level) {
		int width = level.getWidth();
		int height = level.getHeight();
		for( int y=0;y<height;y++) {
			for (int x= 0 ; x< width;x++) {
				int pixel = level.getRGB(x, y);
				int red = (pixel >>16 )&0xff;
				int green = (pixel >>8 )&0xff;
				int blue = (pixel )&0xff;
				if(red==0&&blue==0&&green==0) addTile(new Wall(x*32, y*32, 64, 64, true, Id.wall, this));
				if(red==0&&blue==255&&green==0) addEntity(new Player(x*32, y*32, 48, 48,Id.player, this));//0000ff
				if(red==255&&green==0&&blue==0) addEntity(new Mushroom(x*32, y*32, 64, 64, Id.mushroom, this));//ff0000
				if(red==255&&green==119&&blue==0) addEntity(new Goomba(x*32, y*32, 64, 64, Id.goomba,this));//ff7700
				if(red==255&&green==255&&blue==0) addTile(new PowerUpBlock(x*32, y*32, 64, 64,true, Id.powerUp,this,Game.mushroom));//ffff00
				if(red==0&&(green>123&&green<129)&&blue==0) addTile(new Pipe(x*32, y*32, 64, 64*5,true,Id.pipe,this,128-green));//ff7700
			}
		}
//		for(int i=0; i<Game.WIDTH*Game.SCALE/32+1;i++) {//the width of our tile is 32
//			addTile(new Wall(i*32, Game.HEIGHT*Game.SCALE-32, 32,32, true, Id.wall,this));
//			if(i!=0&&i!=1&&i!=32&&i!=17)addTile(new Wall(i*32, 300, 32,32, true, Id.wall,this));
//		}
	}


}
