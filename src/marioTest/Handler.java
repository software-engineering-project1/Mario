package marioTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

import mario.entity.Coin;
import mario.entity.Entity;
import mario.entity.Player;
import mario.entity.mob.Goomba;
import mario.entity.mob.Koopa;
import mario.entity.mob.TowerBoss;
import mario.entity.power.Mushroom;
import mario.entity.power.PowerStar;
import mario.tile.Flag;
import mario.tile.Pipe;
import mario.tile.PowerUpBlock;
import mario.tile.Tile;
import mario.tile.Wall;

public class Handler {//a LickedList to add entities

	public LinkedList <Entity> entity =new LinkedList <Entity>();
	public LinkedList <Tile> tile =new LinkedList <Tile>();

	public void render(Graphics g) {//because we have had graphic in the Game
		for(int i=0;i<entity.size();i++) {
			Entity en = entity.get(i);
			if(Game.getVisibleArea()!=null&&en.getBounds().intersects(Game.getVisibleArea())&&en.getId()!=Id.particle) en.render(g);
		}
		for(int i=0;i<tile.size();i++) {
			Tile  ti = tile.get(i);
			if(Game.getVisibleArea()!=null&&ti.getBounds().intersects(Game.getVisibleArea())) ti.render(g);
		}
		for(int i=0;i<entity.size();i++) {
			Entity en = entity.get(i);
			if(Game.getVisibleArea()!=null&&en.getBounds().intersects(Game.getVisibleArea())&&en.getId()==Id.particle) en.render(g);
		}
		
		g.drawImage(Game.coin.getBufferedImage(), Game.getVisibleArea().x, Game.getVisibleArea().y, 75, 75,null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier",Font.BOLD,20));
		g.drawString("x"+Game.coins, Game.getVisibleArea().x+100, Game.getVisibleArea().y+95);
	}
	
		
	public void tick() {
		for(int i=0; i<entity.size();i++) {
			Entity e = entity.get(i);
			e.tick();
		}
		for(int i=0;i<tile.size();i++) {
			Tile ti = tile.get(i);
			if(Game.getVisibleArea()!=null&&ti.getBounds().intersects(Game.getVisibleArea())) ti.tick();
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
				if(red==255&&green==0&&blue==0) addEntity(new Mushroom(x*32, y*32, 64, 64, Id.mushroom, this, blue ));//ff0000
				if(red==255&&green==119&&blue==0) addEntity(new Goomba(x*32, y*32, 64, 64, Id.goomba,this));//ff7700
				if(red==0&&green==128&&blue==128) addEntity(new Koopa(x*32, y*32, 64, 64, Id.koopa,this));//008080
				if(red==255&&green==255&&blue==0) addTile(new PowerUpBlock(x*32, y*32, 64, 64,true, Id.powerUp,this,Game.lifeMushroom,1));//ffff00
				if(red==0&&(green>123&&green<129)&&blue==0) addTile(new Pipe(x*32, y*32, 64, 64*5,true,Id.pipe,this,128-green,true));//ff7700,008000
				if(red==255&&green==165&&blue==0) addEntity(new Coin(x*32, y*32, 64, 64, Id.coin, this));//FFA500
				if(red==0&&green==255&&blue==255) addEntity(new TowerBoss(x*32, y*32, 64, 64, Id.towerBoss, this, 3));//00ffff
				if(red==105&&green==105&&blue==105) addTile(new Flag(x*32, y*32, 64, 64*5, true, Id.flag, this));//696969
//				if(red==169&&green==169&&blue==169) addEntity(new PowerStar(x*32, y*32, 64, 64,Id.star,this));//A9A9A9
				if(red==255&&green==182&&blue==193) addTile(new PowerUpBlock(x*32, y*32, 64, 64,true, Id.powerUp,this,Game.flower,0));//FFB6C1
			
			
			
			
			
//new level
//				if(red==0&&blue==0&&green==0) addTile(new Wall(x*32, y*32, 64, 64, true, Id.wall, this));
//			    if(red==0&&blue==255&&green==0) addEntity(new Player(x*32, y*32, 64, 64,Id.player, this));//0000ff
//			    if(red==255&&green==0&&blue==0) addEntity(new Mushroom(x*32, y*32, 64, 64, Id.mushroom, this));//ff0000
//			    if(red==255&&green==119&&blue==0) addEntity(new Goomba(x*32, y*32, 64, 64, Id.goomba,this));//ff7700
//			    if(red==255&&green==255&&blue==0) addTile(new PowerUpBlock(x*32, y*32, 64, 64,true, Id.powerUp,this,Game.mushroom));//ffff00
//			    if(red==0&&(green>123&&green<129)&&blue==0) addTile(new Pipe(x*240, y*32, 64, 64*3,true,Id.pipe,this,128-green));//ff7700
//			
			
			
			}
		}
		
		Game.deathY=Game.getDeathY();
//		for(int i=0; i<Game.WIDTH*Game.SCALE/32+1;i++) {//the width of our tile is 32
//			addTile(new Wall(i*32, Game.HEIGHT*Game.SCALE-32, 32,32, true, Id.wall,this));
//			if(i!=0&&i!=1&&i!=32&&i!=17)addTile(new Wall(i*32, 300, 32,32, true, Id.wall,this));
//		}
	}
	public void clearLevel() {
		entity.clear();
		tile.clear();
	}


}
