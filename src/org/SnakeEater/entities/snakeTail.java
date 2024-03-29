package org.SnakeEater.entities;

import java.util.List;
import java.util.Stack;

import org.SnakeEater.Game;
import org.SnakeEater.geom.SmRectangle;
import org.SnakeEater.states.MainState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public class snakeTail extends MoveableEntity{

	
	//Stack containing the animations being used by the player. Only the top of the stack will render
    public Stack<Animation> animationStack = new Stack<Animation>();
    
    StateBasedGame game;
    
    boolean newPart = false;
    
    int wait = 0;
    
	public snakeTail(Shape shape) {
		super(shape);
		nextStep = new SmRectangle(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		// TODO Auto-generated constructor stub
		dir = "left";
		name="snake";
	}
	@Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
		checkCollisions();
	}
	public void render(GameContainer gc, StateBasedGame game, Graphics g) {
		super.render(gc, game, g);
		this.game = game;
		if(animationStack.empty())
			this.setupAnimations(game);
		//g.drawImage(((Game) game).getResourceManager().getImage("player"), shape.getX(), shape.getY());
		g.drawAnimation(animationStack.peek(), shape.getX() - ((animationStack.peek().getWidth() - shape.getWidth())/2), shape.getY());
		if(((Game) game).isDebug()) {
			g.setColor(Color.cyan);
			g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		}
	}
	
	private void setXPosition(int amount){
		shape.setLocation(shape.getX() + amount, shape.getY());
	}
	
	private void setYPosition(int amount){
		shape.setLocation(shape.getX(), shape.getY()+amount);
	}
	
	public void moveSnake(String string) {
		if(!newPart){	
			if(string == "left"){
				setXPosition(-2);
				dir = "left";
			}
			else if(string == "right"){
				setXPosition(2);
				dir = "right";
			}
			else if(string == "down"){
				setYPosition(2);
				dir = "down";
			}
			else if(string == "up"){
				setYPosition(-2);
				dir = "up";
			}
		}
		else if(wait == 7){
			newPart=false;
		}
		else{
			wait++;
		}
		
	}

	public void dead(){
	}
	
	protected boolean checkCollisions() {
		boolean collison = false;
		if(shape.intersects(((MainState)game.getCurrentState()).getPlayer().shape)){
			((MainState)game.getCurrentState()).getPlayer().collidingAction(2, "");
		}
		return collison;
	}
	
	@Override
	public int getRenderPriority() {
		 return 1000;
	 }
	public snakeTail clone(){
		snakeTail temp = new snakeTail(new SmRectangle(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight()));
		temp.dir = dir; 
		temp.newPart=true;
		temp.animationStack = this.animationStack;
		((MainState)game.getCurrentState()).addEntity(temp);
		return temp;
	}
}
