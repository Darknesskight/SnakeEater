package org.SnakeEater.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.SnakeEater.Game;
import org.SnakeEater.entities.Camera;
import org.SnakeEater.entities.Entity;
import org.SnakeEater.entities.GameMap;
import org.SnakeEater.entities.Player;
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

public class MainState extends BasicGameState{

	protected List<Entity> Entities = new ArrayList<Entity>();;
	protected GameMap maps;
	GameContainer gc;
	StateBasedGame game;
	protected boolean previouslyLoaded;
	public Camera camera;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		previouslyLoaded = false;
		for (Entity e : Entities)
	        e.init(gc, game);
		this.game = game;
		this.gc = gc;
		
		
	}
	@Override
	public void enter(GameContainer gc, StateBasedGame game){
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		g.scale(camera.getScale(), camera.getScale());
        g.translate(-camera.getX(), -camera.getY());
        Collections.sort(((Game) game).getRenderQueue(), new RenderableComparator());
        for(int i = ((Game) game).getRenderQueue().size()-1; i >= 0; i--) {
            ((Game) game).getRenderQueue().get(i).render(gc, game, g);
        }
		
	}
	public Player getPlayer(){
		return (Player) Entities.get(0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		for (Entity e : Entities)
	        e.update(gc, game, delta);
        maps.update(gc, game, delta);
        if(gc.getInput().isKeyPressed(Input.KEY_D)) {
            if(((Game) game).isDebug()) ((Game) game).setDebug(false);
            else ((Game) game).setDebug(true);
        }
        camera.update(gc, game, delta);
		
	}
	
	@Override
    public void leave(GameContainer gc, StateBasedGame game){
    	    	((Game) game).getRenderQueue().clear();
    	
    }
	
	public void addEntity(Entity S){
		S.init(gc, game);
		Entities.add(S);
	}
	
	public List<Entity> getEntity(){
		return Entities;
	}
	
	@Override
	public int getID() {
		return 0;
	}

	public GameMap getMaps() {
		return maps;
	}

	public void setMaps(GameMap maps) {
		this.maps = maps;
	}

}
