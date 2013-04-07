package org.SnakeEater.entities;

import java.util.ArrayList;
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
    
    //ResourceManager context
    private ResourceManager rm;
    
    //StateBasedGame context
    private StateBasedGame game;
    
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
		 tail = new snakeTail[4];
			for(int i =0; i<4; i++){
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
		for(int i =0; i<4; i++){
			tail[i].animationStack.push(((Game) game).getResourceManager().getAnimation("snake"));
			((MainState)game.getCurrentState()).addEntity(tail[i]);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		super.update(gc, game, delta);
		if(movementSteps%8 == 0){
			if(movementSteps!=0){
				for(int i =3; i>0; i--){
					tail[i].dir=tail[i-1].dir;
				}
				tail[0].dir = dir;
			}
			getMovement();
			
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
	
	
	private void setXPosition(int amount){
		shape.setLocation(shape.getX() + amount, shape.getY());
	}
	
	private void setYPosition(int amount){
		shape.setLocation(shape.getX(), shape.getY()+amount);
	}
	private void getMovement() {
		List<String> directions = new ArrayList<String>();
		////////////////////////////////DIR LEFT/////////////////////////////////////
		if(dir == "left"){
			nextStep.setY(
					shape.getY()+16);
			nextStep.setX(shape.getX());
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("down");
			}
			nextStep.setY(
					shape.getY());
			nextStep.setX(shape.getX() - 16);
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("left");
			}
			nextStep.setY(
					shape.getY() - 16);
			nextStep.setX(shape.getX());
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("up");
			}
			if(directions.size()!=0){
				moveSnake(directions.get(rand.nextInt(directions.size())));
			}
			else{
				//// KILL
				movementSteps = 0;
			}
		}
		/////////////////////////////////////////////DIR RIGHT///////////////////////////////
		else if(dir == "right"){
			nextStep.setY(
					shape.getY());
			nextStep.setX(shape.getX() + 16);
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("right");
			}
			nextStep.setY(
					shape.getY()+ 16);
			nextStep.setX(shape.getX());
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("down");
			}
			nextStep.setY(
					shape.getY() - 16);
			nextStep.setX(shape.getX());
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("up");
			}
			if(directions.size()!=0){
				moveSnake(directions.get(rand.nextInt(directions.size())));
			}
			else{
				////KILL
				movementSteps = 0;
			}
		}
		else if(dir == "up"){
			nextStep.setY(
					shape.getY());
			nextStep.setX(shape.getX() + 16);
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("right");
			}
			nextStep.setY(
					shape.getY());
			nextStep.setX(shape.getX() - 16);
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("left");
			}
			nextStep.setY(
					shape.getY() - 16);
			nextStep.setX(shape.getX());
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("up");
			}
			if(directions.size()!=0){
				moveSnake(directions.get(rand.nextInt(directions.size())));
			}
			else{
				////KILL
				movementSteps = 0;
			}
		}
		else if(dir == "down"){
			nextStep.setY(
					shape.getY());
			nextStep.setX(shape.getX() + 16);
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("right");
			}
			nextStep.setY(
					shape.getY());
			nextStep.setX(shape.getX() - 16);
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("left");
			}
			nextStep.setY(
					shape.getY() + 16);
			nextStep.setX(shape.getX());
			if(!checkCollisions( ((MainState)game.getCurrentState()).getEntity(),  calcLAABB(), this)){
				directions.add("down");
			}
			if(directions.size()!=0){
				moveSnake(directions.get(rand.nextInt(directions.size())));
			}
			else{
				////KILL
				movementSteps = 0;
			}
		}
		
	}
	private void moveSnake(String string) {
		movementSteps+=1;
		for(int i =0; i<4; i++){
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
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) {
		super.render(gc, game, g);
			
		if(animationStack.empty())
			this.setupAnimations(game);
		for(int i =0; i<4; i++){
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
	@Override
	public int getRenderPriority() {
		 return 1000;
	 }

}
