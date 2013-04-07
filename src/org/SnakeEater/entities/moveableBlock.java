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

public class moveableBlock extends MoveableEntity{
	
	private Stack<Animation> animationStack = new Stack<Animation>();
	//ResourceManager context

    
    //StateBasedGame context
    private StateBasedGame game;
	
	public moveableBlock(Shape shape) {
		super(shape);
		nextStep = new SmRectangle(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void collidingAction(int amount, String direction){
		if(direction =="x"){
			nextStep.setX(
	        		shape.getX() + amount);
	        nextStep.setY(shape.getY());
		colliding = checkCollisions(((MainState)game.getCurrentState()).getEntity(), calcLAABB(), this);
		if(!colliding)
			shape.setX(shape.getX() + amount);
		}
		else
		{
			nextStep.setX(
	        		shape.getX());
	        nextStep.setY(shape.getY()+amount);
		colliding = checkCollisions(((MainState)game.getCurrentState()).getEntity(), calcLAABB(), this);
		if(!colliding)
			shape.setY(shape.getY() + amount);
		}
	}
	
	@Override
    public void init(GameContainer gc, StateBasedGame game) {
        super.init(gc, game);
        ((Game) game).getRenderQueue().add(this);

        this.game = game;
        movementSpeed = 1;
    }
	
	public void setupAnimations(StateBasedGame game) {
        //Set and load player's static 'animation'
        Image[] player = new Image[] { ((Game) game).getResourceManager().getAnimation("block").getImage(0)};
        ((Game) game).getResourceManager().load("block", new Animation(player, 1));
        animationStack.push(((Game) game).getResourceManager().getAnimation("block"));
    }
	
	@Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        super.render(gc, game, g);
        if(animationStack.empty())
        	this.setupAnimations(game);
        //g.drawImage(((Game) game).getResourceManager().getImage("player"), shape.getX(), shape.getY());
        g.drawAnimation(animationStack.peek(), shape.getX() - ((animationStack.peek().getWidth() - shape.getWidth())/2), shape.getY());
        if(((Game) game).isDebug()) {
            g.setColor(new Color(0, 125, 125, 128));
            g.setColor(Color.cyan);
            g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
            g.setColor(Color.orange);
            g.fillRect(collidingBlock.getShape().getX(), collidingBlock.getShape().getY(), collidingBlock.getShape().getWidth(), collidingBlock.getShape().getHeight());
        }
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
