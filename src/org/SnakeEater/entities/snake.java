package org.SnakeEater.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.SnakeEater.Game;
import org.SnakeEater.geom.SmRectangle;
import org.SnakeEater.states.MainState;
import org.SnakeEater.util.AnimationUtils;
import org.SnakeEater.util.ResourceManager;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class snake extends MoveableEntity{

	//Stack containing the animations being used by the player. Only the top of the stack will render
    private Stack<Animation> animationStack = new Stack<Animation>();
    
    private List<String> movementDecider = new ArrayList<String>();
    
    //ResourceManager context
    private ResourceManager rm;
    
    //StateBasedGame context
    private StateBasedGame game;
    
    private int deathTime = 0;
    
    boolean timeToGrow=false;
    
    boolean dead = false;
    
  //StateBasedGame context
    private int lastPlaceChecked;
    
    //Snake Tail
    private snakeTail[] tail;
    
    //movement steps of snake.
    private int movementSteps;
    
    Random rand;
	
	public snake(Shape shape) {
		super(shape);
		rand = new Random();
		nextStep = new SmRectangle(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		dir = "left";
		movementSteps = 0;
		name="snakeHead";
	}
	@Override
    public void init(GameContainer gc, StateBasedGame game) {
        super.init(gc, game);
        ((Game) game).getRenderQueue().add(this);
        rm = ((Game) game).getResourceManager();
        this.game = game;
        movementSpeed = 2;
    }

	@Override
	public void setupAnimations(StateBasedGame game) {
		//Set and load player's static 'animation'
		 tail = new snakeTail[2];
			for(int i =0; i<tail.length; i++){
				tail[i] = new snakeTail(new SmRectangle((shape.getX()+(16*(i+1))), shape.getY(), 16, 16));
				((MainState)game.getCurrentState()).addEntity(tail[i]);
				
			}
		Image[] snake = new Image[] { ((Game) game).getResourceManager().getAnimation("snake").getImage(0)};
		Image[] snake2 = new Image[] { ((Game) game).getResourceManager().getAnimation("snakeLeft").getImage(0)};
		Image[] snake3 = new Image[] { ((Game) game).getResourceManager().getAnimation("snakeFront").getImage(0)};
		((Game) game).getResourceManager().load("snake", new Animation(snake, 1));
		((Game) game).getResourceManager().load("snakeLeft", new Animation(snake2, 6));
        ((Game) game).getResourceManager().load("snakeRight", AnimationUtils.returnFlippedAnimation(new Animation(snake2, 6)));
        ((Game) game).getResourceManager().load("snakeFront", new Animation(snake3, 6));
        animationStack.push(((Game) game).getResourceManager().getAnimation("snakeLeft"));
		for(int i =0; i<tail.length; i++){
			tail[i].animationStack.push(((Game) game).getResourceManager().getAnimation("snake"));
			((MainState)game.getCurrentState()).addEntity(tail[i]);
		}
	}
	
	public void dead(){
		movementSpeed=0;
		dead = true;

	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		super.update(gc, game, delta);
		if(!dead){
			checkPlayer();
			if(movementSteps%8 == 0){
				checkPellet();
				if(movementSteps!=0){
					for(int i =tail.length-1; i>0; i--){
						tail[i].dir=tail[i-1].dir;
					}
					tail[0].dir = dir;
				}
				lookAhead(7, shape, dir);

			}
			else{
				moveSnake(dir);
			}

			if(animationStack.size() == 1) {
				if(dir.equals("left")) animationStack.set(0, rm.getAnimation("snakeLeft"));
				if(dir.equals("right")) animationStack.set(0, rm.getAnimation("snakeRight"));
				if(dir.equals("up")) animationStack.set(0, rm.getAnimation("snake"));
				if(dir.equals("down")) animationStack.set(0, rm.getAnimation("snakeFront"));
			}
		}
	}

	private void checkPlayer(){
		if(this.shape.intersects(((MainState)game.getCurrentState()).getPlayer().shape) && ((MainState)game.getCurrentState()).getPlayer().notDead()){
			((MainState)game.getCurrentState()).getPlayer().collidingAction(2, "");
			timeToGrow = true;
		}
	}
	private void checkPellet() {
		for(Entity b : ((MainState)game.getCurrentState()).getEntity()) {
			if(b.name == "pellet"){
				if(shape.intersects(b.getShape())) {
					timeToGrow = true;
					b.collidingAction(2, "");
				}
			}
		}
	}
		
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) {
		super.render(gc, game, g);
		if(!dead){
			if(timeToGrow){
				timeToGrow = false;
				grow();
			}

			if(animationStack.empty())
				this.setupAnimations(game);
			for(int i =0; i<tail.length; i++){
				tail[i].render(gc, game, g);
			}
			//g.drawImage(((Game) game).getResourceManager().getImage("player"), shape.getX(), shape.getY());
			g.drawAnimation(animationStack.peek(), shape.getX() - ((animationStack.peek().getWidth() - shape.getWidth())/2), shape.getY());
			if(((Game) game).isDebug()) {
				g.setColor(new Color(0, 125, 125, 128));
				g.fillRect(nextStep.getX(), nextStep.getY(), shape.getWidth(), shape.getHeight());
				g.setColor(Color.cyan);
				g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
				g.setColor(Color.orange);
				g.fillRect(collidingBlock.getShape().getX(), collidingBlock.getShape().getY(), collidingBlock.getShape().getWidth(), collidingBlock.getShape().getHeight());
			}
		}
		else{
			deathSequence(gc,game,g);
		}
	}
	private void deathSequence(GameContainer gc, StateBasedGame game2, Graphics g) {
		if(deathTime<10){
			for(int i =0; i<tail.length; i++){
				tail[i].render(gc, game, g);
			}
			g.drawAnimation(animationStack.peek(), shape.getX() - ((animationStack.peek().getWidth() - shape.getWidth())/2), shape.getY());
			deathTime++;
		}
		else if( deathTime<20){
			for(int i =0; i<tail.length; i++){
				tail[i].render(gc, game, g);
			}
			deathTime++;
		}
		else{
			if((deathTime-20)/10 == tail.length){
			}
			else
			{
				for(int i =(deathTime-20)/10; i<tail.length; i++){
					tail[i].render(gc, game, g);
				}
				deathTime++;
			}
		}
		
	}
	@Override
	public int getRenderPriority() {
		 return 1000;
	 }
	
	private void grow() {
		snakeTail[] newTail = new snakeTail[tail.length+1];
		for(int i=0; i<tail.length; i++){
			newTail[i] = tail[i];
		}
		newTail[tail.length] = tail[tail.length-1].clone();
		tail = newTail;
	}
	private void lookAhead(int spacesToCheck, Shape shape, String dir){
		lastPlaceChecked = spacesToCheck+1;
		List<Shape> tails = new ArrayList<Shape>();
		movementDecider.clear();
		for(int i =tail.length-1; i>=0; i--){
			tails.add(tail[i].shape);
		}
		tails.add(shape);
		tails.remove(0);
		if(dir != "left"){
			nextStep.setY(
					shape.getY());
			nextStep.setX(shape.getX() + 16);
			
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this, tails)){
				if(lastPlaceChecked > spacesToCheck){
					lastPlaceChecked = spacesToCheck;
					movementDecider.clear();
					movementDecider.add("right");
				}
				else if(lastPlaceChecked == spacesToCheck){
					movementDecider.add("right");
				}
				lookAheadRec(spacesToCheck-1, new SmRectangle(nextStep.getX(), nextStep.getY(), shape.getWidth(), shape.getHeight()), "right", "right", tails.subList(0, tails.size()));
				//directions.add("right");
			}
		}
		if(dir != "right"){
			nextStep.setY(
					shape.getY());
			nextStep.setX(shape.getX() - 16);
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this, tails)){
				if(lastPlaceChecked > spacesToCheck){
					lastPlaceChecked = spacesToCheck;
					movementDecider.clear();
					movementDecider.add("left");
				}
				else if(lastPlaceChecked == spacesToCheck){
					movementDecider.add("left");
				}
				lookAheadRec(spacesToCheck-1, new SmRectangle(nextStep.getX(), nextStep.getY(), shape.getWidth(), shape.getHeight()), "left", "left", tails.subList(0, tails.size()));
				//directions.add("left");
			}
		}	
		if(dir != "up"){
			nextStep.setY(
					shape.getY() + 16);
			nextStep.setX(shape.getX());
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this, tails)){
				if(lastPlaceChecked > spacesToCheck){
					lastPlaceChecked = spacesToCheck;
					movementDecider.clear();
					movementDecider.add("down");
				}
				else if(lastPlaceChecked == spacesToCheck){
					movementDecider.add("down");
				}
				lookAheadRec(spacesToCheck-1, new SmRectangle(nextStep.getX(), nextStep.getY(), shape.getWidth(), shape.getHeight()), "down", "down", tails.subList(0, tails.size()));
				//directions.add("down");
			}
		}
		if(dir != "down"){
			nextStep.setY(
					shape.getY() - 16);
			nextStep.setX(shape.getX());
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this, tails)){
				if(lastPlaceChecked > spacesToCheck){
					lastPlaceChecked = spacesToCheck;
					movementDecider.clear();
					movementDecider.add("up");
				}
				else if(lastPlaceChecked == spacesToCheck){
					movementDecider.add("up");
				}
				lookAheadRec(spacesToCheck-1, new SmRectangle(nextStep.getX(), nextStep.getY(), shape.getWidth(), shape.getHeight()), "up", "up", tails.subList(0, tails.size()));
				//directions.add("up");
			}
		}
		if(movementDecider.size() == 0){
				dead();
		}
		else{
			//System.out.println();
			moveSnake(movementDecider.get(rand.nextInt(movementDecider.size())));
		}
	}

	private void lookAheadRec(int spacesToCheck, Shape shape, String dir, String Original, List<Shape> tailOld){
		////System.out.println(ignore);
		List<Shape> tails = new ArrayList<Shape>();
		for(int i =0; i<tailOld.size(); i++){
			tails.add(new SmRectangle(tailOld.get(i).getX(), tailOld.get(i).getY(), 16, 16));
		}
		tails.add(shape);
		if(!onPellet(shape, ((MainState)game.getCurrentState()).getEntity()))
			tails.remove(0);
		if(spacesToCheck>0){
			if(dir != "left"){
				nextStep.setY(
						shape.getY());
				nextStep.setX(shape.getX() + 16);
				if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(shape, nextStep), this, tails)){
					if(lastPlaceChecked > spacesToCheck){
						lastPlaceChecked = spacesToCheck;
						movementDecider.clear();
						movementDecider.add(Original);
					}
					else if(lastPlaceChecked == spacesToCheck){
						movementDecider.add(Original);
					}
					lookAheadRec(spacesToCheck-1, new SmRectangle(nextStep.getX(), nextStep.getY(), shape.getWidth(), shape.getHeight()), "right", Original, tails.subList(0, tails.size()));
					//directions.add("right");
				}
			}
			if(dir != "right"){
				nextStep.setY(
						shape.getY());
				nextStep.setX(shape.getX() - 16);
				if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(shape, nextStep), this, tails)){
					if(lastPlaceChecked > spacesToCheck){
						lastPlaceChecked = spacesToCheck;
						movementDecider.clear();
						movementDecider.add(Original);
					}
					else if(lastPlaceChecked == spacesToCheck){
						movementDecider.add(Original);
					}
					lookAheadRec(spacesToCheck-1, new SmRectangle(nextStep.getX(), nextStep.getY(), shape.getWidth(), shape.getHeight()), "left", Original, tails.subList(0, tails.size()));
					//directions.add("left");
				}
			}	
			if(dir != "up"){
				nextStep.setY(
						shape.getY() + 16);
				nextStep.setX(shape.getX());
				////System.out.println("xx" +nextStep.getX() + "yy" + nextStep.getY());
				if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(shape, nextStep), this, tails)){
					if(lastPlaceChecked > spacesToCheck){
						lastPlaceChecked = spacesToCheck;
						movementDecider.clear();
						movementDecider.add(Original);
					}
					else if(lastPlaceChecked == spacesToCheck){
						movementDecider.add(Original);
					}
					lookAheadRec(spacesToCheck-1, new SmRectangle(nextStep.getX(), nextStep.getY(), shape.getWidth(), shape.getHeight()), "down", Original, tails.subList(0, tails.size()));
					////System.out.println(down);
				}
			}
			if(dir != "down"){
				nextStep.setY(
						shape.getY() - 16);
				nextStep.setX(shape.getX());
				
				if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(shape, nextStep), this, tails)){
					if(lastPlaceChecked > spacesToCheck){
						lastPlaceChecked = spacesToCheck;
						movementDecider.clear();
						movementDecider.add(Original);
					}
					else if(lastPlaceChecked == spacesToCheck){
						movementDecider.add(Original);
					}
					lookAheadRec(spacesToCheck-1, new SmRectangle(nextStep.getX(), nextStep.getY(), shape.getWidth(), shape.getHeight()), "up", Original, tails.subList(0, tails.size()));
					//directions.add("up");
				}
			}
		}
	}
	
	
	private boolean onPellet(Shape shape, List<Entity> Entities) {
		for(Entity b : Entities) {
			if(b.name.equals("pellet")){
				if(b.shape.intersects(shape)){
					return true;
				}
			}
		}
		return false;
	}
	protected Shape calcLAABB(Shape shape, Shape nextStep) {
        return nextStep;
    }
	
	
	private void setXPosition(int amount){
		shape.setLocation(shape.getX() + amount, shape.getY());
	}

	private void setYPosition(int amount){
		shape.setLocation(shape.getX(), shape.getY()+amount);
	}
	private void moveSnake(String string) {
		movementSteps+=1;
		for(int i =0; i<tail.length; i++){
			tail[i].moveSnake(tail[i].dir);
		}
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

	protected boolean checkCollisions(List<Entity> Entities, Shape shapeToCheck, Entity e, List<Shape> tails) {
		collidingBlock = new Block(new SmRectangle(0,0,0,0));
		boolean colliding = false;
		float distance = Float.MAX_VALUE;
		Vector2f shapeCoord = new Vector2f(shape.getCenterX(), shape.getCenterY());
		Vector2f colCoord = new Vector2f(0, 0);
		for(Entity b : Entities) {
			if(b!=e && (b.name !="snake" && b.name !="pellet" && b.name !="player")){
				if(shapeToCheck.intersects(b.getShape())) { //if it collides with the shape and if it is a validOneWayCollision
					if(!skipOneWay) {
						colCoord.set(b.getShape().getCenterX(), b.getShape().getCenterY());
						if(shapeCoord.distance(colCoord) < distance) { //set collidingBlock if the distance between it and the shapeToCheck is the shortest found
							distance = shapeCoord.distance(colCoord);
							collidingBlock = b;
						}
						colliding = true;
					} //else {
					//if(b.getShape().getY() > collidingBlock.getShape().getY()) {
					//	skipOneWay = false;
					//}
					//}
				} 
			}
		}
		Shape c = tails.get(tails.size()-1);
		for(Shape b : tails) {
			//System.out.println(b.getX() + " " + b.getY());
			//System.out.println(c.getX() + " " + c.getY());
			if(b!= tails.get(tails.size()-1)){
				if(shapeToCheck.intersects(b)) { //if it collides with the shape and if it is a validOneWayCollision
					if(!skipOneWay) {
						colCoord.set(b.getCenterX(), b.getCenterY());
						if(shapeCoord.distance(colCoord) < distance) { //set collidingBlock if the distance between it and the shapeToCheck is the shortest found
							distance = shapeCoord.distance(colCoord);
							//collidingBlock = b;
						}
						colliding = true;
						//System.out.println(b.getY());
					}
				}
			}
		}
		//System.out.println(colliding);
		return colliding;
	}

}