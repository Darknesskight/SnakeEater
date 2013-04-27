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

public class BossState extends MainState {

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		Entities.add(new Player(new SmRectangle(18*16+10, 18*16+10, 10, 10)));
		Entities.add(new snake(new SmRectangle(24, 8, 16, 16)));
		for(int i =0; i<6; i++)
		Entities.add(new pellet(new SmRectangle(78, 78, 4, 4)));
		super.init(gc, game);
	}
    @Override
    public void enter(GameContainer gc, StateBasedGame game){
    	if(!previouslyLoaded){
    		maps = new GameMap(((Game)game).getResourceManager().getMap("bossTest"));
    		camera = new Camera(new Vector2f(getPlayer().getShape().getCenterX(), getPlayer().getShape().getCenterY()), maps);
    		previouslyLoaded = true;
    		maps.init(gc, game);
    	}
    	maps.renderLayers();
    	 for(Entity b : Entities)
    	    	((Game) game).getRenderQueue().add(b);
    	 ((Game) game).getRenderQueue().add(maps);
    	 Entities.get(0).getShape().setLocation(17*8, 37*8);
    	 camera.update(gc, game, 1);
    }
    
    
    @Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
    	super.render(gc, game, g);
    }
	
    @Override
    public int getID() {
        return Game.BOSS_STATE_ID;
    }

}
