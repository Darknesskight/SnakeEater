package org.SnakeEater.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.SnakeEater.Game;
import org.SnakeEater.entities.Camera;
import org.SnakeEater.entities.Entity;
import org.SnakeEater.entities.GameMap;
import org.SnakeEater.entities.MapLayer;
import org.SnakeEater.entities.Player;
import org.SnakeEater.entities.moveableBlock;
import org.SnakeEater.entities.pellet;
import org.SnakeEater.entities.snake;
import org.SnakeEater.geom.SmRectangle;
import org.SnakeEater.util.RenderableComparator;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Overworld extends MainState {

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		Entities.add(new Player(new SmRectangle(23*8, 20*8, 10, 10)));
		//Entities.add(new snake(new SmRectangle(24, 8, 16, 16)));
		//for(int i =0; i<6; i++)
		//Entities.add(new pellet(new SmRectangle(78, 78, 4, 4)));
		super.init(gc, game);
	}
    @Override
    public void enter(GameContainer gc, StateBasedGame game){
    	if(!previouslyLoaded){
    	maps = new GameMap(((Game)game).getResourceManager().getMap("testMap"));
    	camera = new Camera(new Vector2f(getPlayer().getShape().getCenterX(), getPlayer().getShape().getCenterY()), maps);
    	previouslyLoaded = true;
    	maps.init(gc, game);
    	}
    	maps.renderLayers();
    	((Game) game).getRenderQueue().add(maps);
    	for(Entity b : Entities){
    		((Game) game).getRenderQueue().add(b);
    	}
    	Entities.get(0).getShape().setLocation(23*8, 20*8);
    	camera.update(gc, game, 1);
    }
    @Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
    	super.render(gc, game, g);
    }
	
    @Override
    public int getID() {
        return Game.OVERWORLD_ID;
    }

}
