package org.SnakeEater.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.SnakeEater.Game;
import org.SnakeEater.entities.Entity;
import org.SnakeEater.entities.GameMap;
import org.SnakeEater.entities.MapLayer;
import org.SnakeEater.entities.Player;
import org.SnakeEater.entities.moveableBlock;
import org.SnakeEater.entities.snake;
import org.SnakeEater.geom.SmRectangle;
import org.SnakeEater.util.RenderableComparator;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class InGameState extends MainState {

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		Entities.add(new Player(new SmRectangle(40, 40, 10, 10)));
		Entities.add(new snake(new SmRectangle(24, 8, 16, 16)));
		super.init(gc, game);
	}
    @Override
    public void enter(GameContainer gc, StateBasedGame game){
    	maps = new GameMap(((Game)game).getResourceManager().getMap("bossTest"));
    	maps.init(gc, game);
    	
    }
    @Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
    	super.render(gc, game, g);
    }
	
    @Override
    public int getID() {
        return Game.IN_GAME_STATE_ID;
    }

}