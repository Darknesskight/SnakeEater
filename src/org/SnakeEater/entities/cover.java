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
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public class cover extends Entity{
	
	private Stack<Animation> animationStack = new Stack<Animation>();
	//ResourceManager context

    
    //StateBasedGame context
    private StateBasedGame game;
	
	public cover(Shape shape) {
		super(shape);
		name = "cover";
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
    public void init(GameContainer gc, StateBasedGame game) {
        super.init(gc, game);
        //((Game) game).getRenderQueue().add(this);

        this.game = game;
    }
	
	@Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        super.render(gc, game, g);
            g.setColor(Color.black);
            g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
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
