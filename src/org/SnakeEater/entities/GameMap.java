package org.SnakeEater.entities;

import java.util.ArrayList;
import java.util.List;

import org.SnakeEater.Game;
import org.SnakeEater.attributes.Renderable;
import org.SnakeEater.states.MainState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * A GameMap is a map of the game which contains the TiledMap data
 * 
 * @author Tucker Lein
 *
 */
public class GameMap implements Renderable {
	//Camera used on the map
    private Camera camera;
    
    //TiledMap of map
    private TiledMap map;
    
    //Level song
    private Music levelMusic;
    
    
    //All MapLayers on the map
    private List<MapLayer> layers = new ArrayList<MapLayer>();
    
    //width of the map
    private int width;
    
    private StateBasedGame game;
    
    //height of the map
    private int height;
    
    //render priority of the map
    private int renderPriority = 1000;
    
    //position in the level song, used for restarting the song when paused
    private float musicPosition = 0;
    
    /**
     * Constructs a new GameMap with the given TiledMap
     * 
     * @param map TiledMap context
     */
    public GameMap(TiledMap map) {
        this.map = map;
        for(int i = 0; i < map.getLayerCount(); i++) {
            int priority = i;
            if(Boolean.parseBoolean(map.getLayerProperty(i, "abovePlayer", "false"))) {
                priority += 1001;
            }
            layers.add(new MapLayer(map, i, priority));
        }
        width = map.getWidth() * map.getTileWidth();
        height = map.getHeight() * map.getTileHeight();
        
    }
    
    /**
     * Goes through the tiledMap and finds all the collidable tiles and creates Blocks at those locations
     */
    private void locateBlocks() {
        int collisionLayer = 0;
        for(int i = 0; i < map.getLayerCount(); i++) { //get the layer which contains the collision blocks
            if(Boolean.parseBoolean(map.getLayerProperty(i, "collisionMask", "false"))){ 
            	collisionLayer = i;
            	int tileId = 0;
                for(int j = 0; j < map.getWidth(); j++) {
                    for(int k = 0; k < map.getHeight(); k++) {
                        tileId = map.getTileId(j, k, collisionLayer);
                        if(Boolean.parseBoolean(map.getTileProperty(map.getTileId(j, k, collisionLayer), "solid", "false"))) {
                            ((MainState) game.getCurrentState()).addEntity(new Block(new Rectangle(j * map.getTileWidth(), k * map.getTileHeight(), 
                                    map.getTileWidth(), map.getTileHeight())));
                            
                        }
                        if(Boolean.parseBoolean(map.getTileProperty(map.getTileId(j, k, collisionLayer), "block", "false"))) {
                        	((MainState) game.getCurrentState()).addEntity(new moveableBlock(new Rectangle(j * map.getTileWidth(), k * map.getTileHeight(), 
                                    16, 16)));
                        }
                    }
                }
            }
        }
        
    }
    
    /**
     * inits the GameMap
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     */
    public void init(GameContainer gc, StateBasedGame game) {
        //levelMusic = ((Game) game).getResourceManager().getMusic(map.getMapProperty("levelMusic", ""));
        //playMusic();
    	this.game = game;
        camera = new Camera(new Vector2f(((MainState)game.getCurrentState()).getPlayer().getShape().getCenterX(), ((MainState)game.getCurrentState()).getPlayer().getShape().getCenterY()), ((MainState)game.getCurrentState()).getMaps());
        for(MapLayer layer : layers) {
        	
            layer.init(gc, game);
        }
        ((Game) game).getRenderQueue().add(this);
        locateBlocks();
    }
    
    /**
     * updates the GameMap's camera and other attributes
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param delta time since last update call
     */
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        camera.update(gc, game, delta);
        Input input = gc.getInput();
        if(input.isKeyPressed(Input.KEY_M)) {
            if(levelMusic.playing()) stopMusic();
            else playMusic();
        }
    }

    /**
     * Renders the GameMap, which only renders debug mode items.
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param g Graphics context
     */
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if(((Game) game).isDebug()) {
        }
    }
    
    /**
     * Plays the levelMusic at the left off position
     */
    private void playMusic() {
        if(!levelMusic.playing()) {
            levelMusic.setPosition(musicPosition);
            levelMusic.loop();
        }
    }
    
    /**
     * Stops the levelMusic and saves the position
     */
    private void stopMusic() {
        musicPosition = levelMusic.getPosition();
        levelMusic.stop();
    }
    

    
    /**
     * Getter for width
     * 
     * @return width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Getter for height
     * 
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Getter for camera
     * 
     * @return camera
     */
    public Camera getCamera() {
        return camera;
    }
    
    /**
     * Getter for map
     * 
     * @return map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Getter for renderPriority
     * 
     * @return renderPriority
     */
    public int getRenderPriority() {
        return renderPriority;
    }
}
