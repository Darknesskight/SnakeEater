package org.SnakeEater.entities;

import java.util.List;
import java.util.Random;

import org.SnakeEater.Game;
import org.SnakeEater.geom.SmRectangle;
import org.SnakeEater.states.MainState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class pellet extends Entity{

	public pellet(Shape shape) {
		super(shape);
		name = "pellet";
		// TODO Auto-generated constructor stub
	}
	
	StateBasedGame game;
	
	boolean setLocation = true;
	
	@Override
    public void init(GameContainer gc, StateBasedGame game) {
        super.init(gc, game);
        this.game = game;
    }
	
	public void render(GameContainer gc, StateBasedGame game, Graphics g) {
		if(setLocation){
			moveToNonBlockLocation();
			setLocation = false;
		}
		g.setColor(Color.yellow);
		g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
    }
	@Override
	public int getRenderPriority() {
		 return 1000;
	 }
	public void collidingAction(int amount, String string){
    	moveToNonBlockLocation();
    }

	private void moveToNonBlockLocation() {
		Random rand = new Random();
		boolean placed = false;
		int x, y;
		while(!placed){
			x = rand.nextInt(15)+2;
			y = rand.nextInt(15)+2;
			if(!checkCollisions(((MainState)game.getCurrentState()).getEntity(), new SmRectangle(x*16+8,y*16+8,16,16), this)){
				placed = true;
				shape.setLocation(x*16+14,y*16+14);
			}
		}
	}
	protected boolean checkCollisions(List<Entity> Entities, Shape shapeToCheck, Entity e) {
        boolean colliding = false;
        for(Entity b : Entities) {
        	if(b!=e && b.name!="player" && b.name!="snakeHead"){
        		if(shapeToCheck.intersects(b.getShape())) { //if it collides with the shape and if it is a validOneWayCollision
        				colliding = true;
        				//else {
        				//if(b.getShape().getY() > collidingBlock.getShape().getY()) {
        				//	skipOneWay = false;
        				//}
        			//}
        		} 
        	}
        }
        return colliding;
    }
}
