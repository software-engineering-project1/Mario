package marioTest;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

import mario.entity.Entity;
import mario.entity.Player;
import mario.tile.Tile;
import mario.tile.Wall;

public class Handler {//a LickedList to add entities

	public LinkedList <Entity> entity =new LinkedList <Entity>();
	public LinkedList <Tile> tile =new LinkedList <Tile>();
	public Handler() {
		createLeve();
	}
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
	public void addEntiy(Entity en) {
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
	private void createLeve() {
		for(int i=0; i<Game.WIDTH*Game.SCALE/64+1;i++) {//the width of our tile is 64
			addTile(new Wall(i*64, Game.HEIGHT*Game.SCALE-64, 64,64, true, Id.wall,this));
			if(i!=0&&i!=1&&i!=16&&i!=17)addTile(new Wall(i*64, 300, 64,64, true, Id.wall,this));
		}
	}


}
