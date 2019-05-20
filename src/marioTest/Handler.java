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

public class Handler {//a LinkedList to add entities

	public LinkedList <Entity> entity =new LinkedList <Entity>();
	public LinkedList <Tile> tile =new LinkedList <Tile>();

	public void render(Graphics g) {//because we have had graphic in the Game
		for(Entity en: entity) {
			if(Game.getVisibleArea()!=null&&en.getBounds().intersects(Game.getVisibleArea())&& en.getId()!=Id.particle) en.render(g);
		}
		for(Tile ti: tile) {
			if(Game.getVisibleArea()!=null&&ti.getBounds().intersects(Game.getVisibleArea())) ti.render(g);
		}
		for(Entity en: entity) {
			if(Game.getVisibleArea()!=null&&en.getBounds().intersects(Game.getVisibleArea())&& en.getId()==Id.particle) en.render(g);
		}
		g.drawImage(Game.coin.getBufferedImage(), Game.getVisibleArea().x+20, Game.getVisibleArea().y+20, 75, 75,null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier",Font.BOLD,20));
		g.drawString("x"+Game.coins,Game.getVisibleArea().x+100, Game.getVisibleArea().y+95);
	}
	
		
	public void tick() {
		for(Entity en: entity) {
			en.tick();
		}
		for(Tile ti:tile) {
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
				if(red==255&&green==0&&blue==0) addEntity(new Mushroom(x*32, y*32, 64, 64, Id.mushroom,this, blue));//ff0000
				if(red==220&&green==20&&blue==60) addEntity(new Goomba(x*32, y*32, 64, 64, Id.goomba,this));//DC143C
				if(red==255&&green==215&&blue==0) addEntity(new Koopa(x*32, y*32, 64, 64, Id.koopa,this));//FFD700
				if(red==255&&green==255&&blue==0) addTile(new PowerUpBlock(x*32, y*32, 64, 64,true, Id.powerUp,this,Game.lifeMushroom,1));//ffff00
				if(red==255&&green==248&&blue==220) addEntity(new PowerStar(x*32, y*32, 64, 64,Id.star,this));//FFF8DC
				if(red==0&&(green>123&&green<129)&&blue==0) addTile(new Pipe(x*32, y*32, 64, 64*5,true,Id.pipe,this,128-green,true));//ff7700
				if(red==255&&green==165&&blue==0) addEntity(new Coin(x*32, y*32, 64, 64, Id.coin, this));//FFA500
				if(red==165&&green==42&&blue==42) addEntity(new TowerBoss(x*32, y*32, 64, 64, Id.towerBoss, this, 3));//A52A2A
				if(red==0&&green==255&&blue==0) addTile(new Flag(x*32, y*32, 64, 64*5, true, Id.flag, this));//00ff00
			}
		}
	}
	public void clearLevel() {
		entity.clear();
		tile.clear();
	}


}
