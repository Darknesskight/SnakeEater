package org.SnakeEater.entities;

import java.util.Stack;

import org.SnakeEater.Game;
import org.SnakeEater.geom.SmRectangle;
import org.SnakeEater.states.MainState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class warp extends Entity{
	
	private Stack<Animation> animationStack = new Stack<Animation>();
	//ResourceManager context

    
    //StateBasedGame context
    private StateBasedGame game;
	String destination;
    
	public warp(Shape shape) {
		super(shape);
		name = "warp";
		
		// TODO Auto-generated constructor stub
	}
	
	public void moving(){
		System.out.println("destination: " + destination + "!!!");
		if(destination.equals("Snake"))
			game.enterState(Game.BOSS_STATE_ID, new FadeOutTransition(), new FadeInTransition());
		else if(destination.equals("overworld"))
			game.enterState(Game.OVERWORLD_ID, new FadeOutTransition(), new FadeInTransition());
		else if(destination.equals("dungeonOne"))
			game.enterState(Game.IN_GAME_STATE_ID, new FadeOutTransition(), new FadeInTransition());
	}
	
	public warp(Shape shape, String tileProperty) {
		super(shape);
		name = "warp";
		destination = tileProperty;
	}


	@Override
    public void init(GameContainer gc, StateBasedGame game) {
        super.init(gc, game);

        this.game = game;
    }
	
	@Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        super.render(gc, game, g);
    		//g.setColor(Color.cyan);
            //g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
        
    }
	
	@Override
    public int getRenderPriority() {
        return 1000;
    }
	
	 @Override
	    public void update(GameContainer gc, StateBasedGame game, int delta) {
	        super.update(gc, game, delta);
	        
	    }

}
